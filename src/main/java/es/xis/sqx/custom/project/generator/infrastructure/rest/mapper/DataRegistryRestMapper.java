package es.xis.sqx.custom.project.generator.infrastructure.rest.mapper;

import es.xis.sqx.custom.project.generator.infrastructure.rest.model.DataRegistryResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DataRegistryRestMapper {

  DataRegistryResponse applicationDataRegistryResponseToRestDataRegistryResponse(
      es.xis.sqx.custom.project.generator.application.usecase.list_data.DataRegistryResponse
          dataRegistryResponse);
}
