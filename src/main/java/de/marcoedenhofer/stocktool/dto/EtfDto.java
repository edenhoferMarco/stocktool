package de.marcoedenhofer.stocktool.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EtfDto {
    public String name;
    public String isin;
    public List<StockWithWeightDto> stockWithWeightDtos;
}
