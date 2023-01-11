package pe.mil.microservices.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionsConstants {

    public static final String CONNECTOR_UNKNOWN = "UNKNOWN";
    public static final String BROKER_DECLARE_EXCHANGE = "EX-BROKER-NOVOPAYMENT-SERVICES";
    public static final String BROKER_DECLARE_EXCHANGE_TYPE = "TOPIC";
}
