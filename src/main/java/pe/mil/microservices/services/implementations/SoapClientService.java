package pe.mil.microservices.services.implementations;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pe.mil.microservices.components.exceptions.SoapBusinessProcessException;
import pe.mil.microservices.components.factories.implementations.SoapServiceGatewayFactory;
import pe.mil.microservices.components.validations.ISoapMutualConfigurationValidation;
import pe.mil.microservices.components.wsdl.SoapValidationResult;
import pe.mil.microservices.dtos.request.SoapClientRequest;
import pe.mil.microservices.dtos.response.SoapClientResponse;
import pe.mil.microservices.services.abstractions.contracts.ISoapSerializerService;
import pe.mil.microservices.services.abstractions.implementations.SoapBaseService;
import pe.mil.microservices.services.abstractions.implementations.SoapGatewayService;
import pe.mil.microservices.services.abstractions.implementations.SoapSerializerService;
import pe.mil.microservices.services.contracts.ISoapClientService;
import pe.mil.microservices.utils.components.enums.ResponseCode;
import pe.mil.microservices.utils.components.enums.ValidateResult;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static pe.mil.microservices.utils.constants.CommonSoapConstants.SOAP_CLIENT_SERVICE_NAME;

@Log4j2
@Service
public class SoapClientService extends SoapBaseService implements ISoapClientService {
    private final SoapServiceGatewayFactory soapServiceGatewayFactory;

    public SoapClientService(final SoapServiceGatewayFactory soapServiceGatewayFactory) {
        super(SOAP_CLIENT_SERVICE_NAME);
        this.soapServiceGatewayFactory = soapServiceGatewayFactory;
    }

    @Override
    public <T, R> Mono<SoapClientResponse<R>> doOnExecuteSoapMessage(final SoapClientRequest<T> soapClientRequest) {

        log.debug("soapClientServiceId {}", this.getServiceIdentification());
        log.debug("{} call doOnExecuteSoapMessage method", this.getClass().getName());
        return doOnFactorySoapGatewayService(soapClientRequest)
            .flatMap(soapGatewayService -> soapGatewayService.doOnExecuteMessage(soapClientRequest.getSoapRequest())
                .flatMap(response -> {

                    final ISoapSerializerService soapSerializerService = new SoapSerializerService();
                    if (Objects.nonNull(soapClientRequest.getCustomJsonDeserializer())) {
                        soapSerializerService.loadCustomJsonDeserializer(soapClientRequest.getCustomJsonDeserializer());
                    }

                    final SoapClientResponse<R> soapClientResponse = new SoapClientResponse<>();
                    if (Objects.nonNull(soapClientRequest.getTypeOfResponse())) {
                        soapClientResponse.setSoapResponse(soapSerializerService.fromObject(response, soapClientRequest.getTypeOfResponse()));
                    } else {
                        soapClientResponse.setSoapResponse(soapSerializerService.fromObject(response, soapClientRequest.getClassOfResponse()));
                    }
                    return Mono.just(soapClientResponse);
                })
            )
            .doOnSuccess(success -> {
                log.debug("process doOnExecuteSoapMessage successfully completed");
                log.debug("process doOnExecuteSoapMessage successfully completed, response: {}", success.toString());
            })
            .doOnError(throwable ->
                log.error("exception error in process doOnExecuteSoapMessage, error: {}", throwable.getMessage())
            );
    }

    private <T> Mono<SoapGatewayService> doOnFactorySoapGatewayService(final SoapClientRequest<T> soapClientRequest) {

        log.debug("soapClientServiceId {}", this.getServiceIdentification());
        log.debug("{} call doOnFactorySoapGatewayService method", this.getClass().getName());
        return Mono.just(soapClientRequest)
            .flatMap(currentSoapClientRequest -> {

                if (Objects.isNull(currentSoapClientRequest.getSoapMutualConfiguration()) || Boolean.FALSE.equals(currentSoapClientRequest.getSoapMutualConfiguration().getMutualEnable())) {
                    return soapServiceGatewayFactory.doOnFactory(soapClientRequest.getConfiguration());
                }

                final SoapValidationResult soapValidationResult = ISoapMutualConfigurationValidation.validateDefinitions().apply(currentSoapClientRequest.getSoapMutualConfiguration());
                if (ValidateResult.NOT_VALID.equals(soapValidationResult.getValidateResult())) {
                    return Mono.error(() -> new SoapBusinessProcessException("error in mutual tls configuration", ResponseCode.ERROR_DATA_INVALID));
                }

                return soapServiceGatewayFactory.doOnFactoryByMutual(currentSoapClientRequest.getConfiguration(), currentSoapClientRequest.getSoapMutualConfiguration());
            })
            .doOnSuccess(success -> {
                log.debug("process doOnFactorySoapGatewayService successfully completed");
                log.debug("process doOnFactorySoapGatewayService successfully completed, response: {}", success.toString());
            })
            .doOnError(throwable ->
                log.error("exception error in process doOnFactorySoapGatewayService, error: {}", throwable.getMessage())
            );
    }

    @Override
    public <T, R> SoapClientResponse<R> executeSoapMessage(SoapClientRequest<T> soapClientRequest) {

        try {

            log.debug("soapClientServiceId {}", this.getServiceIdentification());
            log.debug("{} call executeSoapMessage method", this.getClass().getName());
            final SoapGatewayService soapGatewayService = factorySoapGatewayService(soapClientRequest);
            final Object soapResponse = soapGatewayService.executeMessage(soapClientRequest.getSoapRequest());
            final ISoapSerializerService novoSoapJsonService = new SoapSerializerService();
            if (Objects.nonNull(soapClientRequest.getCustomJsonDeserializer())) {
                novoSoapJsonService.loadCustomJsonDeserializer(soapClientRequest.getCustomJsonDeserializer());
            }

            final SoapClientResponse<R> soapClientResponse = new SoapClientResponse<>();
            if (Objects.nonNull(soapClientRequest.getTypeOfResponse())) {
                soapClientResponse.setSoapResponse(novoSoapJsonService.fromJson(novoSoapJsonService.toJson(soapResponse), soapClientRequest.getTypeOfResponse()));
            } else {
                soapClientResponse.setSoapResponse(novoSoapJsonService.fromJson(novoSoapJsonService.toJson(soapResponse), soapClientRequest.getClassOfResponse()));
            }

            return soapClientResponse;
        } catch (Exception e) {
            log.error("error in process executeSoapMessage, error: {}", e.getMessage());
            throw new SoapBusinessProcessException(String.format("error in process executeSoapMessage, error: %s", e.getMessage()), ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }


    private <T> SoapGatewayService factorySoapGatewayService(final SoapClientRequest<T> soapClientRequest) {

        log.debug("soapClientServiceId {}", this.getServiceIdentification());
        log.debug("{} call factorySoapGatewayService method", this.getClass().getName());
        if (Objects.isNull(soapClientRequest.getSoapMutualConfiguration()) || Boolean.FALSE.equals(soapClientRequest.getSoapMutualConfiguration().getMutualEnable())) {
            return soapServiceGatewayFactory.factory(soapClientRequest.getConfiguration());
        }

        final SoapValidationResult soapValidationResult = ISoapMutualConfigurationValidation.validateDefinitions().apply(soapClientRequest.getSoapMutualConfiguration());
        if (ValidateResult.NOT_VALID.equals(soapValidationResult.getValidateResult())) {
            throw new SoapBusinessProcessException("error in mutual tls configuration, error: %s", ResponseCode.ERROR_DATA_INVALID);
        }

        return soapServiceGatewayFactory.factoryByMutual(soapClientRequest.getConfiguration(), soapClientRequest.getSoapMutualConfiguration());
    }
}
