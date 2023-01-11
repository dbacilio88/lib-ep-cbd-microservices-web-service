package pe.mil.microservices.events.callbacks;

import ep.mil.microservices.dtos.Event;
import lombok.extern.log4j.Log4j2;
import pe.mil.microservices.dtos.StandardTransaction;
import pe.mil.microservices.dtos.TransactionMessage;

@Log4j2
public class BaseTransactionBrokerCallBack {

    public void processBrokerTransactionCallBack(int code, String message, Event<TransactionMessage<StandardTransaction>> messageEvent) {
        log.error("Error in process publisher");
        // your code the process error
    }
}
