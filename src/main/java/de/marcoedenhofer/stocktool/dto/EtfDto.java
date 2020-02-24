package de.marcoedenhofer.stocktool.dto;

import java.util.List;

public class EtfDto {
    public String name;
    public String isin;
    public String wkn;
    public List<StockWithWeightDto> stockWithWeightDtos;

    public EtfDto() {

    }

    public EtfDto(String name, String isin, String wkn, List<StockWithWeightDto> stockWithWeightDtos) {
        this.name = name;
        this.isin = isin;
        this.wkn = wkn;
        this.stockWithWeightDtos = stockWithWeightDtos;
    }
}
