package pe.mil.microservices.components.validations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pe.mil.microservices.components.wsdl.SoapValidationResult;
import pe.mil.microservices.services.abstractions.contracts.ISoapStandardDefinition;
import pe.mil.microservices.utils.components.enums.ValidateResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

@FunctionalInterface
public interface ISoapStandardDefinitionValidation extends Function<ISoapStandardDefinition, SoapValidationResult> {

    Logger log = LogManager.getLogger(ISoapStandardDefinitionValidation.class);

    static ISoapStandardDefinitionValidation validateDefinitions() {

        return soapStandardDefinition -> {

            final SoapValidationResult soapValidationResult = SoapValidationResult.builder().validateResult(ValidateResult.SUCCESSFULLY_VALID).build();
            final List<String> errors = new ArrayList<>();

            if (Objects.isNull(soapStandardDefinition)) {
                log.error("error in ISoapProviderService the soapStandardDefinition are not defined");
                errors.add("error in ISoapProviderService the soapStandardDefinition are not defined");
            }

            if (Objects.isNull(soapStandardDefinition.validateSoapRequest())) {
                log.error("error in ISoapCustomDefinition the validateSoapRequest are not defined");
                errors.add("error in ISoapCustomDefinition the validateSoapRequest are not defined");
            }

            if (Objects.isNull(soapStandardDefinition.validateSoapResponse())) {
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

    static ISoapStandardDefinitionValidation customValidation(final Predicate<ISoapStandardDefinition> validation) {

        return soapStandardDefinition -> {

            final SoapValidationResult soapValidationResult = SoapValidationResult.builder().validateResult(ValidateResult.SUCCESSFULLY_VALID).build();
            final List<String> errors = new ArrayList<>();

            if (Boolean.FALSE.equals(validation.test(soapStandardDefinition))) {
                log.error("error in customValidation");
                errors.add("error in customValidation");
            }

            if (!errors.isEmpty()) {
                soapValidationResult.setValidateResult(ValidateResult.NOT_VALID);
                soapValidationResult.setErrors(errors);
            }

            return soapValidationResult;
        };
    }

    default ISoapStandardDefinitionValidation and(ISoapStandardDefinitionValidation otherValidation) {

        return soapStandardDefinition -> {
            final SoapValidationResult soapValidationResult = this.apply(soapStandardDefinition);
            return ValidateResult.SUCCESSFULLY_VALID.equals(soapValidationResult.getValidateResult()) ? otherValidation.apply(soapStandardDefinition) : soapValidationResult;
        };
    }
}
