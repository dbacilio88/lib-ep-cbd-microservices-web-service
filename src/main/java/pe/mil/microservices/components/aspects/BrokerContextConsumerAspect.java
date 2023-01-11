package pe.mil.microservices.components.aspects;

import ep.mil.microservices.dtos.Event;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import pe.mil.microservices.constants.CommonLoggerConstants;
import pe.mil.microservices.utils.constants.BaseInterceptorConstants;

import java.lang.reflect.Method;

@Log4j2
@Aspect
@Component
public class BrokerContextConsumerAspect {


    @Around("@annotation(pe.mil.microservices.components.aspects.BrokerContextConsumer)")
    public Object processAppSessionContext(ProceedingJoinPoint joinPoint) throws Throwable {
        try {

            final MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            final Method method = signature.getMethod();

            log.debug("create ThreadContext from method: {}", method.toString());

            final Event<?> currentEventBroker = (Event<?>) joinPoint.proceed();
            final String tenantId = currentEventBroker.getMessageProperties().getHeaders().get(BaseInterceptorConstants.HEADER_TENANT_ID).toString();
            final String requestId = currentEventBroker.getMessageProperties().getHeaders().get(BaseInterceptorConstants.HEADER_REQUEST_ID).toString();

            ThreadContext.put(CommonLoggerConstants.KEY_USER_ID, tenantId);
            ThreadContext.put(CommonLoggerConstants.KEY_REQUEST_ID, requestId);
            ThreadContext.put(CommonLoggerConstants.KEY_TENANT_ID, tenantId);

        } catch (Exception e) {
            log.error("error in process processAppSessionContext, error: {}", e.getMessage());
        }
        return joinPoint.proceed();
    }
}
