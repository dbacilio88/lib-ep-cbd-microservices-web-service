package pe.mil.microservices.components.mappers;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import pe.mil.microservices.dtos.SagaControl;
import pe.mil.microservices.repositories.entities.SagaControlEntity;
import pe.mil.microservices.utils.service.interfaces.IObjectMapperEntity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SagaControlEntityMapper
    implements IObjectMapperEntity<SagaControl, SagaControlEntity> {


    private static final long serialVersionUID = -2355636518965515444L;

    @Override
    public SagaControlEntity map(SagaControl source) {
        final ModelMapper mapper = getMapper();
        return mapper.map(source, SagaControlEntity.class);
    }

    @Override
    public List<SagaControlEntity> mapAll(Collection<SagaControl> source) {
        return source.stream().map(this::map).collect(Collectors.toList());
    }


    private ModelMapper getMapper() {
        final ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.typeMap(SagaControl.class, SagaControlEntity.class);
        return mapper;
    }


}
