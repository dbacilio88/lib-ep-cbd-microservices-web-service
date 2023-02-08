package pe.mil.microservices.components.wsdl;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import pe.mil.microservices.utils.components.enums.ValidateResult;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SoapValidationResult implements Serializable {
    private static final long serialVersionUID = -4204474024123516457L;

    private ValidateResult validateResult;
    private List<String> errors;
}
