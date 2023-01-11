package pe.mil.microservices.dtos.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import pe.mil.microservices.dtos.EventInformation;
import pe.mil.microservices.dtos.TransactionInformation;

import java.io.Serializable;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateSagaTransactionalRequest implements Serializable {

    private static final long serialVersionUID = 4704757380719871253L;
    private EventInformation eventInformation;
    private TransactionInformation transactionInformation;
}
