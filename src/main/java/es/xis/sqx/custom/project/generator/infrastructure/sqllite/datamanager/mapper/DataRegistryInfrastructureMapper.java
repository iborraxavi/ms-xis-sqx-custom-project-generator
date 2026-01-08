package es.xis.sqx.custom.project.generator.infrastructure.sqllite.datamanager.mapper;

import es.xis.sqx.custom.project.generator.domain.model.DataRegistry;
import es.xis.sqx.custom.project.generator.domain.model.DataRegistryListItem;
import es.xis.sqx.custom.project.generator.infrastructure.sqllite.datamanager.entity.DataRegistryEntity;
import es.xis.sqx.custom.project.generator.infrastructure.sqllite.datamanager.entity.projection.DataRegistryListItemProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DataRegistryInfrastructureMapper {

  @Mapping(source = "instrument.instrument", target = "instrument.name")
  DataRegistry dataRegistryEntityToDataRegistry(DataRegistryEntity dataRegistryEntity);

  DataRegistryListItem dataRegistryListItemProjectionToDataRegistryListItem(
      DataRegistryListItemProjection dataRegistryListItemProjection);
}
