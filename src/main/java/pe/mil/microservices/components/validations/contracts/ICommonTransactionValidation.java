package pe.mil.microservices.components.validations.contracts;

import pe.mil.microservices.dtos.StandardTransaction;
import pe.mil.microservices.dtos.TransactionMessage;
import reactor.core.publisher.Mono;

public interface ICommonTransactionValidation {

    Mono<TransactionMessage<StandardTransaction>> validateFields(final TransactionMessage<StandardTransaction> transactionMessage);
}
