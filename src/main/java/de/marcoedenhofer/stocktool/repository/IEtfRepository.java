package de.marcoedenhofer.stocktool.repository;

import de.marcoedenhofer.stocktool.model.Etf;
import org.springframework.data.repository.CrudRepository;

public interface IEtfRepository extends CrudRepository<Etf, String> {
}
