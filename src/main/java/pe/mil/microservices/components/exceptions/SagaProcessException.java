package pe.mil.microservices.components.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SagaProcessException extends RuntimeException {


    public SagaProcessException(String message) {
        super(message);
    }
}
