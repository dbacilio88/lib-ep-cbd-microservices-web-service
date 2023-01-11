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
public class TransactionInformation implements Serializable {

    private static final long serialVersionUID = -3658035238415085131L;

    private String transactionType;
    private String transactionDate;
    private String transactionProcessDate;
}