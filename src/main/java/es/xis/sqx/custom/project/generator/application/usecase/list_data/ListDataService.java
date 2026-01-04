package es.xis.sqx.custom.project.generator.application.usecase.list_data;

import es.xis.sqx.custom.project.generator.domain.repository.DataManagerRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ListDataService {

  private final ListDataMapper listDataMapper;

  private final DataManagerRepository dataManagerRepository;

  public List<DataRegistryResponse> listData() {
    return dataManagerRepository.listData().stream()
        .map(listDataMapper::dataRegistryToDataRegistryResponse)
        .toList();
  }
}
