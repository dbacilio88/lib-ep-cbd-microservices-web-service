package pe.mil.microservices.components.factories.contracts;

import pe.mil.microservices.services.abstractions.contracts.ISoapCustomDefinition;

public interface ISoapCustomDefinitionFactory {

    ISoapCustomDefinition factory(String customDefinitionId);
}
