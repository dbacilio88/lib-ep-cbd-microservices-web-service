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
public class EventInformation implements Serializable {

    private static final long serialVersionUID = 7056341336275268224L;

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
    private String eventType;
}
