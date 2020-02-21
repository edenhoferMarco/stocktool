package de.marcoedenhofer.stocktool.service.csvingestor.api;

import de.marcoedenhofer.stocktool.model.Etf;

import java.nio.file.Path;

public interface ICsvIngestionService {
    Etf createEtfWithCsv(Etf etfDetails, Path pathToCsv);
}
