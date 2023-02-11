package pe.mil.microservices.services.abstractions.contracts;

import reactor.core.publisher.Mono;

public interface ISoapGatewayService {

    Mono<Object> doOnExecuteMessage(final Object request, String action);

    Object executeMessage(final Object request, String action);
}
