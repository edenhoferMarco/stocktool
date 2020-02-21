package de.marcoedenhofer.stocktool.dto;

import java.util.List;

public class EtfDto {
    String name;
    String isin;
    String issuer;
    List<StockWithWeightDto> stockWithWeightDtos;
}
