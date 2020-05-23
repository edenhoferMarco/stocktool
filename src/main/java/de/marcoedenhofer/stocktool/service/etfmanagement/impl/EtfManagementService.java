package de.marcoedenhofer.stocktool.service.etfmanagement.impl;

import com.google.common.base.Strings;
import de.marcoedenhofer.stocktool.dto.EtfDto;
import de.marcoedenhofer.stocktool.dto.StockWithWeightDto;
import de.marcoedenhofer.stocktool.model.Etf;
import de.marcoedenhofer.stocktool.model.Stock;
import de.marcoedenhofer.stocktool.repository.IEtfRepository;
import de.marcoedenhofer.stocktool.service.csvingestor.impl.CsvIngestionService;
import de.marcoedenhofer.stocktool.service.etfcomparator.api.IEtfComparatorService;
import de.marcoedenhofer.stocktool.service.etfmanagement.api.IEtfManagementService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EtfManagementService implements IEtfManagementService {
    private final IEtfRepository etfRepository;
    private final IEtfComparatorService etfComparatorService;
    private final CsvIngestionService csvIngestionService;

    public EtfManagementService(IEtfRepository etfRepository, IEtfComparatorService etfComparatorService,
                                CsvIngestionService csvIngestionService) {
        this.etfRepository = etfRepository;
        this.etfComparatorService = etfComparatorService;
        this.csvIngestionService = csvIngestionService;
    }

    @Override
    public List<Etf> getAllEtfs() {
        ArrayList<Etf> etfs = new ArrayList<>();

        etfRepository.findAll().forEach(etfs::add);

        return Collections.unmodifiableList(etfs);
    }

    @Override
    public Etf getEtfWithIsin(String isin) throws NoSuchElementException{
        return etfRepository.getEtfByIsin(isin).orElseThrow(() -> new NoSuchElementException("No ETF with ISIN: " + isin + " exists."));
    }

    @Override
    public Etf createEtf(EtfDto etfDetails, InputStream stockCsvInputStream) throws IllegalArgumentException {
        checkIfEtfInputIsValid(etfDetails);
        return csvIngestionService.createEtfWithCsvFromStream(etfDetails, stockCsvInputStream);
    }

    @Override
    @Transactional
    public void deleteEtf(Etf etf) {
        etfRepository.delete(etf);
    }

    @Override
    public List<Stock> getCommonStocks(Etf etf, Etf otherEtf) {
        return etfComparatorService.getCommonStocks(etf,otherEtf);
    }

    @Override
    public List<StockWithWeightDto> getStocksWithWeightingInEtf(List<Stock> stocks, Etf etf) {
        return etfComparatorService.getStocksWithWeightingInEtf(stocks,etf);
    }

    private void checkIfEtfInputIsValid(EtfDto etfDto) throws IllegalArgumentException {
        boolean isinIsValid = !Strings.isNullOrEmpty(etfDto.isin);
        boolean nameIsValid =  !Strings.isNullOrEmpty(etfDto.name);

        if (!isinIsValid || !nameIsValid) {
            throw new IllegalArgumentException("Name or Isin from ETF must not be null or empty");
        }
    }
}
