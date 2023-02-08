package pe.mil.microservices.services.contracts;

import pe.mil.microservices.dtos.request.SoapBusinessRequest;
import pe.mil.microservices.dtos.response.SoapBusinessResponse;
import pe.mil.microservices.services.abstractions.contracts.ISoapCustomDefinition;
import reactor.core.publisher.Mono;

public interface ISoapCustomProviderService {

    <T, R> Mono<SoapBusinessResponse<R>> doOnExecuteSoapService(SoapBusinessRequest<T, R> soapBusinessRequest, ISoapCustomDefinition soapCustomDefinition);
}
