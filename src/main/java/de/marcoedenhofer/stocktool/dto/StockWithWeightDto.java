package de.marcoedenhofer.stocktool.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class StockWithWeightDto {
    public String isin;
    public String name;
    public float weight;
}
