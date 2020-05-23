package de.marcoedenhofer.stocktool.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Stock extends IsinIdentifiedEntity {
    private String name;

    @OneToMany(mappedBy = "stock", fetch = FetchType.EAGER)
    private List<IncludedStock> includedInEtfs = new ArrayList<>();
}
