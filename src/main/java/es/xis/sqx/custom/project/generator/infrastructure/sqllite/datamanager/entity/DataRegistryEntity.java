package es.xis.sqx.custom.project.generator.infrastructure.sqllite.datamanager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "DATA")
@Entity
public class DataRegistryEntity {

  private @Id Integer id;
  private String symbol;
  private @Column(name = "datefrom") Long dateFrom;
  private @Column(name = "dateto") Long dateTo;
  private String usymbol;

  @ManyToOne
  @JoinColumn(name = "instrument")
  private InstrumentEntity instrument;
}
