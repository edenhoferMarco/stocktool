package de.marcoedenhofer.stocktool.service.csvingestor.impl;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder(value = {"name", "isin", "weight"})
public class CsvStockEntry {
    public String name;
    public String isin;
    public String weight;
}
