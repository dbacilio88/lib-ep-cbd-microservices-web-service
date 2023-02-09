package pe.mil.microservices.components.validations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pe.mil.microservices.components.wsdl.SoapValidationResult;
import pe.mil.microservices.services.abstractions.contracts.ISoapCustomDefinition;
import pe.mil.microservices.utils.components.enums.ValidateResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface ISoapCustomDefinitionValidation extends Function<ISoapCustomDefinition, SoapValidationResult> {

    Logger log = LogManager.getLogger(ISoapCustomDefinitionValidation.class);

    static ISoapCustomDefinitionValidation validateMutualConfiguration() {

        return soapProviderService -> {
            final SoapValidationResult soapValidationResult = SoapValidationResult.builder().validateResult(ValidateResult.SUCCESSFULLY_VALID).build();
            final List<String> errors = new ArrayList<>();

            if (Objects.isNull(soapProviderService)) {
                log.error("error in ISoapProviderService the soapProviderService are not defined");
                errors.add("error in ISoapProviderService the soapProviderService are not defined");
            }

            if (Objects.isNull(soapProviderService.soapMapperRequest())) {
                log.error("error in ISoapCustomDefinition the soapMapperRequest are not defined");
                errors.add("error in ISoapCustomDefinition the soapMapperRequest are not defined");
            }

            if (Objects.isNull(soapProviderService.soapMapperResponse())) {
                log.error("error in ISoapCustomDefinition the soapMapperResponse are not defined");
                errors.add("error in ISoapCustomDefinition the soapMapperResponse are not defined");
            }

            if (Objects.isNull(soapProviderService.validateSoapRequest())) {
                log.error("error in ISoapCustomDefinition the validateSoapRequest are not defined");
                errors.add("error in ISoapCustomDefinition the validateSoapRequest are not defined");
            }

            if (Objects.isNull(soapProviderService.validateSoapResponse())) {
                log.error("error in ISoapCustomDefinition the validateSoapResponse are not defined");
                errors.add("error in ISoapCustomDefinition the validateSoapResponse are not defined");
            }

            if (!errors.isEmpty()) {
                soapValidationResult.setValidateResult(ValidateResult.NOT_VALID);
                soapValidationResult.setErrors(errors);
            }

            return soapValidationResult;
        };
    }
}
