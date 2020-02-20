package de.marcoedenhofer.stocktool.service.etfcomparator.api;

import de.marcoedenhofer.stocktool.dto.StockWithWeightDto;
import de.marcoedenhofer.stocktool.model.Etf;
import de.marcoedenhofer.stocktool.model.Stock;

import java.util.List;

public interface IEtfComparatorService {
    List<Stock> getCommonStocks(Etf etf, Etf otherEtf);
    List<StockWithWeightDto> getStocksWithWeightingInEtf(List<Stock> stocks, Etf etf);
}
