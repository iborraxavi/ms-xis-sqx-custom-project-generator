package es.xis.sqx.custom.project.generator.infrastructure.sqllite.datamanager.repository;

import es.xis.sqx.custom.project.generator.domain.model.DataRegistry;
import es.xis.sqx.custom.project.generator.domain.model.DataRegistryListItem;
import es.xis.sqx.custom.project.generator.domain.repository.DataManagerRepository;
import es.xis.sqx.custom.project.generator.infrastructure.sqllite.datamanager.jpa.DataRegistryEntityJpaRepository;
import es.xis.sqx.custom.project.generator.infrastructure.sqllite.datamanager.mapper.DataRegistryInfrastructureMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DefaultDataManagerRepository implements DataManagerRepository {

  private final DataRegistryInfrastructureMapper dataRegistryInfrastructureMapper;

  private final DataRegistryEntityJpaRepository dataRegistryEntityJpaRepository;

  @Override
  public List<DataRegistryListItem> listData() {
    return dataRegistryEntityJpaRepository.findWithUsymbolOrdered().stream()
        .map(dataRegistryInfrastructureMapper::dataRegistryListItemProjectionToDataRegistryListItem)
        .toList();
  }

  @Override
  public Optional<DataRegistry> findBySymbolName(final String symbolName) {
    return dataRegistryEntityJpaRepository
        .findBySymbolName(symbolName)
        .map(dataRegistryInfrastructureMapper::dataRegistryEntityToDataRegistry);
  }
}
