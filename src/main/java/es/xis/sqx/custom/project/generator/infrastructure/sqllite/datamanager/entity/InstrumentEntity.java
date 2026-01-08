package es.xis.sqx.custom.project.generator.infrastructure.sqllite.datamanager.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "INSTRUMENTS")
@Entity
public class InstrumentEntity {

  private @Id String instrument;
}
