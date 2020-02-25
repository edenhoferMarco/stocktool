package de.marcoedenhofer.stocktool.config;

import de.marcoedenhofer.stocktool.model.Etf;
import de.marcoedenhofer.stocktool.model.IncludedStock;
import de.marcoedenhofer.stocktool.model.Stock;
import de.marcoedenhofer.stocktool.repository.IEtfRepository;
import de.marcoedenhofer.stocktool.repository.IStockRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
@Profile("!test")
public class DummyDataLoader implements ApplicationRunner {

    final private IStockRepository stockRepository;
    final private IEtfRepository etfRepository;

    DummyDataLoader(IStockRepository stockRepository, IEtfRepository etfRepository) {
        this.stockRepository = stockRepository;
        this.etfRepository = etfRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createDummyData();
        //deleteDummyData();
        //deleteStocksWithNoEtf();
    }

    @Transactional
    protected void createDummyData() {
        Stock microsoftStock = new Stock();
        microsoftStock.setName("Microsoft");
        microsoftStock.setIsin("US5949181045");
        microsoftStock.setSymbol("MSFT");


        Stock nvidiaStock = new Stock();
        nvidiaStock.setName("Nvidia");
        nvidiaStock.setIsin("US67066G1040");
        nvidiaStock.setSymbol("NVDA");

        Stock appleStock = new Stock();
        appleStock.setName("Apple");
        appleStock.setIsin("US0378331005");
        appleStock.setSymbol("AAPL");

        // stockRepository.save(microsoftStock);
        // stockRepository.save(nvidiaStock);
        // stockRepository.save(appleStock);

        Etf etf1 = new Etf();
        etf1.setIsin("DEISINABC001");
        etf1.setWkn("ABC001");
        etf1.setName("Dummy ETF No. 1");

        Etf etf2 = new Etf();
        etf2.setIsin("DEISINDEF002");
        etf2.setWkn("DEF002");
        etf2.setName("Dummy ETF No. 2");

        Etf etf3 = new Etf();
        etf3.setIsin("DEISINDEF003");
        etf3.setWkn("DEF003");
        etf3.setName("Dummy ETF No. 3");

        IncludedStock includedMicrosoftStock1 = new IncludedStock();
        includedMicrosoftStock1.setStock(microsoftStock);
        includedMicrosoftStock1.setEtf(etf1);
        includedMicrosoftStock1.setWeight(12.3f);

        IncludedStock includedMicrosoftStock2 = new IncludedStock();
        includedMicrosoftStock2.setStock(microsoftStock);
        includedMicrosoftStock2.setEtf(etf2);
        includedMicrosoftStock2.setWeight(9.84f);

        IncludedStock includedNvidiaStock = new IncludedStock();
        includedNvidiaStock.setStock(nvidiaStock);
        includedNvidiaStock.setEtf(etf1);
        includedNvidiaStock.setWeight(10.1f);

        IncludedStock includedNvidiaStock2 = new IncludedStock();
        includedNvidiaStock2.setStock(nvidiaStock);
        includedNvidiaStock2.setEtf(etf3);
        includedNvidiaStock2.setWeight(11.1f);

        IncludedStock includedAppleStock = new IncludedStock();
        includedAppleStock.setStock(appleStock);
        includedAppleStock.setEtf(etf2);
        includedAppleStock.setWeight(8.8f);

        IncludedStock includedAppleStock2 = new IncludedStock();
        includedAppleStock2.setStock(appleStock);
        includedAppleStock2.setEtf(etf3);
        includedAppleStock2.setWeight(9.4f);

        etf1.getStocks().add(includedMicrosoftStock1);
        etf1.getStocks().add(includedNvidiaStock);

        etf2.getStocks().add(includedMicrosoftStock2);
        etf2.getStocks().add(includedAppleStock);

        etf3.getStocks().add((includedNvidiaStock2));
        etf3.getStocks().add(includedAppleStock2);

        etf1 = etfRepository.save(etf1);
        etf2 = etfRepository.save(etf2);
        etf3 = etfRepository.save(etf3);

    }

    @Transactional
    protected void deleteDummyData() {
        etfRepository.findById("DEISINABC123").ifPresent(etfRepository::delete);
    }

    @Transactional
    protected void deleteStocksWithNoEtf() {
        List<Stock> stockList = new ArrayList<>();
        stockRepository.findAll().forEach(stockList::add);

        Predicate<Stock> stocksWithoutEtfs = stock -> stock.getIncludedInEtfs().size() == 0;

        stockList.stream().filter(stocksWithoutEtfs).forEach(stockRepository::delete);
    }
}
