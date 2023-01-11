package pe.mil.microservices.dtos.commons;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonBrokerConfiguration implements Serializable {

    private static final long serialVersionUID = 3753566921342599959L;

    private String command;
    private String routingDomain;
    private String routingKeyOrigin;
    private String routingKeyDestiny;
}
