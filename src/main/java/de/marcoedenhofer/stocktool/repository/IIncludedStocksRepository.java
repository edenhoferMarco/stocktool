package de.marcoedenhofer.stocktool.repository;

import de.marcoedenhofer.stocktool.model.EtfStockCombinedIdentifiedEntry;
import de.marcoedenhofer.stocktool.model.IncludedStock;
import org.springframework.data.repository.CrudRepository;

public interface IIncludedStocksRepository extends CrudRepository<IncludedStock, EtfStockCombinedIdentifiedEntry> {
}
