package de.marcoedenhofer.stocktool.service.csvingestor.impl;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import de.marcoedenhofer.stocktool.dto.StockWithWeightDto;
import de.marcoedenhofer.stocktool.service.csvingestor.api.ICsvLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CsvLoader implements ICsvLoader {

    private final Function<CsvStockEntry, StockWithWeightDto> csvEntryToStockWithWeightDto = csvStockEntry -> {
        StringBuilder trimmedWeight = new StringBuilder(csvStockEntry.weight.trim());
        StringBuilder stringWithoutPercentSign = removePercentSign(trimmedWeight);
        StringBuilder stringWithoutQuotes = removeQuotes(stringWithoutPercentSign);
        StringBuilder stringWithDotDelimiters = changeFloatCommaDelimiterToDots(stringWithoutQuotes);
        String cleanedString = stringWithDotDelimiters.toString().trim();

        float weight = Float.parseFloat(cleanedString);

        StockWithWeightDto stockWithWeightDto = new StockWithWeightDto();
        stockWithWeightDto.weight = weight;
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

    List<StockWithWeightDto> loadCsv(InputStream csvFileInputStream) {
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = csvMapper.schemaFor(CsvStockEntry.class);
        try {
            MappingIterator<CsvStockEntry> it = csvMapper.readerFor(CsvStockEntry.class).with(csvSchema).readValues(csvFileInputStream);
            List<CsvStockEntry> stocks = it.readAll();
            // isin is specified to be exactly 12 long.
            final int specifiedIsinLength = 12;
            Predicate<CsvStockEntry> isinLengthAsSpecified = entry -> entry.isin.length() == specifiedIsinLength;

            return stocks.stream()
                    .filter(isinLengthAsSpecified)
                    .map(csvEntryToStockWithWeightDto)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error("Error while reading CSV File!", e);
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

    private StringBuilder changeFloatCommaDelimiterToDots(StringBuilder string) {
        int index;

        // while input hast at least one <"> left
        while ((index = string.indexOf(",")) != -1) {
            string.replace(index,index + 1,".");
        }

        return string;
    }
}
