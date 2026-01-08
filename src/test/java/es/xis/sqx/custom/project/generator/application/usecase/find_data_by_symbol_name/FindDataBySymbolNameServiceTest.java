package es.xis.sqx.custom.project.generator.application.usecase.find_data_by_symbol_name;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import es.xis.sqx.custom.project.generator.domain.exceptions.NotFoundException;
import es.xis.sqx.custom.project.generator.domain.model.DataRegistry;
import es.xis.sqx.custom.project.generator.domain.repository.DataManagerRepository;
import java.util.Optional;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FindDataBySymbolNameServiceTest {

  @Mock private FindDataBySymbolNameMapper findDataBySymbolNameMapper;

  @Mock private DataManagerRepository dataManagerRepository;

  @InjectMocks private FindDataBySymbolNameService findDataBySymbolNameService;

  @Test
  void findDataBySymbolName_whenSuccess_shouldFindDataBySymbolNameSuccessfully() {
    // Arrange
    final String symbolName = Instancio.create(String.class);
    final DataRegistry dataRegistry = Instancio.create(DataRegistry.class);
    final FindDataBySymbolNameResponse expectedResponse =
        Instancio.create(FindDataBySymbolNameResponse.class);

    when(dataManagerRepository.findBySymbolName(symbolName)).thenReturn(Optional.of(dataRegistry));
    when(findDataBySymbolNameMapper.dataRegistryToFindDataBySymbolNameResponse(dataRegistry))
        .thenReturn(expectedResponse);

    // Act
    final var result = findDataBySymbolNameService.findDataBySymbolName(symbolName);

    // Assert
    assertThat(result).isEqualTo(expectedResponse);

    verify(dataManagerRepository).findBySymbolName(symbolName);
    verify(findDataBySymbolNameMapper).dataRegistryToFindDataBySymbolNameResponse(dataRegistry);
  }

  @Test
  void findDataBySymbolName_whenNotFound_shouldThrowExpectedException() {
    // Arrange
    final String symbolName = Instancio.create(String.class);

    when(dataManagerRepository.findBySymbolName(symbolName)).thenReturn(Optional.empty());

    // Act & Assert
    assertThatThrownBy(() -> findDataBySymbolNameService.findDataBySymbolName(symbolName))
        .isInstanceOf(NotFoundException.class)
        .hasMessage("Data registry not found for symbol name: " + symbolName);

    verify(dataManagerRepository).findBySymbolName(symbolName);
    verifyNoInteractions(findDataBySymbolNameMapper);
  }
}
