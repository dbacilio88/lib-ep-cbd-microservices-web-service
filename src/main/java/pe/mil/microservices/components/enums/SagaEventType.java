package pe.mil.microservices.components.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public enum SagaEventType {

    EVENT_TYPE_INBOUND("", ""),
    EVENT_TYPE_OUTBOUND("", "");

    private static final Map<String, SagaEventType> MAP_SAGA_EVENT_TYPE = new HashMap<>();

    private final String eventCode;
    private final String eventDescription;

    static {
        for (SagaEventType set : EnumSet.allOf(SagaEventType.class)) {
            MAP_SAGA_EVENT_TYPE.put(set.getEventCode(), set);
        }
    }

    SagaEventType(final String eventCode, final String eventDescription) {
        this.eventCode = eventCode;
        this.eventDescription = eventDescription;
    }

    @Override
    public String toString() {
        return "SagaEventType{" +
            "eventCode='" + eventCode + '\'' +
            ", eventDescription='" + eventDescription + '\'' +
            '}';
    }

    public static Optional<SagaEventType> findEventByCode(final String eventCode) {
        return StringUtils.isNoneBlank(eventCode) ? Optional.of(MAP_SAGA_EVENT_TYPE.get(eventCode)) : Optional.empty();
    }
}
