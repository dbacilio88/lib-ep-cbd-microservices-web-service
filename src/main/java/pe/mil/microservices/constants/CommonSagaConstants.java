package pe.mil.microservices.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CommonSagaConstants {

    public static final String SAGA_COMMON_FIELD_MESSAGE_ID = "messageId";
    public static final String SAGA_COMMON_FIELD_EVENT_ID = "eventId";
    public static final String SAGA_COMMON_FIELD_CORRELATION_ID = "correlationId";
    public static final String SAGA_COMMON_FIELD_EVENT_PRIORITY = "eventPriority";
    public static final String SAGA_COMMON_FIELD_EVENT_SOURCE = "eventSource";
    public static final String SAGA_COMMON_FIELD_EVENT_DESTINY = "eventDestiny";
    public static final String SAGA_COMMON_FIELD_EVENT_STATE = "eventState";
    public static final String SAGA_COMMON_FIELD_EVENT_START_TIME = "eventStartTime";
    public static final String SAGA_COMMON_FIELD_EVENT_TYPE = "eventType";
    public static final String FIELD_TENANT_ID = "tenantId";
    public static final String FIELD_REQUEST_ID = "requestId";
    public static final String ERROR_FORMAT = "error in %s, the field %s is empty";
    public static final String ERROR_NULL_OR_EMPTY_FORMAT = "error in %s, the field %s is empty or null";


    public static final String SWITCH_KEY_DATE_FORMAT = "yyyyMMddHHmmssSSSSSS";
    public static final String SWITCH_KEY_SEPARATOR_FORMAT = "-";
    public static final String EVENT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

}
