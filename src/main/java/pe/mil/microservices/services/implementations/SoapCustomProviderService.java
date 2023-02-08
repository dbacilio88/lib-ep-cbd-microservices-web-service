package pe.mil.microservices.services.implementations;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pe.mil.microservices.components.exceptions.SoapBusinessProcessException;
import pe.mil.microservices.components.validations.ISoapBusinessRequestValidation;
import pe.mil.microservices.components.validations.ISoapCustomDefinitionValidation;
import pe.mil.microservices.components.wsdl.SoapValidationResult;
import pe.mil.microservices.dtos.request.SoapBusinessRequest;
import pe.mil.microservices.dtos.response.SoapBusinessResponse;
import pe.mil.microservices.services.abstractions.contracts.ISoapCustomDefinition;
import pe.mil.microservices.services.abstractions.implementations.SoapBaseService;
import pe.mil.microservices.services.contracts.ISoapClientService;
import pe.mil.microservices.services.contracts.ISoapCustomProviderService;
import pe.mil.microservices.utils.components.enums.ResponseCode;
import pe.mil.microservices.utils.components.enums.ValidateResult;

import reactor.core.publisher.Mono;

import java.util.Objects;

import static pe.mil.microservices.utils.constants.CommonSoapConstants.SOAP_CUSTOM_PROVIDER_SERVICE_IDENTIFICATION;
import static pe.mil.microservices.utils.constants.CommonSoapConstants.SOAP_CUSTOM_PROVIDER_SERVICE_NAME;

@Log4j2
@Service(SOAP_CUSTOM_PROVIDER_SERVICE_IDENTIFICATION)
public class SoapCustomProviderService extends SoapBaseService implements ISoapCustomProviderService {

    private final ISoapClientService soapClientService;

    public SoapCustomProviderService(final ISoapClientService soapClientService) {
        super(SOAP_CUSTOM_PROVIDER_SERVICE_NAME);
        this.soapClientService = soapClientService;
    }

    @Override
    public <T, R> Mono<SoapBusinessResponse<R>> doOnExecuteSoapService(SoapBusinessRequest<T, R> soapBusinessRequest, ISoapCustomDefinition soapCustomDefinition) {
        log.debug("soapCustomProviderServiceId {}", this.getServiceIdentification());
        log.debug("{} call doOnExecuteSoapService method", this.getClass().getName());
        final SoapValidationResult soapBusinessRequestValidationResult = ISoapBusinessRequestValidation.validateSoapBusinessRequest()
            .apply(soapBusinessRequest);

        if (ValidateResult.NOT_VALID.equals(soapBusinessRequestValidationResult.getValidateResult())) {
            return Mono.error(() -> new SoapBusinessProcessException("error in soap business request validation", ResponseCode.ERROR_DATA_INVALID));
        }

        final SoapValidationResult soapDefinitionValidationResult = ISoapCustomDefinitionValidation.validateMutualConfiguration().apply(soapCustomDefinition);

        if (ValidateResult.NOT_VALID.equals(soapDefinitionValidationResult.getValidateResult())) {
            return Mono.error(() -> new SoapBusinessProcessException("error in soap business request validation", ResponseCode.ERROR_DATA_INVALID));
        }

        return doOnMapSoapRequest(soapBusinessRequest, soapCustomDefinition)
            .flatMap(soapRequest -> doOnValidateSoapRequest(soapRequest, soapCustomDefinition))
            .flatMap(soapRequest -> doOnExecuteSoapMessage(soapRequest, soapCustomDefinition))
            .flatMap(soapResponse -> doOnValidateSoapResponse(soapResponse, null, soapCustomDefinition, false))
            .flatMap(soapResponse -> doOnMapBusinessResponse(soapResponse, soapCustomDefinition))
            .flatMap(businessResponse -> doOnReturnSoapBusinessResponse((R) businessResponse, soapBusinessRequest))
            .doOnSuccess(success ->
                log.debug("process doOnExecuteSoapService successfully completed, response: {}", success.toString())
            ).doOnError(throwable ->
                log.error("error in process doOnExecuteSoapService, error: {}", throwable.getMessage())
            ).log();
    }

    private <T, R> Mono<?> doOnMapSoapRequest(final SoapBusinessRequest<T, R> soapBusinessRequest, final ISoapCustomDefinition soapCustomDefinition) {

        log.debug("soapCustomProviderServiceId {}", this.getServiceIdentification());
        log.debug("SoapCustomProviderService call doOnMapSoapRequest method");
        return Mono.just(soapCustomDefinition.soapMapperRequest())
            .flatMap(currentSoapMapperRequest -> {
                log.debug("find soap mapping request");
                return Mono.just(currentSoapMapperRequest.mapRequestBySource(soapBusinessRequest.getSoapBusinessRequest()));
            })
            .doOnSuccess(success ->
                log.debug("process doOnMapSoapRequest successfully completed, response: {}", success.toString())
            )
            .doOnError(throwable ->
                log.error("error in process doOnMapSoapRequest, error: {}", throwable.getMessage())
            );
    }

    private <T> Mono<?> doOnValidateSoapRequest(final T soapRequest, final ISoapCustomDefinition soapCustomDefinition) {

        log.debug("soapCustomProviderServiceId {}", this.getServiceIdentification());
        log.debug("SoapCustomProviderService call doOnValidateSoapRequest method");
        return Mono.just(soapRequest)
            .flatMap(currentSoapRequest -> {
                log.debug("find soap request validator");
                return Mono.just(soapCustomDefinition.validateSoapRequest())
                    .flatMap(currentSoapValidationRequest -> {
                        // apply the current soap request validation
                        final SoapValidationResult soapValidationResult = currentSoapValidationRequest.validateRequest(currentSoapRequest);
                        if (Objects.nonNull(soapValidationResult) && ValidateResult.SUCCESSFULLY_VALID.equals(soapValidationResult.getValidateResult())) {
                            return Mono.just(currentSoapRequest);
                        }
                        return Mono.error(() -> new SoapBusinessProcessException("error in soap request validation", ResponseCode.ERROR_DATA_INVALID));
                    });
            })
            .doOnSuccess(success ->
                log.debug("process doOnValidateSoapRequest successfully completed, response: {}", success.toString())
            )
            .doOnError(throwable ->
                log.error("error in process doOnValidateSoapRequest, error: {}", throwable.getMessage())
            );
    }

    private <T> Mono<?> doOnExecuteSoapMessage(final T soapRequest, final ISoapCustomDefinition soapCustomDefinition) {

        log.debug("soapCustomProviderServiceId {}", this.getServiceIdentification());
        log.debug("SoapCustomProviderService call doOnExecuteSoapMessage method");
        return Mono.just(soapRequest)
            .flatMap(currentSoapRequest -> Mono.just(soapCustomDefinition.buildNovoSoapClientRequest(currentSoapRequest))
                .flatMap(currentNovoSoapClientRequest -> soapClientService.doOnExecuteSoapMessage(currentNovoSoapClientRequest)
                    .flatMap(currentSoapResponse -> Mono.just(currentSoapResponse.getSoapResponse()))))
            .doOnSuccess(success ->
                log.debug("process doOnExecuteSoapMessage successfully completed, response: {}", success.toString())
            )
            .doOnError(throwable ->
                log.error("error in process doOnExecuteSoapMessage, error: {}", throwable.getMessage())
            );
    }

    private <T> Mono<?> doOnMapBusinessResponse(final T soapResponse, final ISoapCustomDefinition soapClientCustomService) {

        log.debug("soapCustomProviderServiceId {}", this.getServiceIdentification());
        log.debug("SoapCustomProviderService call doOnMapBusinessResponse method");
        return Mono.just(soapResponse)
            .flatMap(novoSoapClientResponse -> Mono.just(soapClientCustomService.soapMapperResponse())
                .flatMap(currentSoapMapperResponse -> Mono.just(currentSoapMapperResponse.mapResponseBySource(soapResponse))))
            .doOnSuccess(success ->
                log.debug("process doOnMapBusinessResponse successfully completed, response: {}", success.toString())
            )
            .doOnError(throwable ->
                log.error("error in process doOnMapBusinessResponse, error: {}", throwable.getMessage())
            );
    }

    private <T, R> Mono<SoapBusinessResponse<R>> doOnReturnSoapBusinessResponse(final R businessResponse, final SoapBusinessRequest<T, R> soapBusinessRequest) {

        log.debug("soapCustomProviderServiceId {}", this.getServiceIdentification());
        log.debug("SoapCustomProviderService call doOnReturnSoapBusinessResponse method");
        return Mono.just(businessResponse)
            .flatMap(currentBusinessResponse -> {
                final SoapBusinessResponse<R> soapBusinessResponse = createDoOnReturnSoapBusinessResponse(businessResponse, soapBusinessRequest);
                return Mono.just(soapBusinessResponse);
            })
            .doOnSuccess(success ->
                log.debug("process doOnReturnSoapBusinessResponse successfully completed, response: {}", success.toString())
            )
            .doOnError(throwable ->
                log.error("error in process doOnReturnSoapBusinessResponse, error: {}", throwable.getMessage())
            );
    }
}
