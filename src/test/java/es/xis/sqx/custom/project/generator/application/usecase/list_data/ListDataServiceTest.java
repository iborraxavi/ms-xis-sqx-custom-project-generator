package es.xis.sqx.custom.project.generator.application.usecase.list_data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.mockito.Mockito.*;

import es.xis.sqx.custom.project.generator.domain.model.DataRegistry;
import es.xis.sqx.custom.project.generator.domain.repository.DataManagerRepository;
import java.util.List;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ListDataServiceTest {

  @Mock private ListDataMapper listDataMapper;

  @Mock private DataManagerRepository dataManagerRepository;

  @InjectMocks private ListDataService listDataService;

  @Test
  void listData_whenRepositoryReturnsEmpty_shouldReturnEmptyList() {
    // Arrange
    when(dataManagerRepository.listData()).thenReturn(List.of());

    // Act
    final var result = listDataService.listData();

    // Assert
    assertThat(result).isEmpty();
    verify(dataManagerRepository).listData();
    verifyNoInteractions(listDataMapper);
  }

  @Test
  void listData_whenValidData_shouldMapAllDataRegistriesToResponses() {
    // Arrange
    final List<DataRegistry> dataRegistries =
        Instancio.ofList(DataRegistry.class)
            .size(3)
            .generate(field(DataRegistry::id), generators -> generators.ints().range(1, 1000))
            .generate(
                field(DataRegistry::symbol),
                generators -> generators.string().alphaNumeric().length(30))
            .generate(
                field(DataRegistry::instrument),
                generators -> generators.string().alphaNumeric().length(30))
            .create();

    final List<DataRegistryResponse> expectedResponses =
        Instancio.ofList(DataRegistryResponse.class)
            .size(3)
            .generate(
                field(DataRegistryResponse::id), generators -> generators.ints().range(1, 1000))
            .generate(
                field(DataRegistryResponse::symbol),
                generators -> generators.string().alphaNumeric().length(30))
            .generate(
                field(DataRegistryResponse::instrument),
                generators -> generators.string().alphaNumeric().length(30))
            .create();

    when(dataManagerRepository.listData()).thenReturn(dataRegistries);
    for (int i = 0; i < dataRegistries.size(); i++) {
      when(listDataMapper.dataRegistryToDataRegistryResponse(dataRegistries.get(i)))
          .thenReturn(expectedResponses.get(i));
    }

    // Act
    final var result = listDataService.listData();

    // Assert
    assertThat(result).hasSize(3).containsExactlyElementsOf(expectedResponses);
    for (int i = 0; i < result.size(); i++) {
      assertThat(result.get(i)).isSameAs(expectedResponses.get(i));
    }

    verify(dataManagerRepository).listData();
    dataRegistries.forEach(
        dataRegistry -> verify(listDataMapper).dataRegistryToDataRegistryResponse(dataRegistry));
  }
}
