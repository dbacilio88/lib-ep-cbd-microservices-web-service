package pe.mil.microservices.services.abstractions.contracts;


import pe.mil.microservices.components.validations.ISoapValidationRequest;
import pe.mil.microservices.components.validations.ISoapValidationResponse;
import pe.mil.microservices.dtos.request.SoapClientRequest;

public interface ISoapStandardDefinition {

    <ST> ISoapValidationRequest<ST> validateSoapRequest();

    <SR> ISoapValidationResponse<SR> validateSoapResponse();

    <ST> SoapClientRequest<ST> buildNovoSoapClientRequest(ST soapRequest);
}
