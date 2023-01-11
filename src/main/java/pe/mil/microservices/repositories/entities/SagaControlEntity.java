package pe.mil.microservices.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SagaControlEntity implements Serializable {

    private static final long serialVersionUID = -1908989148077044794L;

    private String messageId;
    private String eventId;
    private String correlationId;
    private String eventPriority;
    private String eventSource;
    private String eventDestiny;
    private String eventState;
    private String eventDestinationQueue;
    private String eventStartTime;
    private Boolean eventCompensatory;
    private Boolean eventPivot;

}
