package es.xis.sqx.custom.project.generator.infrastructure.rest.mapper;

import es.xis.sqx.custom.project.generator.application.usecase.find_data_by_symbol_name.FindDataBySymbolNameResponse;
import es.xis.sqx.custom.project.generator.infrastructure.rest.model.DataRegistryListItemResponse;
import es.xis.sqx.custom.project.generator.infrastructure.rest.model.DataRegistryResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DataRegistryRestMapper {

  DataRegistryListItemResponse
      applicationDataRegistryListItemResponseToRestDataRegistryListItemResponse(
          es.xis.sqx.custom.project.generator.application.usecase.list_data
                  .DataRegistryListItemResponse
              dataRegistryListItemResponse);

  DataRegistryResponse findDataBySymbolNameResponseToDataRegistryResponse(
      FindDataBySymbolNameResponse findDataBySymbolNameResponse);
}
