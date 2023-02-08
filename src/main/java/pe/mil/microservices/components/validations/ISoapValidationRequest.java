package pe.mil.microservices.components.validations;

import pe.mil.microservices.components.wsdl.SoapValidationResult;

public interface ISoapValidationRequest<T> {

    SoapValidationResult validateRequest(T request);

}
