package es.xis.sqx.custom.project.generator.infrastructure.sqllite.datamanager.jpa;

import es.xis.sqx.custom.project.generator.infrastructure.sqllite.datamanager.model.DataRegistryEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DataRegistryEntityJpaRepository
    extends JpaRepository<DataRegistryEntity, Integer> {

  @Query("SELECT dre FROM DataRegistryEntity dre WHERE dre.usymbol IS NOT NULL ORDER BY dre.symbol")
  List<DataRegistryEntity> findWithoutUsymbolOrdered();
}
