package pe.mil.microservices.dtos.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import pe.mil.microservices.utils.dtos.base.BaseBusinessResponseDto;
import javax.json.bind.annotation.JsonbProperty;
import java.io.Serializable;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericBusinessResponse<T> extends BaseBusinessResponseDto implements Serializable {

    private static final long serialVersionUID = -2515878949125281665L;
    @JsonbProperty("data")
    @JsonProperty("data")
    private T data;
}
