package pe.mil.microservices.components.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pe.mil.microservices.utils.components.enums.ResponseCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BrokerConfigureException extends RuntimeException {

    private static final long serialVersionUID = 1412040912143293549L;

    private ResponseCode responseCode;

    public BrokerConfigureException(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public BrokerConfigureException(String message) {
        super(message);
    }

    public BrokerConfigureException(String message, ResponseCode responseCode) {
        super(message);
        this.responseCode = responseCode;
    }

    public BrokerConfigureException(String message, ResponseCode responseCode, Throwable throwable) {
        super(message, throwable);
        this.responseCode = responseCode;
    }
}
