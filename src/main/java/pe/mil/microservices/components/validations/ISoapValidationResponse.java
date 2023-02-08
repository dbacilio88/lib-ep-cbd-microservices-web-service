package pe.mil.microservices.components.validations;

import pe.mil.microservices.components.wsdl.SoapValidationResult;

public interface ISoapValidationResponse<T> {

    SoapValidationResult validateResponse(T response);
}
