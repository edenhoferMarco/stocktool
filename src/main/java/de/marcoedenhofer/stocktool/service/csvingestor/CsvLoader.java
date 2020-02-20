package de.marcoedenhofer.stocktool.service.csvingestor;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import de.marcoedenhofer.stocktool.dto.StockWithWeightDto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CsvLoader implements ICsvLoader {

    final private Function<CsvStockEntry, StockWithWeightDto> csvEntryToStockWithWeightInFloat = csvStockEntry -> {
        StringBuilder trimmedWeight = new StringBuilder(csvStockEntry.weight.trim());
        StringBuilder stringWithoutPercentSign = removePercentSign(trimmedWeight);
        StringBuilder stringWithoutQuotes = removeQuotes(stringWithoutPercentSign);
        String cleanedString = stringWithoutQuotes.toString().trim();

        float weight = Float.parseFloat(cleanedString);

        StockWithWeightDto stockWithWeightDto = new StockWithWeightDto();
        stockWithWeightDto.weight = weight;
        stockWithWeightDto.symbol = csvStockEntry.symbol;
        stockWithWeightDto.isin = csvStockEntry.isin;
        stockWithWeightDto.name = csvStockEntry.name;

        return stockWithWeightDto;
    };

    @Override
    public List<StockWithWeightDto> loadCsvFromPath(Path pathToCsv) {
        File csvFile = new File(pathToCsv.toUri());
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = csvMapper.schemaFor(CsvStockEntry.class);
        try {
            MappingIterator<CsvStockEntry> it = csvMapper.readerFor(CsvStockEntry.class).with(csvSchema).readValues(csvFile);
            List<CsvStockEntry> stocks = it.readAll();

            return stocks.stream()
                    .map(csvEntryToStockWithWeightInFloat)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private StringBuilder removePercentSign(StringBuilder input) {
        StringBuilder output;

        int indexOfPercentSign = input.indexOf("%");
        // if a % is found...
        if (indexOfPercentSign != -1) {
            output = input.deleteCharAt(indexOfPercentSign);
        } else {
            output = input;
        }

        return output;
    }

    private StringBuilder removeQuotes(StringBuilder string) {
        int index;

        // while input hast at least one <"> left
        while ((index = string.indexOf("\"")) != -1) {
            string.deleteCharAt(index);
        }

        return string;
    }
}