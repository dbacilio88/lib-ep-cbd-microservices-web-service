package pe.mil.microservices.events.inbounds.implementations;

import ep.mil.microservices.dtos.Event;
import lombok.extern.log4j.Log4j2;
import pe.mil.microservices.dtos.StandardTransaction;
import pe.mil.microservices.dtos.TransactionMessage;
import pe.mil.microservices.events.inbounds.base.BaseMessageConsumer;
import pe.mil.microservices.events.inbounds.contracts.ITransactionResponseConsumer;

@Log4j2
public abstract class BaseTransactionResponseConsumer extends BaseMessageConsumer implements ITransactionResponseConsumer {

    @Override
    public void processTransaction(Event<TransactionMessage<StandardTransaction>> eventTransactionMessage) {
        var transactionMessage = eventTransactionMessage.getData();
        transactionMessage.setTransactionType(getTransactionType());

        this.processAppSessionContext(transactionMessage);
        log.info("Receiving message. transactionType [{}], messageId [{}], requestId [{}]",
            transactionMessage.getTransactionType(),
            transactionMessage.getMessageId(),
            transactionMessage.getRequestId());

        //base transaction logic here
    }

    protected abstract String getTransactionType();
}
