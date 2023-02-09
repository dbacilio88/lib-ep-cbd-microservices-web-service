package pe.mil.microservices.dtos.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import pe.mil.microservices.components.wsdl.SoapConfiguration;
import pe.mil.microservices.components.wsdl.SoapMutualConfiguration;
import pe.mil.microservices.services.abstractions.contracts.ICustomJsonDeserializer;


import java.lang.reflect.Type;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SoapClientRequest<T> {
    private ICustomJsonDeserializer customJsonDeserializer;
    private SoapMutualConfiguration soapMutualConfiguration;
    private SoapConfiguration configuration;
    private Class classOfResponse;
    private Type typeOfResponse;
    private T soapRequest;
}
