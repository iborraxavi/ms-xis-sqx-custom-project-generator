package es.xis.sqx.custom.project.generator.infrastructure.sqllite.datamanager.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import es.xis.sqx.custom.project.generator.domain.model.DataRegistry;
import es.xis.sqx.custom.project.generator.domain.model.DataRegistryListItem;
import es.xis.sqx.custom.project.generator.infrastructure.sqllite.datamanager.entity.DataRegistryEntity;
import es.xis.sqx.custom.project.generator.infrastructure.sqllite.datamanager.entity.projection.DataRegistryListItemProjection;
import es.xis.sqx.custom.project.generator.infrastructure.sqllite.datamanager.jpa.DataRegistryEntityJpaRepository;
import es.xis.sqx.custom.project.generator.infrastructure.sqllite.datamanager.mapper.DataRegistryInfrastructureMapper;
import java.util.List;
import java.util.Optional;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DefaultDataManagerRepositoryTest {

  @Mock private DataRegistryInfrastructureMapper dataRegistryInfrastructureMapper;

  @Mock private DataRegistryEntityJpaRepository dataRegistryEntityJpaRepository;

  @InjectMocks private DefaultDataManagerRepository defaultDataManagerRepository;

  @Test
  void listData_whenNoDataInRepository_shouldReturnEmptyList() {
    // Arrange
    when(dataRegistryEntityJpaRepository.findWithUsymbolOrdered()).thenReturn(List.of());

    // Act
    final var result = defaultDataManagerRepository.listData();

    // Assert
    assertThat(result).isEmpty();
    verify(dataRegistryEntityJpaRepository).findWithUsymbolOrdered();
    verifyNoInteractions(dataRegistryInfrastructureMapper);
  }

  @Test
  void listData_whenDataExists_shouldReturnMappedData() {
    // Arrange
    final DataRegistryListItemProjection firstDataRegistryListItemProjection =
        mock(DataRegistryListItemProjection.class);
    final DataRegistryListItemProjection secondDataRegistryListItemProjection =
        mock(DataRegistryListItemProjection.class);
    final DataRegistryListItemProjection thirdDataRegistryListItemProjection =
        mock(DataRegistryListItemProjection.class);
    final List<DataRegistryListItemProjection> entities =
        List.of(
            firstDataRegistryListItemProjection,
            secondDataRegistryListItemProjection,
            thirdDataRegistryListItemProjection);

    final List<DataRegistryListItem> expectedDataRegistries =
        Instancio.ofList(DataRegistryListItem.class).size(3).create();

    when(dataRegistryEntityJpaRepository.findWithUsymbolOrdered()).thenReturn(entities);
    for (int i = 0; i < entities.size(); i++) {
      when(dataRegistryInfrastructureMapper.dataRegistryListItemProjectionToDataRegistryListItem(
              entities.get(i)))
          .thenReturn(expectedDataRegistries.get(i));
    }

    // Act
    final var result = defaultDataManagerRepository.listData();

    // Assert
    assertThat(result).hasSize(3).containsExactlyElementsOf(expectedDataRegistries);

    verify(dataRegistryEntityJpaRepository).findWithUsymbolOrdered();
    entities.forEach(
        entity ->
            verify(dataRegistryInfrastructureMapper)
                .dataRegistryListItemProjectionToDataRegistryListItem(entity));
  }

  @Test
  void findBySymbolName_whenNoDataInRepository_shouldReturnEmptyOptional() {
    // Arrange
    final String symbolName = Instancio.create(String.class);

    when(dataRegistryEntityJpaRepository.findBySymbolName(symbolName)).thenReturn(Optional.empty());

    // Act
    final var result = defaultDataManagerRepository.findBySymbolName(symbolName);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.isEmpty()).isEqualTo(true);

    verify(dataRegistryEntityJpaRepository, only()).findBySymbolName(symbolName);
    verifyNoInteractions(dataRegistryInfrastructureMapper);
  }

  @Test
  void findBySymbolName_whenDataExists_shouldReturnMappedData() {
    // Arrange
    final String symbolName = Instancio.create(String.class);
    final DataRegistryEntity dataRegistryEntity = Instancio.create(DataRegistryEntity.class);
    final DataRegistry dataRegistry = Instancio.create(DataRegistry.class);

    when(dataRegistryEntityJpaRepository.findBySymbolName(symbolName))
        .thenReturn(Optional.of(dataRegistryEntity));
    when(dataRegistryInfrastructureMapper.dataRegistryEntityToDataRegistry(dataRegistryEntity))
        .thenReturn(dataRegistry);

    // Act
    final var result = defaultDataManagerRepository.findBySymbolName(symbolName);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.isEmpty()).isEqualTo(false);

    verify(dataRegistryEntityJpaRepository, only()).findBySymbolName(symbolName);
    verify(dataRegistryInfrastructureMapper, only())
        .dataRegistryEntityToDataRegistry(dataRegistryEntity);
  }
}
