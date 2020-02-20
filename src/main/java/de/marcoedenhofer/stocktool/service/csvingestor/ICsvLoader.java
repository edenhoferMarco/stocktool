package de.marcoedenhofer.stocktool.service.csvingestor;

import de.marcoedenhofer.stocktool.dto.StockWithWeightDto;

import java.nio.file.Path;
import java.util.List;

public interface ICsvLoader {
    List<StockWithWeightDto> loadCsvFromPath(Path pathToCsv);
}
