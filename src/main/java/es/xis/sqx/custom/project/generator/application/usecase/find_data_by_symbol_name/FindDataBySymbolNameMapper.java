package es.xis.sqx.custom.project.generator.application.usecase.find_data_by_symbol_name;

import es.xis.sqx.custom.project.generator.domain.model.DataRegistry;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FindDataBySymbolNameMapper {

  FindDataBySymbolNameResponse dataRegistryToFindDataBySymbolNameResponse(DataRegistry dataRegistry);
}
