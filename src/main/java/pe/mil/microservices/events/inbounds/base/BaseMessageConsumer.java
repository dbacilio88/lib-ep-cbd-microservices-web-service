package pe.mil.microservices.events.inbounds.base;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;
import pe.mil.microservices.constants.CommonLoggerConstants;
import pe.mil.microservices.dtos.TransactionMessage;
import pe.mil.microservices.utils.constants.BaseInterceptorConstants;

import java.util.Map;

@Log4j2
public class BaseMessageConsumer {

    public void processAppSessionContext(final TransactionMessage<?> transactionMessage) {
        try {
            ThreadContext.put(CommonLoggerConstants.KEY_USER_ID, transactionMessage.getTenantId());
            ThreadContext.put(CommonLoggerConstants.KEY_REQUEST_ID, transactionMessage.getRequestId());
            ThreadContext.put(CommonLoggerConstants.KEY_TENANT_ID, transactionMessage.getTenantId());

        } catch (Exception e) {
            log.error("error in process processAppSessionContext, error: {}", e.getMessage());
        }
    }

    public void processAppSessionContext(final Map<String, Object> messageHeaders) {
        try {
            final String tenantId = messageHeaders.get(BaseInterceptorConstants.HEADER_TENANT_ID).toString();
            final String requestId = messageHeaders.get(BaseInterceptorConstants.HEADER_REQUEST_ID  ).toString();
            ThreadContext.put(CommonLoggerConstants.KEY_USER_ID, tenantId);
            ThreadContext.put(CommonLoggerConstants.KEY_REQUEST_ID, requestId);
            ThreadContext.put(CommonLoggerConstants.KEY_TENANT_ID, tenantId);
        } catch (Exception e) {
            log.error("error in process processAppSessionContext, error: {}", e.getMessage());
        }
    }
}
