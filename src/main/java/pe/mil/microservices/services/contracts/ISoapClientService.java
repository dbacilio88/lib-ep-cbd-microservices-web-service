package pe.mil.microservices.services.contracts;

import pe.mil.microservices.dtos.request.SoapClientRequest;
import pe.mil.microservices.dtos.response.SoapClientResponse;
import reactor.core.publisher.Mono;

public interface ISoapClientService {

    <T, R> Mono<SoapClientResponse<R>> doOnExecuteSoapMessage(SoapClientRequest<T> soapClientRequest);

    <T, R> SoapClientResponse<R> executeSoapMessage(SoapClientRequest<T> soapClientRequest);
}
