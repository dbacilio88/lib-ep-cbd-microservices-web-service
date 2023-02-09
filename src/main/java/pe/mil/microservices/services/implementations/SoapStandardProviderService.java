package pe.mil.microservices.services.implementations;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pe.mil.microservices.components.exceptions.SoapBusinessProcessException;
import pe.mil.microservices.components.validations.ISoapBusinessRequestValidation;
import pe.mil.microservices.components.validations.ISoapStandardDefinitionValidation;
import pe.mil.microservices.components.wsdl.SoapValidationResult;
import pe.mil.microservices.dtos.request.SoapBusinessRequest;
import pe.mil.microservices.dtos.response.SoapBusinessResponse;
import pe.mil.microservices.services.abstractions.contracts.ISoapStandardDefinition;
import pe.mil.microservices.services.abstractions.implementations.SoapBaseService;
import pe.mil.microservices.services.contracts.ISoapClientService;
import pe.mil.microservices.services.contracts.ISoapStandardProviderService;
import pe.mil.microservices.utils.components.enums.ResponseCode;
import pe.mil.microservices.utils.components.enums.ValidateResult;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static pe.mil.microservices.utils.constants.CommonSoapConstants.SOAP_STANDARD_PROVIDER_SERVICE_IDENTIFICATION;
import static pe.mil.microservices.utils.constants.CommonSoapConstants.SOAP_STANDARD_PROVIDER_SERVICE_NAME;

@Log4j2
@Service(SOAP_STANDARD_PROVIDER_SERVICE_IDENTIFICATION)
public class SoapStandardProviderService extends SoapBaseService implements ISoapStandardProviderService {
    private final ISoapClientService soapClientService;

    public SoapStandardProviderService(final ISoapClientService soapClientService) {
        super(SOAP_STANDARD_PROVIDER_SERVICE_NAME);
        this.soapClientService = soapClientService;
    }

    @Override
    public <T, R> Mono<SoapBusinessResponse<R>> doOnExecuteSoapService(SoapBusinessRequest<T, R> soapBusinessRequest, ISoapStandardDefinition soapStandardDefinition) {
        log.debug("soapStandardProviderServiceId {}", this.getServiceIdentification());
        log.debug("{} call doOnExecuteSoapService method", this.getClass().getName());
        final SoapValidationResult soapBusinessRequestValidationResult = ISoapBusinessRequestValidation.validateSoapBusinessRequest()
            .apply(soapBusinessRequest);

        if (ValidateResult.NOT_VALID.equals(soapBusinessRequestValidationResult.getValidateResult())) {
            return Mono.error(() -> new SoapBusinessProcessException("error in soap business request validation", ResponseCode.ERROR_DATA_INVALID));
        }

        final SoapValidationResult soapDefinitionValidationResult = ISoapStandardDefinitionValidation.validateDefinitions()
            .apply(soapStandardDefinition);

        if (ValidateResult.NOT_VALID.equals(soapDefinitionValidationResult.getValidateResult())) {
            return Mono.error(() -> new SoapBusinessProcessException("error in soap standard definition validation", ResponseCode.ERROR_DATA_INVALID));
        }

        return doOnValidateSoapRequest(soapBusinessRequest.getSoapBusinessRequest(), soapStandardDefinition)
            .flatMap(soapResponse -> doOnValidateSoapResponse(soapResponse, soapStandardDefinition, null, true))
            .flatMap(soapRequest -> doOnExecuteSoapMessage(soapRequest, soapStandardDefinition))
            .flatMap(soapResponse -> doOnReturnSoapBusinessResponse((R) soapResponse, soapBusinessRequest))
            .doOnSuccess(success ->
                log.debug("process doOnExecuteSoapService successfully completed, response: {}", success.toString())
            ).doOnError(throwable ->
                log.error("error in process doOnExecuteSoapService, error: {}", throwable.getMessage())
            ).log();
    }

    private <T> Mono<?> doOnValidateSoapRequest(final T soapRequest, final ISoapStandardDefinition soapStandardDefinition) {

        log.debug("soapStandardProviderServiceId {}", this.getServiceIdentification());
        log.debug("{} call doOnValidateSoapRequest method", this.getClass().getName());
        return Mono.just(soapRequest)
            .flatMap(currentSoapRequest -> {
                log.debug("find soap request validator");
                return Mono.just(soapStandardDefinition.validateSoapRequest())
                    .flatMap(currentSoapValidationRequest -> {
                        // apply the current soap request validation
                        final SoapValidationResult soapValidationResult =
                            currentSoapValidationRequest.validateRequest(currentSoapRequest);
                        if (Objects.nonNull(soapValidationResult)
                            && ValidateResult.SUCCESSFULLY_VALID.equals(soapValidationResult.getValidateResult())) {
                            return Mono.just(currentSoapRequest);
                        }
                        return Mono.error(() -> new SoapBusinessProcessException("error in soap request validation, ", ResponseCode.ERROR_DATA_INVALID));
                    });
            })
            .doOnSuccess(success ->
                log.debug("process doOnValidateSoapRequest successfully completed, response: {}", success.toString())
            )
            .doOnError(throwable ->
                log.error("error in process doOnValidateSoapRequest, error: {}", throwable.getMessage())
            );
    }

    private <T> Mono<?> doOnExecuteSoapMessage(final T soapRequest, final ISoapStandardDefinition soapStandardDefinition) {

        log.debug("soapStandardProviderServiceId {}", this.getServiceIdentification());
        log.debug("{} call doOnExecuteSoapMessage method", this.getClass().getName());
        return Mono.just(soapRequest)
            .flatMap(currentSoapRequest -> Mono.just(soapStandardDefinition.buildSoapClientRequest(currentSoapRequest))
                .flatMap(currentNovoSoapClientRequest -> soapClientService.doOnExecuteSoapMessage(currentNovoSoapClientRequest)
                    .flatMap(currentSoapResponse -> Mono.just(currentSoapResponse.getSoapResponse()))))
            .doOnSuccess(success ->
                log.debug("process doOnValidateSoapRequest successfully completed, response: {}", success.toString())
            )
            .doOnError(throwable ->
                log.error("error in process doOnValidateSoapRequest, error: {}", throwable.getMessage())
            );
    }

    private <T, R> Mono<SoapBusinessResponse<R>> doOnReturnSoapBusinessResponse(final R businessResponse, final SoapBusinessRequest<T, R> soapBusinessRequest) {

        log.debug("soapStandardProviderServiceId {}", this.getServiceIdentification());
        log.debug("{} call doOnReturnSoapBusinessResponse method", this.getClass().getName());
        return Mono.just(businessResponse)
            .flatMap(currentBusinessResponse -> {
                final SoapBusinessResponse<R> soapBusinessResponse =
                    createDoOnReturnSoapBusinessResponse(businessResponse, soapBusinessRequest);
                return Mono.just(soapBusinessResponse);
            })
            .doOnSuccess(success ->
                log.debug("process doOnReturnBusinessProcessResponse successfully completed, response: {}", success.toString())
            )
            .doOnError(throwable ->
                log.error("error in process doOnReturnBusinessProcessResponse, error: {}", throwable.getMessage())
            );
    }
}
