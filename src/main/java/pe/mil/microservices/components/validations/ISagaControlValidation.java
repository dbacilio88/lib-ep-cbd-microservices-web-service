package pe.mil.microservices.components.validations;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pe.mil.microservices.components.enums.SagaResultValidation;
import pe.mil.microservices.dtos.SagaControl;
import pe.mil.microservices.dtos.SagaPersistenceResult;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@FunctionalInterface
public interface ISagaControlValidation extends Function<SagaControl, Mono<SagaPersistenceResult>> {

    Logger logger = LogManager.getLogger(ISagaControlValidation.class);

    static ISagaControlValidation iSagaControlValidation() {
        return sagaControl -> Mono.just(SagaPersistenceResult
                .builder()
                .sagaResultValidation(SagaResultValidation.SUCCESSFULLY_VALID).build())
            .flatMap(sagaPersistenceResult -> {
                final List<String> errors = new ArrayList<>();
                final String sagaEntityName = sagaControl.getClass().getName();
                logger.info("sagaEntityName {} ", sagaEntityName);
                if (StringUtils.isEmpty(sagaControl.getMessageId())) {
                    errors.add("error the parameter messageId is empty or null");
                }

                if (StringUtils.isEmpty(sagaControl.getEventId())) {
                    errors.add("error the parameter eventId is empty or null");
                }

                if (!errors.isEmpty()) {
                    sagaPersistenceResult.setSagaResultValidation(SagaResultValidation.NOT_VALID);
                    sagaPersistenceResult.setErrors(errors);
                    logger.error("error in process validation SagaControl entity");
                }

                return Mono.just(sagaPersistenceResult);
            });
    }


    default ISagaControlValidation and(ISagaControlValidation otherValidation) {
        return sagaControl -> {
            final Mono<SagaPersistenceResult> sagaPersistenceResult = this.apply(sagaControl);
            sagaPersistenceResult.flatMap(currentSagaPersistenceResult -> SagaResultValidation.SUCCESSFULLY_VALID.equals(currentSagaPersistenceResult.getSagaResultValidation()) ? otherValidation.apply(sagaControl) : Mono.just(sagaPersistenceResult));
            return sagaPersistenceResult;
        };
    }

}
