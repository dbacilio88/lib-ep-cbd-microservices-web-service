package pe.mil.microservices.components.factories.contracts;

import pe.mil.microservices.components.wsdl.SoapConfiguration;
import pe.mil.microservices.components.wsdl.SoapMutualConfiguration;
import pe.mil.microservices.services.abstractions.contracts.ISoapGatewayService;
import reactor.core.publisher.Mono;

public interface ISoapServiceGatewayFactory {


    Mono<ISoapGatewayService> doOnFactory(final SoapConfiguration soapConfiguration);

    Mono<ISoapGatewayService> doOnFactoryByMutual(final SoapConfiguration soapConfiguration, final SoapMutualConfiguration soapMutualConfiguration);

    ISoapGatewayService factoryByMutual(final SoapConfiguration soapConfiguration, final SoapMutualConfiguration soapMutualConfiguration);

    ISoapGatewayService factory(final SoapConfiguration soapConfiguration);
}
