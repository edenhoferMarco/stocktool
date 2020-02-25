package de.marcoedenhofer.stocktool.service.csvingestor.api;

import de.marcoedenhofer.stocktool.dto.StockWithWeightDto;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

public interface ICsvLoader {
    List<StockWithWeightDto> loadCsvFromPath(Path pathToCsv) throws FileNotFoundException;
    List<StockWithWeightDto> loadCsvFromInputStream(InputStream csvFileInputStream);
}
