package es.xis.sqx.custom.project.generator.infrastructure.rest.controller;

import es.xis.sqx.custom.project.generator.application.usecase.find_data_by_symbol_name.FindDataBySymbolNameResponse;
import es.xis.sqx.custom.project.generator.application.usecase.find_data_by_symbol_name.FindDataBySymbolNameService;
import es.xis.sqx.custom.project.generator.application.usecase.list_data.ListDataService;
import es.xis.sqx.custom.project.generator.infrastructure.rest.mapper.DataRegistryRestMapper;
import es.xis.sqx.custom.project.generator.infrastructure.rest.model.DataRegistryListItemResponse;
import es.xis.sqx.custom.project.generator.infrastructure.rest.model.DataRegistryResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/data-manager/v1")
@RestController
public class DataManagerController {

  private final DataRegistryRestMapper dataRegistryRestMapper;

  private final FindDataBySymbolNameService findDataBySymbolNameService;
  private final ListDataService listDataService;

  @GetMapping("/data")
  public ResponseEntity<List<DataRegistryListItemResponse>> listData() {
    final List<DataRegistryListItemResponse> dataRegistryResponses =
        listDataService.listData().stream()
            .map(
                dataRegistryRestMapper
                    ::applicationDataRegistryListItemResponseToRestDataRegistryListItemResponse)
            .toList();
    return ResponseEntity.ok(dataRegistryResponses);
  }

  @GetMapping("/data/{symbolName}")
  public ResponseEntity<DataRegistryResponse> getDataBySymbolName(
      @PathVariable final String symbolName) {
    final FindDataBySymbolNameResponse findDataBySymbolNameResponse =
        findDataBySymbolNameService.findDataBySymbolName(symbolName);
    return ResponseEntity.ok(
        dataRegistryRestMapper.findDataBySymbolNameResponseToDataRegistryResponse(
            findDataBySymbolNameResponse));
  }
}
