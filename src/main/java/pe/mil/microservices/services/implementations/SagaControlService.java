package pe.mil.microservices.services.implementations;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pe.mil.microservices.components.enums.SagaResultValidation;
import pe.mil.microservices.components.exceptions.SagaProcessException;
import pe.mil.microservices.components.validations.ISagaControlValidation;
import pe.mil.microservices.dtos.SagaControl;
import pe.mil.microservices.dtos.SagaPersistenceResult;
import pe.mil.microservices.repositories.entities.SagaControlEntity;
import pe.mil.microservices.services.contracts.ISagaControlService;
import pe.mil.microservices.utils.components.helpers.ObjectMapperHelper;
import reactor.core.publisher.Mono;

@Log4j2
@Service
public class SagaControlService implements ISagaControlService {

    @Override
    public Mono<SagaControl> getById(String id) {
        // add logic business here
        return Mono.error(() -> new SagaProcessException("error in getDomainEntityById, the SagaControlEntity not exists"));
    }

    @Override
    public Mono<SagaControl> save(SagaControl entity) {
        return ISagaControlValidation.iSagaControlValidation().apply(entity).flatMap(sagaPersistenceResult -> this.doOnSaveSagaControl(sagaPersistenceResult, entity)).doOnSuccess(success -> log.debug("success save SagaControl, response: {}", success.toString())).doOnError(throwable -> log.error("exception error in process saveEntity from SagaControl, error: {}", throwable.getMessage()));
    }

    @Override
    public Mono<SagaControl> update(SagaControl entity) {
        return ISagaControlValidation
            .iSagaControlValidation().apply(entity)
            .flatMap(sagaPersistenceResult -> this.doOnUpdateSagaControl(sagaPersistenceResult, entity))
            .doOnSuccess(success -> log.debug("success update SagaControl, response: {}", success.toString()))
            .doOnError(throwable -> log.error("exception error in process update from SagaControl, error: {}", throwable.getMessage()));
    }

    private Mono<SagaControl> doOnUpdateSagaControl(final SagaPersistenceResult sagaPersistenceResult, final SagaControl entity) {
        // add logic business here
        if (SagaResultValidation.SUCCESSFULLY_VALID.equals(sagaPersistenceResult.getSagaResultValidation())) {
            final SagaControlEntity sagaControlEntity = ObjectMapperHelper.map(entity, SagaControlEntity.class);
            // add logic repository here
            return Mono.just(ObjectMapperHelper.map(sagaControlEntity, SagaControl.class));

        }
        return Mono.error(() -> new SagaProcessException("error in validation saga validation SagaControl"));
    }

    private Mono<SagaControl> doOnSaveSagaControl(final SagaPersistenceResult sagaPersistenceResult, final SagaControl entity) {

        if (SagaResultValidation.SUCCESSFULLY_VALID.equals(sagaPersistenceResult.getSagaResultValidation())) {
            final SagaControlEntity sagaControlEntity = ObjectMapperHelper.map(entity, SagaControlEntity.class);
            // add logic repository here
            return Mono.just(ObjectMapperHelper.map(sagaControlEntity, SagaControl.class));
        }
        return Mono.error(() -> new SagaProcessException("error in validation saga validation SagaControl"));

    }
}
