package de.marcoedenhofer.stocktool.service.etfcomparator.impl;

import de.marcoedenhofer.stocktool.dto.StockWithWeightDto;
import de.marcoedenhofer.stocktool.model.Etf;
import de.marcoedenhofer.stocktool.model.Stock;
import de.marcoedenhofer.stocktool.repository.IEtfRepository;
import de.marcoedenhofer.stocktool.service.etfcomparator.api.IEtfComparatorService;
import de.marcoedenhofer.stocktool.testutils.DummyDataGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class EtfComparatorServiceTest {

    private final IEtfRepository etfRepository = mock(IEtfRepository.class);
    private final IEtfComparatorService comparatorService = new EtfComparatorService(etfRepository);

    @Test
    void getCommonStocks_oneCommonStock() {
        Etf etf = DummyDataGenerator.generateDummyEtf1();
        Etf otherEtf = DummyDataGenerator.generateDummyEtf2();
        when(etfRepository.findById(etf.getIsin())).thenReturn(java.util.Optional.of(etf));
        when(etfRepository.findById(otherEtf.getIsin())).thenReturn(Optional.of(otherEtf));

        List<Stock> commonStocks = comparatorService.getCommonStocks(etf, otherEtf);
        String microsoftStockIsin = DummyDataGenerator.generateMicrosoftStock().getIsin();

        assertAll(
                () -> assertThat(commonStocks.size(), is(1)),
                // only common stock should be microsoft.
                () -> assertThat(commonStocks.get(0).getIsin(), is(microsoftStockIsin))
        );
    }

    @Test
    void getStockWeightingInEtf_dummyEtf1() {
        Etf etf = DummyDataGenerator.generateDummyEtf1();
        Etf otherEtf = DummyDataGenerator.generateDummyEtf2();
        when(etfRepository.findById(etf.getIsin())).thenReturn(java.util.Optional.of(etf));
        // when(etfRepository.findById(otherEtf.getIsin())).thenReturn(Optional.of(otherEtf));

        List<Stock> relevantStocks = List.of(DummyDataGenerator.generateMicrosoftStock());

        List<StockWithWeightDto> stocksWithWeight = comparatorService.getStocksWithWeightingInEtf(relevantStocks, etf);
        float etf1MicrosoftWeight = etf.getStocks().stream().filter(stock ->
                stock.getStock().getIsin().equalsIgnoreCase(DummyDataGenerator.generateMicrosoftStock().getIsin()))
                .findFirst().orElseThrow().getWeight();

        assertAll(
                () -> assertThat(stocksWithWeight.size(),is(1)),
                () -> assertThat(stocksWithWeight.get(0).weight,is(etf1MicrosoftWeight))
        );

    }
}