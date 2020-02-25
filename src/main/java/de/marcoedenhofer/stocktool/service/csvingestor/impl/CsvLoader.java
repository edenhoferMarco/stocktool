package de.marcoedenhofer.stocktool.service.csvingestor.impl;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import de.marcoedenhofer.stocktool.dto.StockWithWeightDto;
import de.marcoedenhofer.stocktool.service.csvingestor.api.ICsvLoader;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CsvLoader implements ICsvLoader {

    final private Function<CsvStockEntry, StockWithWeightDto> csvEntryToStockWithWeightInFloat = csvStockEntry -> {
        StringBuilder trimmedWeight = new StringBuilder(csvStockEntry.weight.trim());
        StringBuilder stringWithoutPercentSign = removePercentSign(trimmedWeight);
        StringBuilder stringWithoutQuotes = removeQuotes(stringWithoutPercentSign);
        StringBuilder stringWithDotDelimiters = changeFloatCommaDelimiterToDots(stringWithoutQuotes);
        String cleanedString = stringWithDotDelimiters.toString().trim();

        float weight = Float.parseFloat(cleanedString);

        StockWithWeightDto stockWithWeightDto = new StockWithWeightDto();
        stockWithWeightDto.weight = weight;
        stockWithWeightDto.symbol = csvStockEntry.symbol;
        stockWithWeightDto.isin = csvStockEntry.isin;
        stockWithWeightDto.name = csvStockEntry.name;

        return stockWithWeightDto;
    };

    @Override
    public List<StockWithWeightDto> loadCsvFromPath(Path pathToCsv) throws FileNotFoundException {
        File csvFile = new File(pathToCsv.toUri());

        return loadCsv(new FileInputStream(csvFile));
    }

    @Override
    public List<StockWithWeightDto> loadCsvFromInputStream(InputStream csvFileInputStream) {
        return loadCsv(csvFileInputStream);
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

    List<StockWithWeightDto> loadCsv(InputStream csvFileInputStream) {
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = csvMapper.schemaFor(CsvStockEntry.class);
        try {
            MappingIterator<CsvStockEntry> it = csvMapper.readerFor(CsvStockEntry.class).with(csvSchema).readValues(csvFileInputStream);
            List<CsvStockEntry> stocks = it.readAll();

            return stocks.stream()
                    .map(csvEntryToStockWithWeightInFloat)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private StringBuilder removeQuotes(StringBuilder string) {
        int index;

        // while input hast at least one <"> left
        while ((index = string.indexOf("\"")) != -1) {
            string.deleteCharAt(index);
        }

        return string;
    }

    private StringBuilder changeFloatCommaDelimiterToDots(StringBuilder string) {
        int index;

        // while input hast at least one <"> left
        while ((index = string.indexOf(",")) != -1) {
            string.replace(index,index + 1,".");
        }

        return string;
    }
}
