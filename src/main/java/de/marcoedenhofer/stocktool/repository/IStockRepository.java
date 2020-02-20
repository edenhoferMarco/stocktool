package de.marcoedenhofer.stocktool.repository;

import de.marcoedenhofer.stocktool.model.Stock;
import org.springframework.data.repository.CrudRepository;

public interface IStockRepository extends CrudRepository<Stock, String> {
}
