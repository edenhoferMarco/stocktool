package de.marcoedenhofer.stocktool.model;

import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.Objects;

public class EtfStockCombinedIdentifiedEntry implements Serializable {
    @NonNull
    private Etf etf;
    @NonNull
    private Stock stock;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EtfStockCombinedIdentifiedEntry that = (EtfStockCombinedIdentifiedEntry) o;
        return getEtf().equals(that.getEtf()) &&
                getStock().equals(that.getStock());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEtf(), getStock());
    }

    @NonNull
    public Etf getEtf() {
        return etf;
    }

    public void setEtf(@NonNull Etf etf) {
        this.etf = etf;
    }

    @NonNull
    public Stock getStock() {
        return stock;
    }

    public void setStock(@NonNull Stock stock) {
        this.stock = stock;
    }
}
