package es.xis.sqx.custom.project.generator.infrastructure.sqllite.datamanager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "DATA")
@Entity
public class DataRegistryEntity {

  private @Id Integer id;
  private String symbol;
  private String instrument;
  private String usymbol;
}
