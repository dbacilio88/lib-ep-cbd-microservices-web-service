package pe.mil.microservices.components.factories.contracts;

import pe.mil.microservices.services.abstractions.contracts.ISoapStandardDefinition;

public interface ISoapStandardDefinitionFactory {

    ISoapStandardDefinition factory(String standardDefinitionId);
}
