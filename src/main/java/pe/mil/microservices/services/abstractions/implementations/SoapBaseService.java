package pe.mil.microservices.services.abstractions.implementations;

import lombok.extern.log4j.Log4j2;
import pe.mil.microservices.components.exceptions.SoapBusinessProcessException;
import pe.mil.microservices.components.wsdl.SoapValidationResult;
import pe.mil.microservices.dtos.request.SoapBusinessRequest;
import pe.mil.microservices.dtos.response.SoapBusinessResponse;
import pe.mil.microservices.services.abstractions.contracts.ISoapCustomDefinition;
import pe.mil.microservices.services.abstractions.contracts.ISoapStandardDefinition;
import pe.mil.microservices.utils.components.enums.ResponseCode;
import pe.mil.microservices.utils.components.enums.ValidateResult;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

@Log4j2
public abstract class SoapBaseService {

    private final String serviceIdentification;

    public SoapBaseService(final String serviceName) {
        this.serviceIdentification = UUID.randomUUID().toString();
        log.debug("services name {}, serviceIdentification {}", serviceName, serviceIdentification);
        log.debug("{} loaded successfully", serviceName);
    }

    public String getServiceIdentification() {
        return this.serviceIdentification;
    }

    public static <T, R> SoapBusinessResponse<R> createDoOnReturnSoapBusinessResponse(final R businessResponse, final SoapBusinessRequest<T, R> soapBusinessRequest) {
        final SoapBusinessResponse<R> soapBusinessResponse = SoapBusinessResponse.<R>builder()
            .soapBusinessResponse(businessResponse)
            .soapDefinitionId(soapBusinessRequest.getSoapDefinitionId())
            .build();
        return soapBusinessResponse;
    }

    public <T> Mono<?> doOnValidateSoapResponse(
        final T soapResponse,
        final ISoapStandardDefinition soapStandardDefinition,
        final ISoapCustomDefinition soapCustomDefinition,
        final boolean isStandard) {

        log.debug("soapStandardProviderServiceId {}", this.getServiceIdentification());
        log.debug("{} call doOnValidateSoapResponse method", this.getClass().getName());
        return Mono.just(soapResponse)
            .flatMap(response -> {
                log.debug("find soap response validator");
                if (Boolean.TRUE.equals(isStandard)) {
                    return Mono.just(soapStandardDefinition.validateSoapResponse())
                        .flatMap(standardSoapValidationResponse -> {
                            final SoapValidationResult soapValidationResult = standardSoapValidationResponse.validateResponse(response);
                            if (Objects.nonNull(soapValidationResult) && ValidateResult.SUCCESSFULLY_VALID.equals(soapValidationResult.getValidateResult())) {
                                return Mono.just(response);
                            }
                            return Mono.error(() -> new SoapBusinessProcessException("error in soap response validation", ResponseCode.ERROR_DATA_INVALID));
                        });
                } else {
                    return Mono.just(soapCustomDefinition.validateSoapResponse())
                        .flatMap(currentSoapValidationResponse -> {

                            final SoapValidationResult soapValidationResult = currentSoapValidationResponse.validateResponse(response);
                            if (Objects.nonNull(soapValidationResult) && ValidateResult.SUCCESSFULLY_VALID.equals(soapValidationResult.getValidateResult())) {
                                return Mono.just(response);
                            }
                            return Mono.error(() -> new SoapBusinessProcessException("error in soap response validation", ResponseCode.ERROR_DATA_INVALID));
                        });
                }
            })
            .doOnSuccess(success ->
                log.debug("process doOnValidateSoapResponse successfully completed, response: {}", success.toString())
            )
            .doOnError(throwable ->
                log.error("error in process doOnValidateSoapResponse, error: {}", throwable.getMessage())
            );
    }
}
