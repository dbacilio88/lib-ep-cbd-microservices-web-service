package pe.mil.microservices.components.validations.implementations;

import lombok.extern.log4j.Log4j2;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.cfg.defs.NotBlankDef;
import org.springframework.stereotype.Component;
import pe.mil.microservices.components.validations.contracts.ICommonTransactionValidation;
import pe.mil.microservices.dtos.StandardTransaction;
import pe.mil.microservices.dtos.TransactionMessage;
import reactor.core.publisher.Mono;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;

@Component
@Log4j2
public class CommonTransactionValidation implements ICommonTransactionValidation {

    @Override
    public Mono<TransactionMessage<StandardTransaction>> validateFields(TransactionMessage<StandardTransaction> transactionMessage) {

        log.debug("Starting fields validation");
        var configuration = Validation.byProvider(HibernateValidator.class).configure();

        var constrainMapping = configuration.createConstraintMapping();

        constrainMapping.type(StandardTransaction.class).field("messageTypeIndicator").constraint(new NotBlankDef()).field("dataElement_3").constraint(new NotBlankDef())

        ;


        var validator = configuration.addMapping(constrainMapping).buildValidatorFactory().getValidator();

        var validationConstraints = validator.validate(transactionMessage.getData());

        if (validationConstraints != null && !validationConstraints.isEmpty()) {
            var e = new ConstraintViolationException(validationConstraints);
            log.error("Transaction message with validation errors. [{}]", e.getLocalizedMessage());
            return Mono.error(() -> e);
        }

        log.debug("Transaction message validated");

        return Mono.just(transactionMessage);
    }
}
