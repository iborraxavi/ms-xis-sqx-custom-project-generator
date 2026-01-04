package es.xis.sqx.custom.project.generator.infrastructure.rest.controller;

import es.xis.sqx.custom.project.generator.application.usecase.list_data.ListDataService;
import es.xis.sqx.custom.project.generator.infrastructure.rest.mapper.DataRegistryRestMapper;
import es.xis.sqx.custom.project.generator.infrastructure.rest.model.DataRegistryResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/data-manager/v1")
@RestController
public class DataManagerController {

  private final DataRegistryRestMapper dataRegistryRestMapper;

  private final ListDataService listDataService;

  @GetMapping("/data")
  public ResponseEntity<List<DataRegistryResponse>> listData() {
    final List<DataRegistryResponse> dataRegistryResponses =
        listDataService.listData().stream()
            .map(dataRegistryRestMapper::applicationDataRegistryResponseToRestDataRegistryResponse)
            .toList();
    return ResponseEntity.ok(dataRegistryResponses);
  }
}
