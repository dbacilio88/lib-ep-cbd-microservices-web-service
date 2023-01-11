package pe.mil.microservices.components.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import pe.mil.microservices.constants.CommonSagaConstants;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public enum SagaCommonFields {

    MESSAGE_ID(CommonSagaConstants.SAGA_COMMON_FIELD_MESSAGE_ID, CommonSagaConstants.ERROR_FORMAT),

    EVENT_ID(CommonSagaConstants.SAGA_COMMON_FIELD_EVENT_ID, CommonSagaConstants.ERROR_FORMAT),

    CORRELATION_ID(CommonSagaConstants.SAGA_COMMON_FIELD_CORRELATION_ID, CommonSagaConstants.ERROR_FORMAT),

    EVENT_PRIORITY(CommonSagaConstants.SAGA_COMMON_FIELD_EVENT_PRIORITY, CommonSagaConstants.ERROR_FORMAT),

    EVENT_SOURCE(CommonSagaConstants.SAGA_COMMON_FIELD_EVENT_SOURCE, CommonSagaConstants.ERROR_FORMAT),

    EVENT_DESTINY(CommonSagaConstants.SAGA_COMMON_FIELD_EVENT_DESTINY, CommonSagaConstants.ERROR_FORMAT),

    EVENT_STATE(CommonSagaConstants.SAGA_COMMON_FIELD_EVENT_STATE, CommonSagaConstants.ERROR_FORMAT),

    EVENT_START_TIME(CommonSagaConstants.SAGA_COMMON_FIELD_EVENT_START_TIME, CommonSagaConstants.ERROR_FORMAT);

    private final String fieldId;
    private final String errorFormat;
    private static final Map<String, SagaCommonFields> MAP_SAGA_COMMONS_FIELDS = new HashMap<>();

    static {
        for (SagaCommonFields scf : EnumSet.allOf(SagaCommonFields.class)) {
            MAP_SAGA_COMMONS_FIELDS.put(scf.getFieldId(), scf);
        }
    }

    SagaCommonFields(String fieldId, String errorFormat) {
        this.fieldId = fieldId;
        this.errorFormat = errorFormat;
    }

    @Override
    public String toString() {
        return "SagaCommonFields{" +
            "fieldId='" + fieldId + '\'' +
            ", errorFormat='" + errorFormat + '\'' +
            '}';
    }

    public static Optional<SagaCommonFields> findFieldById(String fieldId) {
        return StringUtils.isNoneBlank(fieldId) ? Optional.of(MAP_SAGA_COMMONS_FIELDS.get(fieldId)) : Optional.empty();
    }

}

