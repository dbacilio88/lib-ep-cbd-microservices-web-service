package pe.mil.microservices.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionMessage<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = -6658035138415085131L;

    private String messageId;
    private String correlationId;
    private String tenantId;
    private String requestId;
    private String eventSource;
    private String eventState;
    private String transactionType;
    private T data;
}
