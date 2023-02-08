package pe.mil.microservices.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SoapBusinessResponse<R> implements Serializable {
    private static final long serialVersionUID = 8654166852426030198L;
    private String soapDefinitionId;
    private R soapBusinessResponse;
}
