package de.marcoedenhofer.stocktool.service.csvingestor.api;

import de.marcoedenhofer.stocktool.dto.StockWithWeightDto;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public interface ICsvLoader {
    List<StockWithWeightDto> loadCsvFromPath(Path pathToCsv);
    List<StockWithWeightDto> loadCsvFromFile(File csvFile);
}
