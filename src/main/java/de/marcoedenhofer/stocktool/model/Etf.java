package de.marcoedenhofer.stocktool.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Etf extends IsinIdentifiedEntity {
    private String wkn;
    private String name;

    @OneToMany(mappedBy = "etf", orphanRemoval = true)
    @Cascade({CascadeType.PERSIST, CascadeType.MERGE})
    private List<IncludedStock> stocks = new ArrayList<>();

    public String getWkn() {
        return wkn;
    }

    public void setWkn(String wkn) {
        this.wkn = wkn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IncludedStock> getStocks() {
        return stocks;
    }

    public void setStocks(List<IncludedStock> stocks) {
        this.stocks = stocks;
    }
}
