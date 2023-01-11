package pe.mil.microservices.components.validations;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pe.mil.microservices.components.enums.BrokerConfigurationValidation;
import pe.mil.microservices.dtos.commons.CommonBrokerConfiguration;

import java.util.function.Function;

@FunctionalInterface
public interface IBrokerConfigurationValidation extends Function<CommonBrokerConfiguration, BrokerConfigurationValidation> {

    Logger logger = LogManager.getLogger(IBrokerConfigurationValidation.class);

    static IBrokerConfigurationValidation isConfigurationValidation() {
        return commonBrokerConfiguration -> {
            if (StringUtils.isEmpty(commonBrokerConfiguration.getCommand())) {
                logger.error("error in commonBrokerConfiguration, the field command is empty");
                return BrokerConfigurationValidation.CONFIGURATION_NOT_VALID;
            }

            if (StringUtils.isEmpty(commonBrokerConfiguration.getRoutingDomain())) {
                logger.error("error in commonBrokerConfiguration, the field routingDomain is empty");
                return BrokerConfigurationValidation.CONFIGURATION_NOT_VALID;
            }

            if (StringUtils.isEmpty(commonBrokerConfiguration.getRoutingKeyOrigin())) {
                logger.error("error in commonBrokerConfiguration, the field routingKeyOrigin is empty");
                return BrokerConfigurationValidation.CONFIGURATION_NOT_VALID;
            }

            if (StringUtils.isEmpty(commonBrokerConfiguration.getRoutingKeyDestiny())) {
                logger.error("error in commonBrokerConfiguration, the field routingKeyDestiny is empty");
                return BrokerConfigurationValidation.CONFIGURATION_NOT_VALID;
            }

            return BrokerConfigurationValidation.CONFIGURATION_SUCCESSFULLY_VALID;
        };
    }

    default IBrokerConfigurationValidation and(IBrokerConfigurationValidation otherValidation) {
        return commonBrokerConfiguration -> {
            BrokerConfigurationValidation brokerConfigurationValidation = this.apply(commonBrokerConfiguration);
            return BrokerConfigurationValidation.CONFIGURATION_SUCCESSFULLY_VALID.equals(brokerConfigurationValidation) ? otherValidation.apply(commonBrokerConfiguration) : brokerConfigurationValidation;
        };
    }
}
