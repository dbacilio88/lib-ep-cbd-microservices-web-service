package pe.mil.microservices.components.factories.implementations;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;
import pe.mil.microservices.components.exceptions.SoapBusinessProcessException;
import pe.mil.microservices.components.factories.contracts.ISoapStandardDefinitionFactory;
import pe.mil.microservices.services.abstractions.contracts.ISoapStandardDefinition;
import pe.mil.microservices.utils.components.enums.ResponseCode;

@Log4j2
@Component
public class SoapStandardDefinitionFactory implements ISoapStandardDefinitionFactory {
    private final BeanFactory beanFactory;

    public SoapStandardDefinitionFactory(final BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public ISoapStandardDefinition factory(final String customDefinitionId) {

        try {
            log.debug("customDefinitionId: {}", customDefinitionId);
            return beanFactory.getBean(customDefinitionId, ISoapStandardDefinition.class);
        } catch (BeansException e) {
            log.error("error in process factory ISoapStandardDefinition, error: {}", e.getMessage());
            throw new SoapBusinessProcessException("error in SoapStandardDefinitionFactory, error in factory process", ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }
}
