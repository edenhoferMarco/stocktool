package de.marcoedenhofer.stocktool.service.csvingestor.impl;

import de.marcoedenhofer.stocktool.dto.StockWithWeightDto;
import de.marcoedenhofer.stocktool.model.Etf;
import de.marcoedenhofer.stocktool.model.IncludedStock;
import de.marcoedenhofer.stocktool.repository.IEtfRepository;
import de.marcoedenhofer.stocktool.service.csvingestor.api.ICsvLoader;
import de.marcoedenhofer.stocktool.testutils.DummyDataGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DataJpaTest
class CsvIngestionServiceTest {
    private final IEtfRepository etfRepository;
    private final ICsvLoader csvLoader = mock(ICsvLoader.class);

    @Autowired
    CsvIngestionServiceTest(IEtfRepository etfRepository) {
        this.etfRepository = etfRepository;
    }

    @Test
    void createEtfWithCsv_EtfIsPersistedInDatabase() {
        Etf dummyEtf = DummyDataGenerator.generateDummyEtf1();
        List<IncludedStock> dummyStocks = List.copyOf(dummyEtf.getStocks());
        dummyEtf.setStocks(Collections.emptyList());

        when(csvLoader.loadCsvFromPath(Path.of("testpath"))).thenReturn(Collections.emptyList());

        final CsvIngestionService csvIngestionService = new CsvIngestionService(csvLoader, etfRepository) {
            @Override
            List<IncludedStock> transformDtosToIncludedStocks(List<StockWithWeightDto> stockWithWeightDtos, Etf etfToCreate) {
                return dummyStocks;
            }
        };

        csvIngestionService.createEtfWithCsv(dummyEtf, Path.of("testpath"));

        assertThat(etfRepository.count(), is(1L));

    }

    @Test
    void transformDtosToIncludedStocks() {
        IEtfRepository mockedEtfRepo = mock(IEtfRepository.class);
        final CsvIngestionService csvIngestionService = new CsvIngestionService(csvLoader, mockedEtfRepo);
        Etf dummyEtf = DummyDataGenerator.generateDummyEtf1();
        dummyEtf.setStocks(Collections.emptyList());
        List<StockWithWeightDto> dummyStocks = DummyDataGenerator.generateStockWithWeightDtosLikeDummyEtf1();

        List<IncludedStock> includedStocks = csvIngestionService.transformDtosToIncludedStocks(dummyStocks, dummyEtf);

        assertAll(
                () -> assertThat(includedStocks.size(), is(2)),
                () -> assertThat(includedStocks.get(0).getWeight(), is(dummyStocks.get(0).weight)),
                () -> assertThat(includedStocks.get(0).getStock().getIsin(), is(dummyStocks.get(0).isin)),
                () -> assertThat(includedStocks.get(0).getEtf().getIsin(), is(dummyEtf.getIsin()))
        );

    }
}