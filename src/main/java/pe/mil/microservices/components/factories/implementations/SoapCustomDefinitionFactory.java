package pe.mil.microservices.components.factories.implementations;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;
import pe.mil.microservices.components.exceptions.SoapBusinessProcessException;
import pe.mil.microservices.components.factories.contracts.ISoapCustomDefinitionFactory;
import pe.mil.microservices.services.abstractions.contracts.ISoapCustomDefinition;
import pe.mil.microservices.utils.components.enums.ResponseCode;

@Component
@Log4j2
public class SoapCustomDefinitionFactory implements ISoapCustomDefinitionFactory {

    private final BeanFactory beanFactory;

    public SoapCustomDefinitionFactory(final BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public ISoapCustomDefinition factory(String customDefinitionId) {

        try {
            log.debug("customDefinitionId: {}", customDefinitionId);
            return beanFactory.getBean(customDefinitionId, ISoapCustomDefinition.class);
        } catch (BeansException e) {
            log.error("error in process factory ISoapCustomDefinition, error: {}", e.getMessage());
            throw new SoapBusinessProcessException("error in SoapCustomDefinitionFactory, error in factory process", ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }
}
