package pe.mil.microservices.components.helpers;

import ep.mil.microservices.components.enums.EventType;
import ep.mil.microservices.dtos.RoutingKey;
import lombok.experimental.UtilityClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pe.mil.microservices.components.enums.BrokerConfigurationValidation;
import pe.mil.microservices.components.exceptions.BrokerConfigureException;
import pe.mil.microservices.components.validations.IBrokerConfigurationValidation;
import pe.mil.microservices.dtos.commons.CommonBrokerConfiguration;
import pe.mil.microservices.utils.components.enums.ResponseCode;

@UtilityClass
public class RoutingKeyHelper {

    private static final Logger logger = LogManager.getLogger(RoutingKeyHelper.class);

    public static RoutingKey createRoutingKey(final EventType eventType, final CommonBrokerConfiguration brokerConfiguration) {
        final BrokerConfigurationValidation configurationValidation = IBrokerConfigurationValidation.isConfigurationValidation().apply(brokerConfiguration);
        if (BrokerConfigurationValidation.CONFIGURATION_NOT_VALID.equals(configurationValidation)) {
            throw new BrokerConfigureException("error un CommonBrokerConfiguration", ResponseCode.ERROR_INVALID_PARAMETERS);
        }

        final RoutingKey routingKey = RoutingKey.builder()
            .eventType(eventType)
            .origin(brokerConfiguration.getRoutingKeyOrigin())
            .destiny(brokerConfiguration.getRoutingKeyDestiny())
            .domain(brokerConfiguration.getRoutingDomain())
            .command(brokerConfiguration.getCommand())
            .build();
        logger.debug("the routingKey configured is: {}", routingKey);
        return routingKey;
    }
}
