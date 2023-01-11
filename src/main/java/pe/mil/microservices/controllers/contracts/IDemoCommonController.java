package pe.mil.microservices.controllers.contracts;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import pe.mil.microservices.constants.CommonProcessConstants;
import reactor.core.publisher.Mono;


@Validated
@RequestMapping(path = CommonProcessConstants.MICROSERVICE_PATH_CONTEXT, produces = MediaType.APPLICATION_JSON_VALUE)
public interface IDemoCommonController {

    Mono<ResponseEntity<Object>> doOnCreateAccount();

}
