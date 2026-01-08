package es.xis.sqx.custom.project.generator.infrastructure.sqllite.datamanager.jpa;

import es.xis.sqx.custom.project.generator.infrastructure.sqllite.datamanager.entity.DataRegistryEntity;
import es.xis.sqx.custom.project.generator.infrastructure.sqllite.datamanager.entity.projection.DataRegistryListItemProjection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DataRegistryEntityJpaRepository
    extends JpaRepository<DataRegistryEntity, Integer> {

  @Query(
      "SELECT dre.id AS id, dre.symbol AS symbol FROM DataRegistryEntity dre WHERE dre.usymbol IS NOT NULL ORDER BY dre.symbol")
  List<DataRegistryListItemProjection> findWithUsymbolOrdered();

  @Query(
      "SELECT dre FROM DataRegistryEntity dre JOIN dre.instrument WHERE dre.symbol = :symbolName")
  Optional<DataRegistryEntity> findBySymbolName(final String symbolName);
}
