package pe.mil.microservices.components.validations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pe.mil.microservices.components.wsdl.SoapValidationResult;
import pe.mil.microservices.dtos.request.SoapBusinessRequest;
import pe.mil.microservices.utils.components.enums.ValidateResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public interface ISoapBusinessRequestValidation extends Function<SoapBusinessRequest<?, ?>, SoapValidationResult> {

    Logger log = LogManager.getLogger(ISoapBusinessRequestValidation.class);

    static ISoapBusinessRequestValidation validateSoapBusinessRequest() {

        return soapBusinessRequest -> {
            final SoapValidationResult soapValidationResult = SoapValidationResult
                .builder()
                .validateResult(ValidateResult.SUCCESSFULLY_VALID)
                .build();
            final List<String> errors = new ArrayList<>();

            if (Objects.isNull(soapBusinessRequest)) {
                log.error("error the soapBusinessRequest is null");
                errors.add("error the soapBusinessRequest is null");
            }

            if (Objects.isNull(soapBusinessRequest.getSoapBusinessRequest())) {
                log.error("error the businessRequest is null");
                errors.add("error the businessRequest is null");
            }

            if (Objects.isNull(soapBusinessRequest.getSoapDefinitionId())) {
                log.error("error the soapProviderIdentification is null");
                errors.add("error the soapProviderIdentification is null");
            }

            if (!errors.isEmpty()) {
                soapValidationResult.setValidateResult(ValidateResult.NOT_VALID);
                soapValidationResult.setErrors(errors);
            }

            return soapValidationResult;
        };
    }
}
