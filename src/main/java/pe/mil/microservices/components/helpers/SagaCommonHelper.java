package pe.mil.microservices.components.helpers;

import lombok.experimental.UtilityClass;

import java.text.SimpleDateFormat;
import java.util.Date;

import static pe.mil.microservices.constants.CommonSagaConstants.*;

@UtilityClass
public class SagaCommonHelper {

    public static String createTimestamp(final Date currentEventDate) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(SWITCH_KEY_DATE_FORMAT);
        return dateFormat.format(currentEventDate);
    }

    public static String createEventDate(final Date currentEventDate) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(EVENT_DATE_FORMAT);
        return dateFormat.format(currentEventDate);
    }

    public static String createMessageId(String transacctionId, String timestamp) {
        return transacctionId.concat(SWITCH_KEY_SEPARATOR_FORMAT).concat(timestamp);
    }

    public static String createCorrelationId(String messageId, String requestId) {
        return messageId.concat(SWITCH_KEY_SEPARATOR_FORMAT).concat(requestId);
    }
}
