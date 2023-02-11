package pe.mil.microservices.components.wsdl;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SoapConfiguration implements Serializable {
    private static final long serialVersionUID = -1287345277521213499L;
    private String serviceUri;
    private String serviceAction;
    private String serviceGeneratorWsdlPath;
}
