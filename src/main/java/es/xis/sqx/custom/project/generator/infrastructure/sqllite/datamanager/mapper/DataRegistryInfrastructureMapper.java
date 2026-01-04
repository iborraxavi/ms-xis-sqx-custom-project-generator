package es.xis.sqx.custom.project.generator.infrastructure.sqllite.datamanager.mapper;

import es.xis.sqx.custom.project.generator.domain.model.DataRegistry;
import es.xis.sqx.custom.project.generator.infrastructure.sqllite.datamanager.model.DataRegistryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DataRegistryInfrastructureMapper {

  DataRegistry dataRegistryEntityToDataRegistry(DataRegistryEntity dataRegistryEntity);
}
