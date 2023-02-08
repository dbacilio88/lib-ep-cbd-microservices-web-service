package pe.mil.microservices.dtos.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SoapBusinessRequest<T, R> implements Serializable {
    private static final long serialVersionUID = 6497331675404969223L;
    private String soapDefinitionId;
    private T soapBusinessRequest;
    private R soapBusinessResponse;
}
