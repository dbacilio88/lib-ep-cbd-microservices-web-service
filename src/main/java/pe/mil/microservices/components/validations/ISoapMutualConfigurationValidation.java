package pe.mil.microservices.components.validations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pe.mil.microservices.components.wsdl.SoapMutualConfiguration;
import pe.mil.microservices.components.wsdl.SoapValidationResult;
import pe.mil.microservices.utils.components.enums.ValidateResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface ISoapMutualConfigurationValidation extends Function<SoapMutualConfiguration, SoapValidationResult> {

    Logger log = LogManager.getLogger(ISoapMutualConfigurationValidation.class);

    static ISoapMutualConfigurationValidation validateDefinitions() {

        return soapMutualConfiguration -> {

            final SoapValidationResult soapValidationResult = SoapValidationResult.builder().validateResult(ValidateResult.SUCCESSFULLY_VALID).build();
            final List<String> errors = new ArrayList<>();

            if (Objects.isNull(soapMutualConfiguration)) {
                log.error("error in soapMutualConfiguration are not defined");
                errors.add("error in soapMutualConfiguration are not defined");
            }

            if (Objects.isNull(soapMutualConfiguration.getClientKeyStorePath())) {
                log.error("error in SoapMutualConfiguration the keyStorePath are not defined");
                errors.add("error in SoapMutualConfiguration the keyStorePath are not defined");
            }

            if (Objects.isNull(soapMutualConfiguration.getClientKeyStorePassword())) {
                log.error("error in SoapMutualConfiguration the keyStorePassword are not defined");
                errors.add("error in SoapMutualConfiguration the keyStorePassword are not defined");
            }

            if (Objects.isNull(soapMutualConfiguration.getServerTrustStorePath())) {
                log.error("error in SoapMutualConfiguration the serverTrustStorePath are not defined");
                errors.add("error in SoapMutualConfiguration the serverTrustStorePath are not defined");
            }

            if (Objects.isNull(soapMutualConfiguration.getServerTrustStorePassword())) {
                log.error("error in SoapMutualConfiguration the serverTrustStorePassword are not defined");
                errors.add("error in SoapMutualConfiguration the serverTrustStorePassword are not defined");
            }

            if (!errors.isEmpty()) {
                soapValidationResult.setValidateResult(ValidateResult.NOT_VALID);
                soapValidationResult.setErrors(errors);
            }

            return soapValidationResult;
        };
    }
}
