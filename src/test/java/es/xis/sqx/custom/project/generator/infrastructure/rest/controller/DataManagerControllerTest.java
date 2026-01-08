package es.xis.sqx.custom.project.generator.infrastructure.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.instancio.Select.field;
import static org.mockito.Mockito.*;

import es.xis.sqx.custom.project.generator.application.usecase.find_data_by_symbol_name.FindDataBySymbolNameResponse;
import es.xis.sqx.custom.project.generator.application.usecase.find_data_by_symbol_name.FindDataBySymbolNameService;
import es.xis.sqx.custom.project.generator.application.usecase.list_data.DataRegistryListItemResponse;
import es.xis.sqx.custom.project.generator.application.usecase.list_data.ListDataService;
import es.xis.sqx.custom.project.generator.domain.exceptions.NotFoundException;
import es.xis.sqx.custom.project.generator.infrastructure.rest.mapper.DataRegistryRestMapper;
import es.xis.sqx.custom.project.generator.infrastructure.rest.model.DataRegistryResponse;
import java.util.List;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class DataManagerControllerTest {

  @Mock private DataRegistryRestMapper dataRegistryRestMapper;

  @Mock private FindDataBySymbolNameService findDataBySymbolNameService;
  @Mock private ListDataService listDataService;

  @InjectMocks private DataManagerController dataManagerController;

  @Test
  void listData_whenNoData_shouldReturnOkWithEmptyList() {
    // Arrange
    when(listDataService.listData()).thenReturn(List.of());

    // Act
    final var result = dataManagerController.listData();

    // Assert
    assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(result.getBody()).isEmpty();
  }

  @Test
  void listData_whenDataExists_shouldApplyRestMappingToAllItems() {
    // Arrange
    final int itemCount = 3;
    final List<DataRegistryListItemResponse> serviceResponses =
        Instancio.ofList(DataRegistryListItemResponse.class).size(itemCount).create();

    final List<
            es.xis.sqx.custom.project.generator.infrastructure.rest.model
                .DataRegistryListItemResponse>
        restResponses =
            Instancio.ofList(
                    es.xis.sqx.custom.project.generator.infrastructure.rest.model
                        .DataRegistryListItemResponse.class)
                .size(itemCount)
                .create();

    when(listDataService.listData()).thenReturn(serviceResponses);
    for (int i = 0; i < itemCount; i++) {
      when(dataRegistryRestMapper
              .applicationDataRegistryListItemResponseToRestDataRegistryListItemResponse(
                  serviceResponses.get(i)))
          .thenReturn(restResponses.get(i));
    }

    // Act
    final var result = dataManagerController.listData();

    // Assert
    assertThat(result.getBody()).hasSize(itemCount);
    verify(dataRegistryRestMapper, times(itemCount))
        .applicationDataRegistryListItemResponseToRestDataRegistryListItemResponse(any());
  }

  @Test
  void listData_whenDataExists_shouldMaintainDataIntegrity() {
    // Arrange
    final Integer id = Instancio.create(Integer.class);
    final String symbol = Instancio.create(String.class);

    final DataRegistryListItemResponse serviceResponse =
        Instancio.of(DataRegistryListItemResponse.class)
            .set(field(DataRegistryListItemResponse::id), id)
            .set(field(DataRegistryListItemResponse::symbol), symbol)
            .create();

    final es.xis.sqx.custom.project.generator.infrastructure.rest.model.DataRegistryListItemResponse
        restResponse =
            Instancio.of(
                    es.xis.sqx.custom.project.generator.infrastructure.rest.model
                        .DataRegistryListItemResponse.class)
                .set(
                    field(
                        es.xis.sqx.custom.project.generator.infrastructure.rest.model
                                .DataRegistryListItemResponse
                            ::id),
                    id)
                .set(
                    field(
                        es.xis.sqx.custom.project.generator.infrastructure.rest.model
                                .DataRegistryListItemResponse
                            ::symbol),
                    symbol)
                .create();

    when(listDataService.listData()).thenReturn(List.of(serviceResponse));
    when(dataRegistryRestMapper
            .applicationDataRegistryListItemResponseToRestDataRegistryListItemResponse(
                serviceResponse))
        .thenReturn(restResponse);

    // Act
    final var result = dataManagerController.listData();

    // Assert
    assertThat(result.getBody())
        .singleElement()
        .satisfies(
            item -> {
              assertThat(item.id()).isEqualTo(id);
              assertThat(item.symbol()).isEqualTo(symbol);
            });
  }

  @Test
  void getDataBySymbolName_whenNoData_shouldReturnExpectedError() {
    // Arrange
    final String symbolName = Instancio.create(String.class);

    when(findDataBySymbolNameService.findDataBySymbolName(symbolName))
        .thenThrow(NotFoundException.class);

    // Act & assert
    assertThatThrownBy(() -> dataManagerController.getDataBySymbolName(symbolName))
        .isInstanceOf(NotFoundException.class);

    verify(findDataBySymbolNameService, only()).findDataBySymbolName(symbolName);
    verifyNoInteractions(dataRegistryRestMapper);
  }

  @Test
  void getDataBySymbolName_whenSuccess_shouldReturnExpectedData() {
    // Arrange
    final String symbolName = Instancio.create(String.class);
    final FindDataBySymbolNameResponse applicationFindDataBySymbolNameResponse =
        Instancio.create(FindDataBySymbolNameResponse.class);
    final DataRegistryResponse dataRegistryResponse = Instancio.create(DataRegistryResponse.class);

    when(findDataBySymbolNameService.findDataBySymbolName(symbolName))
        .thenReturn(applicationFindDataBySymbolNameResponse);
    when(dataRegistryRestMapper.findDataBySymbolNameResponseToDataRegistryResponse(
            applicationFindDataBySymbolNameResponse))
        .thenReturn(dataRegistryResponse);

    // Act
    final var result = dataManagerController.getDataBySymbolName(symbolName);

    // Assert
    assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(result.getBody())
        .satisfies(
            item -> {
              assertThat(item).isNotNull();
              assertThat(item.id()).isNotNull();
              assertThat(item.symbol()).isNotNull();
            });

    verify(findDataBySymbolNameService, only()).findDataBySymbolName(symbolName);
    verify(dataRegistryRestMapper, only())
        .findDataBySymbolNameResponseToDataRegistryResponse(
            applicationFindDataBySymbolNameResponse);
  }
}
