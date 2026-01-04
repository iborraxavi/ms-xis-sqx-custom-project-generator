package es.xis.sqx.custom.project.generator.infrastructure.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.mockito.Mockito.*;

import es.xis.sqx.custom.project.generator.application.usecase.list_data.ListDataService;
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

  @Mock private ListDataService listDataService;

  @InjectMocks private DataManagerController dataManagerController;

  @Test
  void listData_whenNoData_shouldReturnOkWithEmptyList() {
    // Given
    when(listDataService.listData()).thenReturn(List.of());

    // When
    final var result = dataManagerController.listData();

    // Then
    assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(result.getBody()).isEmpty();
  }

  @Test
  void listData_whenDataExists_shouldApplyRestMappingToAllItems() {
    // Given
    final int itemCount = 3;
    final List<
            es.xis.sqx.custom.project.generator.application.usecase.list_data.DataRegistryResponse>
        serviceResponses =
            Instancio.ofList(
                    es.xis.sqx.custom.project.generator.application.usecase.list_data
                        .DataRegistryResponse.class)
                .size(itemCount)
                .create();

    final List<DataRegistryResponse> restResponses =
        Instancio.ofList(DataRegistryResponse.class).size(itemCount).create();

    when(listDataService.listData()).thenReturn(serviceResponses);
    for (int i = 0; i < itemCount; i++) {
      when(dataRegistryRestMapper.applicationDataRegistryResponseToRestDataRegistryResponse(
              serviceResponses.get(i)))
          .thenReturn(restResponses.get(i));
    }

    // When
    final var result = dataManagerController.listData();

    // Then
    assertThat(result.getBody()).hasSize(itemCount);
    verify(dataRegistryRestMapper, times(itemCount))
        .applicationDataRegistryResponseToRestDataRegistryResponse(any());
  }

  @Test
  void listData_whenDataExists_shouldMaintainDataIntegrity() {
    // Given
    final Integer id = 1;
    final String symbol = "Symbol 01";
    final String instrument = "Instrument 01";

    final es.xis.sqx.custom.project.generator.application.usecase.list_data.DataRegistryResponse
        serviceResponse =
            Instancio.of(
                    es.xis.sqx.custom.project.generator.application.usecase.list_data
                        .DataRegistryResponse.class)
                .set(
                    field(
                        es.xis.sqx.custom.project.generator.application.usecase.list_data
                                .DataRegistryResponse
                            ::id),
                    id)
                .set(
                    field(
                        es.xis.sqx.custom.project.generator.application.usecase.list_data
                                .DataRegistryResponse
                            ::symbol),
                    symbol)
                .set(
                    field(
                        es.xis.sqx.custom.project.generator.application.usecase.list_data
                                .DataRegistryResponse
                            ::instrument),
                    instrument)
                .create();

    final DataRegistryResponse restResponse =
        Instancio.of(DataRegistryResponse.class)
            .set(field(DataRegistryResponse::id), id)
            .set(field(DataRegistryResponse::symbol), symbol)
            .set(field(DataRegistryResponse::instrument), instrument)
            .create();

    when(listDataService.listData()).thenReturn(List.of(serviceResponse));
    when(dataRegistryRestMapper.applicationDataRegistryResponseToRestDataRegistryResponse(
            serviceResponse))
        .thenReturn(restResponse);

    // When
    final var result = dataManagerController.listData();

    // Then
    assertThat(result.getBody())
        .singleElement()
        .satisfies(
            item -> {
              assertThat(item.id()).isEqualTo(id);
              assertThat(item.symbol()).isEqualTo(symbol);
              assertThat(item.instrument()).isEqualTo(instrument);
            });
  }
}
