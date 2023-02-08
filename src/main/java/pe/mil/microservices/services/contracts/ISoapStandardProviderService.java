package pe.mil.microservices.services.contracts;


import pe.mil.microservices.dtos.request.SoapBusinessRequest;
import pe.mil.microservices.dtos.response.SoapBusinessResponse;
import pe.mil.microservices.services.abstractions.contracts.ISoapStandardDefinition;
import reactor.core.publisher.Mono;

public interface ISoapStandardProviderService {

    <T, R> Mono<SoapBusinessResponse<R>> doOnExecuteSoapService(SoapBusinessRequest<T, R> soapBusinessRequest, ISoapStandardDefinition soapStandardDefinition);

}
