package pe.mil.microservices.events.inbounds.contracts;

import ep.mil.microservices.dtos.Event;
import pe.mil.microservices.dtos.StandardTransaction;
import pe.mil.microservices.dtos.TransactionMessage;

public interface ITransactionResponseConsumer {

    void processTransaction(final Event<TransactionMessage<StandardTransaction>> eventTransactionMessage);
}
