package pe.mil.microservices.services.abstractions.contracts;

import pe.mil.microservices.components.mappers.contracts.ISoapMapperRequest;
import pe.mil.microservices.components.mappers.contracts.ISoapMapperResponse;
import pe.mil.microservices.components.validations.ISoapValidationRequest;
import pe.mil.microservices.components.validations.ISoapValidationResponse;
import pe.mil.microservices.dtos.request.SoapClientRequest;


public interface ISoapCustomDefinition {

    <BT, ST> ISoapMapperRequest<BT, ST> soapMapperRequest();

    <SR, BR> ISoapMapperResponse<SR, BR> soapMapperResponse();

    <ST> ISoapValidationRequest<ST> validateSoapRequest();

    <SR> ISoapValidationResponse<SR> validateSoapResponse();

    <ST> SoapClientRequest<ST> buildNovoSoapClientRequest(ST soapRequest);
}
