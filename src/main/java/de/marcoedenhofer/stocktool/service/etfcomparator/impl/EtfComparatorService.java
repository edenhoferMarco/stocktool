package de.marcoedenhofer.stocktool.service.etfcomparator.impl;

import de.marcoedenhofer.stocktool.dto.StockWithWeightDto;
import de.marcoedenhofer.stocktool.model.Etf;
import de.marcoedenhofer.stocktool.model.IncludedStock;
import de.marcoedenhofer.stocktool.model.Stock;
import de.marcoedenhofer.stocktool.repository.IEtfRepository;
import de.marcoedenhofer.stocktool.service.etfcomparator.api.IEtfComparatorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class EtfComparatorService implements IEtfComparatorService {

    private final IEtfRepository etfRepository;

    public EtfComparatorService(IEtfRepository etfRepository) {
        this.etfRepository = etfRepository;
    }

    @Override
    public List<Stock> getCommonStocks(Etf etf, Etf otherEtf) {
        etf = etfRepository.findById(etf.getIsin()).orElseThrow();
        otherEtf = etfRepository.findById(otherEtf.getIsin()).orElseThrow();
        List<String> otherEtfStocksIsin = otherEtf.getStocks().stream()
                .map(includedStock -> includedStock.getStock().getIsin())
                .collect(Collectors.toList());

        final Predicate<IncludedStock> stockIsInCommonWithOtherEtf = includedStock ->
                otherEtfStocksIsin.stream().anyMatch(s -> includedStock.getStock().getIsin().equalsIgnoreCase(s));

        return etf.getStocks().stream()
                .filter(stockIsInCommonWithOtherEtf)
                .map(IncludedStock::getStock)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<StockWithWeightDto> getStocksWithWeightingInEtf(List<Stock> stocks, Etf etf) {
        etf = etfRepository.findById(etf.getIsin()).orElseThrow();

        final Predicate<IncludedStock> stockIsRelevant = includedStock ->
                stocks.stream().anyMatch(stock -> includedStock.getStock().getIsin().equalsIgnoreCase(stock.getIsin()));

        return etf.getStocks().stream()
                .filter(stockIsRelevant)
                .map(includedStock -> {
                    StockWithWeightDto stockWithWeightDto = new StockWithWeightDto();
                    stockWithWeightDto.isin = includedStock.getStock().getIsin();
                    stockWithWeightDto.name = includedStock.getStock().getName();
                    stockWithWeightDto.symbol = includedStock.getStock().getSymbol();
                    stockWithWeightDto.weight = includedStock.getWeight();

                    return stockWithWeightDto;
                })
                .collect(Collectors.toList());
    }
}
