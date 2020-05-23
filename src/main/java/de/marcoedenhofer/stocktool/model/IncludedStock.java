package de.marcoedenhofer.stocktool.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@IdClass(EtfStockCombinedIdentifiedEntry.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class IncludedStock {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private Etf etf;
    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Stock stock;

    private float weight;
}
