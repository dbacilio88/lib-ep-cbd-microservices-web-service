package pe.mil.microservices.services.abstractions.implementations;

import lombok.extern.log4j.Log4j2;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import reactor.core.publisher.Mono;

@Log4j2
public class SoapGatewayService extends WebServiceGatewaySupport {

    public Mono<Object> doOnExecuteMessage(final Object request) {
        return Mono.just(getWebServiceTemplate().marshalSendAndReceive(request))
            .doOnSuccess(success -> {
                log.debug("process doOnExecuteMessage successfully completed");
                log.debug("process doOnExecuteMessage successfully completed, response: {}", success.toString());
            })
            .doOnError(throwable ->
                log.error("exception error in process doOnExecuteMessage, error: {}", throwable.getMessage())
            );
    }

    public Object executeMessage(final Object request) {
        return getWebServiceTemplate().marshalSendAndReceive(request);
    }
}
