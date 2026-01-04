package es.xis.sqx.custom.project.generator.application.usecase.list_data;

import es.xis.sqx.custom.project.generator.domain.model.DataRegistry;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ListDataMapper {

  DataRegistryResponse dataRegistryToDataRegistryResponse(DataRegistry dataRegistry);
}
