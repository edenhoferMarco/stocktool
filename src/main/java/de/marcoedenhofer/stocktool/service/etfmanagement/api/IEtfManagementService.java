package de.marcoedenhofer.stocktool.service.etfmanagement.api;

import de.marcoedenhofer.stocktool.model.Etf;

import java.util.List;

public interface IEtfManagementService {
    List<Etf> getAllEtfs();
    void deleteEtf(Etf etf);
}
