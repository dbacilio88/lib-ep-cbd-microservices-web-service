package pe.mil.microservices.services.contracts;

import pe.mil.microservices.dtos.SagaControl;
import pe.mil.microservices.utils.service.interfaces.IGetDomainEntityById;
import pe.mil.microservices.utils.service.interfaces.ISaveDomainEntity;
import pe.mil.microservices.utils.service.interfaces.IUpdateDomainEntity;
import reactor.core.publisher.Mono;

public interface ISagaControlService
    extends
    ISaveDomainEntity<Mono<SagaControl>, SagaControl>,
    IUpdateDomainEntity<Mono<SagaControl>, SagaControl>,
    IGetDomainEntityById<Mono<SagaControl>, String> {
}
