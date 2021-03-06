package de.marcoedenhofer.stocktool.service.csvingestor.impl;

import de.marcoedenhofer.stocktool.dto.StockWithWeightDto;
import de.marcoedenhofer.stocktool.service.csvingestor.api.ICsvLoader;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

class CsvLoaderTest {

    private final ICsvLoader csvLoader = new CsvLoader();

    @Test
    void loadCsvFromPath() throws FileNotFoundException {
        Path pathToTestCsv = Paths.get("src","test","resources", "test_stocks.csv");
        StockWithWeightDto referenceDto = new StockWithWeightDto();
        referenceDto.name = "Microsoft";
        referenceDto.isin = "US5949181045";
        referenceDto.weight = 12.3f;

        List<StockWithWeightDto> stocks = csvLoader.loadCsvFromPath(pathToTestCsv);

        assertAll(
                () -> assertThat(stocks.size(), is(3)),
                () -> assertThat(stocks.get(0).weight,is(referenceDto.weight)),
                () -> assertThat(stocks.get(0).isin,is(referenceDto.isin)),
                () -> assertThat(stocks.get(0).name,is(referenceDto.name))
        );
    }
}