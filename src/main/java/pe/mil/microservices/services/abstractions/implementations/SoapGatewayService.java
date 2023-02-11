package pe.mil.microservices.services.abstractions.implementations;

import lombok.extern.log4j.Log4j2;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import pe.mil.microservices.services.abstractions.contracts.ISoapGatewayService;
import reactor.core.publisher.Mono;

@Log4j2
public class SoapGatewayService extends WebServiceGatewaySupport implements ISoapGatewayService {
    @Override
    public Mono<Object> doOnExecuteMessage(Object request, String action) {
        return Mono.just(getWebServiceTemplate()
                .marshalSendAndReceive(request, new SoapActionCallback(action)))
            .doOnSuccess(success -> {
                log.debug("process doOnExecuteMessage successfully completed");
                log.debug("process doOnExecuteMessage successfully completed, response: {}", success.toString());
            })
            .doOnError(throwable ->
                log.error("exception error in process doOnExecuteMessage, error: {}", throwable.getMessage())
            );
    }

    @Override
    public Object executeMessage(Object request, String action) {
        return getWebServiceTemplate().marshalSendAndReceive(request, new SoapActionCallback(action));
    }

}
