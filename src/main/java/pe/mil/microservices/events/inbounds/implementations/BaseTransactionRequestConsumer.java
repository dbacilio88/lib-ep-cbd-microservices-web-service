package pe.mil.microservices.events.inbounds.implementations;

import ep.mil.microservices.dtos.Event;
import lombok.extern.log4j.Log4j2;
import pe.mil.microservices.dtos.StandardTransaction;
import pe.mil.microservices.dtos.TransactionMessage;
import pe.mil.microservices.events.inbounds.base.BaseMessageConsumer;
import pe.mil.microservices.events.inbounds.contracts.ITransactionRequestConsumer;

@Log4j2
public abstract class BaseTransactionRequestConsumer extends BaseMessageConsumer implements ITransactionRequestConsumer {

    @Override
    public void processTransaction(Event<TransactionMessage<StandardTransaction>> standardTransaction) {
        var transactionMessage = standardTransaction.getData();
        this.processAppSessionContext(transactionMessage);
        log.info("Receiving message. transactionType [{}], messageId [{}], requestId [{}]",
            transactionMessage.getTransactionType(),
            transactionMessage.getMessageId(),
            transactionMessage.getRequestId());

    }

    protected abstract String getTransactionType();
}
