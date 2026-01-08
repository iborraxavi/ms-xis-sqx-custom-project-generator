package es.xis.sqx.custom.project.generator.application.usecase.find_data_by_symbol_name;

import es.xis.sqx.custom.project.generator.domain.exceptions.NotFoundException;
import es.xis.sqx.custom.project.generator.domain.repository.DataManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FindDataBySymbolNameService {

  private final FindDataBySymbolNameMapper findDataBySymbolNameMapper;

  private final DataManagerRepository dataManagerRepository;

  public FindDataBySymbolNameResponse findDataBySymbolName(final String symbolName) {
    return dataManagerRepository
        .findBySymbolName(symbolName)
        .map(findDataBySymbolNameMapper::dataRegistryToFindDataBySymbolNameResponse)
        .orElseThrow(
            () -> new NotFoundException("Data registry not found for symbol name: " + symbolName));
  }
}
