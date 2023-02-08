package pe.mil.microservices.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SoapClientResponse<R> implements Serializable {
    private static final long serialVersionUID = -4735864889934033586L;
    private R soapResponse;
}
