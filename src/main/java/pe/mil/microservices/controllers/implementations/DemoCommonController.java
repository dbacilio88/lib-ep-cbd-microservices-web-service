package pe.mil.microservices.controllers.implementations;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;
import pe.mil.microservices.constants.CommonProcessConstants;
import pe.mil.microservices.controllers.contracts.IDemoCommonController;
import reactor.core.publisher.Mono;

@Log4j2
@RestController
public class DemoCommonController implements IDemoCommonController {

    @Override
    @GetMapping(path = CommonProcessConstants.COMMON_CREATE_ACCOUNT_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> doOnCreateAccount() {

        return Mono.just(new ResponseEntity<>(HttpStatus.OK))
            .onErrorResume(WebExchangeBindException.class, Mono::error)
            .doOnSuccess(success -> log.debug("finish process doOnCreateAccount, response: {}", success))
            .doOnError(throwable -> log.error("exception error in process doOnCreateAccount, error: {}", throwable.getMessage()))
            .log();
    }
}
