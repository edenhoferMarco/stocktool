package de.marcoedenhofer.stocktool.model;

import javax.persistence.*;

@Entity
@IdClass(EtfStockCombinedIdentifiedEntry.class)
public class IncludedStock {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private Etf etf;
    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Stock stock;

    private float weight;

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public Etf getEtf() {
        return etf;
    }

    public void setEtf(Etf etf) {
        this.etf = etf;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }
}
