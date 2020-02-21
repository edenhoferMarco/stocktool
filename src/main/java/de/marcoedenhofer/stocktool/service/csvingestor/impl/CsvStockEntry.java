package de.marcoedenhofer.stocktool.service.csvingestor.impl;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder(value = {"name", "symbol", "isin", "weight"})
public class CsvStockEntry {
    public String name;
    public String symbol;
    public String isin;
    public String weight;
}
