package de.marcoedenhofer.stocktool.service.etfmanagement.impl;

import de.marcoedenhofer.stocktool.model.Etf;
import de.marcoedenhofer.stocktool.repository.IEtfRepository;
import de.marcoedenhofer.stocktool.service.etfmanagement.api.IEtfManagementService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class EtfManagementService implements IEtfManagementService {
    private final IEtfRepository etfRepository;

    public EtfManagementService(IEtfRepository etfRepository) {
        this.etfRepository = etfRepository;
    }

    @Override
    public List<Etf> getAllEtfs() {
        ArrayList<Etf> etfs = new ArrayList<>();

        etfRepository.findAll().forEach(etfs::add);

        return Collections.unmodifiableList(etfs);
    }

    @Override
    @Transactional
    public void deleteEtf(Etf etf) {
        etfRepository.delete(etf);
    }
}
