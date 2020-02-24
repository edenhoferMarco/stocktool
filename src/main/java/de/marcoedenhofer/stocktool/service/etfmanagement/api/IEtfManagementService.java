package de.marcoedenhofer.stocktool.service.etfmanagement.api;

import de.marcoedenhofer.stocktool.model.Etf;
import de.marcoedenhofer.stocktool.service.etfcomparator.api.IEtfComparatorService;

import java.util.List;
import java.util.NoSuchElementException;

public interface IEtfManagementService extends IEtfComparatorService {
    List<Etf> getAllEtfs();
    Etf getEtfWithIsin(String isin) throws NoSuchElementException;
    void deleteEtf(Etf etf);
}
