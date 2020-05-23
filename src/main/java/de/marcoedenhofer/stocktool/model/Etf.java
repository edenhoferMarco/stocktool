package de.marcoedenhofer.stocktool.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Etf extends IsinIdentifiedEntity {
    @OneToMany(mappedBy = "etf", orphanRemoval = true)
    @Cascade({CascadeType.PERSIST, CascadeType.MERGE})
    private List<IncludedStock> stocks = new ArrayList<>();

    private String name;
}
