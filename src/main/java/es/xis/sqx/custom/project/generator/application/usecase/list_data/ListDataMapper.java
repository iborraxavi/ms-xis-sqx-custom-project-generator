package es.xis.sqx.custom.project.generator.application.usecase.list_data;

import es.xis.sqx.custom.project.generator.domain.model.DataRegistryListItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ListDataMapper {

  DataRegistryListItemResponse dataRegistryListItemToDataRegistryResponse(
      DataRegistryListItem dataRegistryListItem);
}
