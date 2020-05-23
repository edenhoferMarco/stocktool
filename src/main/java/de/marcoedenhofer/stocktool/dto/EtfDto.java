package de.marcoedenhofer.stocktool.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class EtfDto {
    public String name;
    public String isin;
    public List<StockWithWeightDto> stockWithWeightDtos;
}
