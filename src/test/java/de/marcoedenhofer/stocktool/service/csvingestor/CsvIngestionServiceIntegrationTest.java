package de.marcoedenhofer.stocktool.service.csvingestor;

import de.marcoedenhofer.stocktool.model.Etf;
import de.marcoedenhofer.stocktool.model.IncludedStock;
import de.marcoedenhofer.stocktool.model.Stock;
import de.marcoedenhofer.stocktool.repository.IEtfRepository;
import de.marcoedenhofer.stocktool.service.csvingestor.impl.CsvIngestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;


@SpringBootTest
public class CsvIngestionServiceIntegrationTest {
    private final CsvIngestionService csvIngestionService;
    private final IEtfRepository etfRepository;

    @Autowired
    public CsvIngestionServiceIntegrationTest(CsvIngestionService csvIngestionService, IEtfRepository etfRepository) {
        this.csvIngestionService = csvIngestionService;
        this.etfRepository = etfRepository;
    }

    @Test
    void createEtfWithCsv() throws FileNotFoundException {
        Path pathToTestCsvFile = Path.of("src", "test", "resources", "test_stocks.csv");
        Etf etfDetails = new Etf();
        etfDetails.setIsin("ISIN2TEST");
        etfDetails.setWkn("TESTWKN");
        etfDetails.setName("Test Etf for integration testing");

        Stock nvidiaStock = new Stock();
        nvidiaStock.setIsin("US67066G1040");
        nvidiaStock.setSymbol("NVDA");
        nvidiaStock.setName("Nvidia");
        final float nvidiaWeight = 3.3f;

        Stock microsoftStock = new Stock();
        microsoftStock.setName("Microsoft");
        microsoftStock.setSymbol("MSFT");
        microsoftStock.setIsin("US5949181045");
        final float microsoftWeight = 12.3f;

        Stock appleStock = new Stock();
        appleStock.setName("Apple");
        appleStock.setIsin("US0378331005");
        appleStock.setSymbol("AAPL");
        final float appleWeight = 8.7f;

        Etf createdEtf = csvIngestionService.createEtfWithCsvFromPath(etfDetails, pathToTestCsvFile);
        List<Stock> stocks = createdEtf.getStocks().stream()
                .map(IncludedStock::getStock)
                .collect(Collectors.toList());

        List<Float> weightings = createdEtf.getStocks().stream()
                .map(IncludedStock::getWeight)
                .collect(Collectors.toList());

        final long countOfEtfs = etfRepository.count();

        assertAll(
                () -> assertThat(countOfEtfs, is(1L)),
                () -> assertThat(createdEtf, is(etfDetails)),
                () -> assertThat(stocks, hasItem(nvidiaStock)),
                () -> assertThat(stocks, hasItem(microsoftStock)),
                () -> assertThat(stocks, hasItem(appleStock)),
                () -> assertThat(weightings, hasItem(nvidiaWeight)),
                () -> assertThat(weightings, hasItem(appleWeight)),
                () -> assertThat(weightings, hasItem(microsoftWeight))
        );
    }
}
