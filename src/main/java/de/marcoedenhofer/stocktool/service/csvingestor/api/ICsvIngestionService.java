package de.marcoedenhofer.stocktool.service.csvingestor.api;

import de.marcoedenhofer.stocktool.model.Etf;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;

public interface ICsvIngestionService {
    Etf createEtfWithCsvFromPath(Etf etfDetails, Path pathToCsv) throws FileNotFoundException;
    Etf createEtfWithCsvFromStream(Etf etfDetails, InputStream csvFileInputStream) throws FileNotFoundException;
}
