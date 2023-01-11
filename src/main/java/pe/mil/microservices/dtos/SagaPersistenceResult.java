package pe.mil.microservices.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import pe.mil.microservices.components.enums.SagaResultValidation;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SagaPersistenceResult implements Serializable {

    private static final long serialVersionUID = 751809722138783883L;

    private SagaResultValidation sagaResultValidation;

    private List<String> errors;
}
