package pe.mil.microservices.events.outbounds.contracts;

import ep.mil.microservices.dtos.Event;
import pe.mil.microservices.dtos.StandardTransaction;
import pe.mil.microservices.dtos.TransactionMessage;

public interface ITransactionProducer {

    Event<TransactionMessage<StandardTransaction>> processTransaction(final TransactionMessage<StandardTransaction> transactionMessage);

}
