package de.marcoedenhofer.stocktool.model;

import lombok.*;
import org.springframework.lang.NonNull;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EtfStockCombinedIdentifiedEntry implements Serializable {
    @NonNull
    private Etf etf;
    @NonNull
    private Stock stock;
}
