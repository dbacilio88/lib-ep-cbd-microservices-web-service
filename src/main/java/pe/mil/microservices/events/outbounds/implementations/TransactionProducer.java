package pe.mil.microservices.events.outbounds.implementations;

import ep.mil.microservices.components.enums.EventType;
import ep.mil.microservices.components.helpers.EnvironmentUtils;
import ep.mil.microservices.dtos.Event;
import ep.mil.microservices.dtos.RoutingKey;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import pe.mil.microservices.components.helpers.RoutingKeyHelper;
import pe.mil.microservices.constants.TransactionsConstants;
import pe.mil.microservices.dtos.StandardTransaction;
import pe.mil.microservices.dtos.TransactionMessage;
import pe.mil.microservices.dtos.commons.CommonBrokerConfiguration;
import pe.mil.microservices.events.outbounds.contracts.ITransactionProducer;

import java.util.Objects;

@Component
@Log4j2
public class TransactionProducer implements ITransactionProducer {

    private static final String ROUTING_KEY_COMMAND = "REQUEST";
    private static final String ROUTING_KEY_DOMAIN = "TRANSACTION";
    private static final String ROUTING_KEY_DESTINY = "API-ISO-SENDER";
    private final EnvironmentUtils environmentUtils;

    public TransactionProducer(EnvironmentUtils environmentUtils) {
        this.environmentUtils = environmentUtils;
    }

    @Override
    public Event<TransactionMessage<StandardTransaction>> processTransaction(TransactionMessage<StandardTransaction> transactionMessage) {
        Objects.requireNonNull(transactionMessage.getTransactionType(), "Transaction type is required");
        CommonBrokerConfiguration configuration = CommonBrokerConfiguration.builder()
            .command(ROUTING_KEY_COMMAND)
            .routingDomain(ROUTING_KEY_DOMAIN)
            .routingKeyOrigin(transactionMessage.getTransactionType())
            .routingKeyDestiny(ROUTING_KEY_DESTINY)
            .build();
        final Event<TransactionMessage<StandardTransaction>> eventTransactionMessage = new Event<>();
        eventTransactionMessage.setData(transactionMessage);
        final RoutingKey routingKey = RoutingKeyHelper.createRoutingKey(EventType.SERVICE, configuration);
        eventTransactionMessage.setRoutingKey(routingKey);
        log.info("Sending transaction to exchange [{}], routing key [{}]. transactionType [{}], messageId [{}], requestId [{}]",
            environmentUtils.getValue(TransactionsConstants.BROKER_DECLARE_EXCHANGE),
            routingKey,
            transactionMessage.getTransactionType(),
            transactionMessage.getMessageId(),
            transactionMessage.getRequestId());

        return eventTransactionMessage;
    }
}
