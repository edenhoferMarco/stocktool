package de.marcoedenhofer.stocktool.testutils;

import de.marcoedenhofer.stocktool.dto.StockWithWeightDto;
import de.marcoedenhofer.stocktool.model.Etf;
import de.marcoedenhofer.stocktool.model.IncludedStock;
import de.marcoedenhofer.stocktool.model.Stock;

import java.util.List;

public class DummyDataGenerator {

    private static Etf dummyEtf1;
    private static Etf dummyEtf2;
    private static Stock microsoftStock;
    private static Stock appleStock;
    private static Stock nvidiaStock;

    public static Etf generateDummyEtf1() {
        Etf etf;

        if (dummyEtf1 == null) {
            etf = new Etf();
            etf.setIsin("DEISINABC123");
            etf.setWkn("ABC123");
            etf.setName("Dummy ETF No. 1");

            IncludedStock microsoft = generateIncludedStockWithWeightForEtf(generateMicrosoftStock(),12.3f, etf);
            IncludedStock apple = generateIncludedStockWithWeightForEtf(generateAppleStock(),8.9f, etf);
            etf.getStocks().addAll(List.of(microsoft, apple));
        } else {
            etf = dummyEtf1;
        }

        return etf;
    }

    public static Etf generateDummyEtf2() {
        Etf etf;

        if (dummyEtf2 == null) {
            etf = new Etf();
            etf.setIsin("DEISINDEF456");
            etf.setWkn("DEF456");
            etf.setName("Dummy ETF No. 2");

            IncludedStock microsoft = generateIncludedStockWithWeightForEtf(generateMicrosoftStock(),10.2f, etf);
            IncludedStock nvidia = generateIncludedStockWithWeightForEtf(generateNvidiaStock(),7.7f, etf);
            etf.getStocks().addAll(List.of(microsoft, nvidia));
        } else {
            etf = dummyEtf2;
        }

        return etf;
    }

    public static IncludedStock generateIncludedStockWithWeightForEtf(Stock stock, float weight, Etf etf) {
        IncludedStock includedStock = new IncludedStock();
        includedStock.setStock(stock);
        includedStock.setWeight(weight);
        includedStock.setEtf(etf);

        return includedStock;
    }

    public static Stock generateMicrosoftStock() {
        Stock stock;

        if (microsoftStock == null) {
            stock = new Stock();
            stock.setName("Microsoft");
            stock.setIsin("US5949181045");
            stock.setSymbol("MSFT");
        } else {
            stock = microsoftStock;
        }

        return stock;
    }

    public static Stock generateAppleStock() {
        Stock stock;

        if (appleStock == null) {
            stock = new Stock();
            stock.setSymbol("AAPL");
            stock.setName("Apple");
            stock.setIsin("US0378331005");
        } else {
            stock = appleStock;
        }

        return stock;
    }

    public static Stock generateNvidiaStock() {
        Stock stock;

        if (nvidiaStock == null) {
            stock = new Stock();
            stock.setName("Nvidia");
            stock.setIsin("US67066G1040");
            stock.setSymbol("NVDA");
        } else {
            stock = nvidiaStock;
        }

        return stock;
    }

    public static List<StockWithWeightDto> generateStockWithWeightDtosLikeDummyEtf1() {
        StockWithWeightDto microsoftDto = getStockWIthWeightDtoForStock(generateMicrosoftStock(), 12.3f);
        StockWithWeightDto appleDto = getStockWIthWeightDtoForStock(generateAppleStock(),8.9f);

        return List.of(microsoftDto, appleDto);
    }

    private static StockWithWeightDto getStockWIthWeightDtoForStock(Stock stock, float weight) {
        StockWithWeightDto dto = new StockWithWeightDto();
        dto.isin = stock.getIsin();
        dto.name = stock.getName();
        dto.symbol = stock.getSymbol();
        dto.weight = weight;

        return dto;
    }
}
