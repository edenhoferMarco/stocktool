package de.marcoedenhofer.stocktool.service.csvingestor;

import de.marcoedenhofer.stocktool.dto.StockWithWeightDto;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

class CsvLoaderTest {

    private final ICsvLoader csvLoader = new CsvLoader();

    @Test
    void loadCsvFromPath() {
        Path pathToTestCsv = Paths.get("src","test","resources", "test_stocks.csv");
        StockWithWeightDto referenceDto = new StockWithWeightDto();
        referenceDto.name = "Microsoft";
        referenceDto.isin = "US5949181045";
        referenceDto.symbol = "MSFT";
        referenceDto.weight = 12.3f;

        List<StockWithWeightDto> stocks = csvLoader.loadCsvFromPath(pathToTestCsv);

        assertAll(
                () -> assertThat(stocks.size(), is(3)),
                () -> assertThat(stocks.get(0).weight,is(referenceDto.weight)),
                () -> assertThat(stocks.get(0).isin,is(referenceDto.isin)),
                () -> assertThat(stocks.get(0).name,is(referenceDto.name)),
                () -> assertThat(stocks.get(0).symbol,is(referenceDto.symbol))
        );


    }
}