package de.marcoedenhofer.stocktool.service.etfmanagement.api;

import de.marcoedenhofer.stocktool.dto.EtfDto;
import de.marcoedenhofer.stocktool.model.Etf;
import de.marcoedenhofer.stocktool.service.etfcomparator.api.IEtfComparatorService;

import java.io.InputStream;
import java.util.List;
import java.util.NoSuchElementException;

public interface IEtfManagementService extends IEtfComparatorService {
    List<Etf> getAllEtfs();
    Etf getEtfWithIsin(String isin) throws NoSuchElementException;
    Etf createEtf(EtfDto etfDetails, InputStream stocksCsvInputStream);
    void deleteEtf(Etf etf);
}
