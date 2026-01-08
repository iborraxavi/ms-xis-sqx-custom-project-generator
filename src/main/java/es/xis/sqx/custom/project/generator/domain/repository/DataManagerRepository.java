package es.xis.sqx.custom.project.generator.domain.repository;

import es.xis.sqx.custom.project.generator.domain.model.DataRegistry;
import es.xis.sqx.custom.project.generator.domain.model.DataRegistryListItem;
import java.util.List;
import java.util.Optional;

public interface DataManagerRepository {

  List<DataRegistryListItem> listData();

  Optional<DataRegistry> findBySymbolName(String symbolName);
}
