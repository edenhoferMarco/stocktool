package de.marcoedenhofer.stocktool.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Stock extends IsinIdentifiedEntity {
    private String symbol;
    private String name;

    @OneToMany(mappedBy = "stock", fetch = FetchType.EAGER)
    private List<IncludedStock> includedInEtfs = new ArrayList<>();

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IncludedStock> getIncludedInEtfs() {
        return includedInEtfs;
    }

    public void setIncludedInEtfs(List<IncludedStock> includedInEtfs) {
        this.includedInEtfs = includedInEtfs;
    }
}
