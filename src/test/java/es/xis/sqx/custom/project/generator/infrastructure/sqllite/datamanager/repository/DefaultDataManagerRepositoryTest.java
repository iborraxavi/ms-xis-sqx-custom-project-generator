package es.xis.sqx.custom.project.generator.infrastructure.sqllite.datamanager.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.mockito.Mockito.*;

import es.xis.sqx.custom.project.generator.domain.model.DataRegistry;
import es.xis.sqx.custom.project.generator.infrastructure.sqllite.datamanager.jpa.DataRegistryEntityJpaRepository;
import es.xis.sqx.custom.project.generator.infrastructure.sqllite.datamanager.mapper.DataRegistryInfrastructureMapper;
import es.xis.sqx.custom.project.generator.infrastructure.sqllite.datamanager.model.DataRegistryEntity;
import java.util.List;
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
    when(dataRegistryEntityJpaRepository.findWithoutUsymbolOrdered()).thenReturn(List.of());

    // Act
    final var result = defaultDataManagerRepository.listData();

    // Assert
    assertThat(result).isEmpty();
    verify(dataRegistryEntityJpaRepository).findWithoutUsymbolOrdered();
    verifyNoInteractions(dataRegistryInfrastructureMapper);
  }

  @Test
  void listData_whenDataExists_shouldReturnMappedData() {
    // Arrange
    final List<DataRegistryEntity> entities =
        Instancio.ofList(DataRegistryEntity.class)
            .size(3)
            .generate(
                field(DataRegistryEntity::getId), generators -> generators.ints().range(1, 1000))
            .generate(
                field(DataRegistryEntity::getSymbol),
                generators -> generators.string().alphaNumeric().length(30))
            .generate(
                field(DataRegistryEntity::getInstrument),
                generators -> generators.string().alphaNumeric().length(30))
            .create();

    final List<DataRegistry> expectedDataRegistries =
        Instancio.ofList(DataRegistry.class).size(3).create();

    when(dataRegistryEntityJpaRepository.findWithoutUsymbolOrdered()).thenReturn(entities);
    for (int i = 0; i < entities.size(); i++) {
      when(dataRegistryInfrastructureMapper.dataRegistryEntityToDataRegistry(entities.get(i)))
          .thenReturn(expectedDataRegistries.get(i));
    }

    // Act
    final var result = defaultDataManagerRepository.listData();

    // Assert
    assertThat(result).hasSize(3).containsExactlyElementsOf(expectedDataRegistries);

    verify(dataRegistryEntityJpaRepository).findWithoutUsymbolOrdered();
    entities.forEach(
        entity ->
            verify(dataRegistryInfrastructureMapper).dataRegistryEntityToDataRegistry(entity));
  }
}
