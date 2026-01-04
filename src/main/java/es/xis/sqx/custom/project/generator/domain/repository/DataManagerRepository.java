package es.xis.sqx.custom.project.generator.domain.repository;

import es.xis.sqx.custom.project.generator.domain.model.DataRegistry;
import java.util.List;

public interface DataManagerRepository {

  List<DataRegistry> listData();
}
