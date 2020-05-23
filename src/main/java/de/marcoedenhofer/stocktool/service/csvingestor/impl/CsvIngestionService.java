package de.marcoedenhofer.stocktool.service.csvingestor.impl;

import de.marcoedenhofer.stocktool.dto.EtfDto;
import de.marcoedenhofer.stocktool.dto.StockWithWeightDto;
import de.marcoedenhofer.stocktool.model.Etf;
import de.marcoedenhofer.stocktool.model.IncludedStock;
import de.marcoedenhofer.stocktool.model.Stock;
import de.marcoedenhofer.stocktool.repository.IEtfRepository;
import de.marcoedenhofer.stocktool.service.csvingestor.api.ICsvIngestionService;
import de.marcoedenhofer.stocktool.service.csvingestor.api.ICsvLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CsvIngestionService implements ICsvIngestionService {
    private final ICsvLoader csvLoader;
    private final IEtfRepository etfRepository;

    public CsvIngestionService(ICsvLoader csvLoader, IEtfRepository etfRepository) {
        this.csvLoader = csvLoader;
        this.etfRepository = etfRepository;
    }

    @Override
    @Transactional
    public Etf createEtfWithCsvFromPath(Etf etfDetails, Path pathToCsv) throws FileNotFoundException {
        Etf etfToCreate = new Etf();
        etfToCreate.setName(etfDetails.getName());
        etfToCreate.setIsin(etfDetails.getIsin());
        List<StockWithWeightDto> stockWithWeightDtos = csvLoader.loadCsvFromPath(pathToCsv);

        etfToCreate.setStocks(transformDtosToIncludedStocks(stockWithWeightDtos,etfToCreate));

        return etfRepository.save(etfToCreate);
    }

    @Override
    @Transactional
    public Etf createEtfWithCsvFromStream(EtfDto etfDetails, InputStream csvFileInputStream) {
        Etf etfToCreate = new Etf();
        etfToCreate.setName(etfDetails.getName());
        etfToCreate.setIsin(etfDetails.getIsin());
        List<StockWithWeightDto> stockWithWeightDtos = csvLoader.loadCsvFromInputStream(csvFileInputStream);

        etfToCreate.setStocks(transformDtosToIncludedStocks(stockWithWeightDtos,etfToCreate));

        return etfRepository.save(etfToCreate);
    }

    List<IncludedStock> transformDtosToIncludedStocks(List<StockWithWeightDto> stockWithWeightDtos, Etf etfToCreate) {
        return  stockWithWeightDtos.parallelStream()
                .map(dto -> {
                    Stock stock = new Stock();
                    stock.setName(dto.name);
                    stock.setIsin(dto.isin);

                    IncludedStock includedStock = new IncludedStock();
                    includedStock.setEtf(etfToCreate);
                    includedStock.setStock(stock);
                    includedStock.setWeight(dto.weight);

                    return includedStock;

                })
                .collect(Collectors.toList());
    }
}
