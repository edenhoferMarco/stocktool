package de.marcoedenhofer.stocktool.repository;

import de.marcoedenhofer.stocktool.model.Etf;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IEtfRepository extends CrudRepository<Etf, String> {
    Optional<Etf> getEtfByIsin(String isin);
}
