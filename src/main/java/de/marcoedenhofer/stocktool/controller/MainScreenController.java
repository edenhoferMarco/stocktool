package de.marcoedenhofer.stocktool.controller;

import de.marcoedenhofer.stocktool.dto.EtfDto;
import de.marcoedenhofer.stocktool.dto.StockWithWeightDto;
import de.marcoedenhofer.stocktool.model.Etf;
import de.marcoedenhofer.stocktool.model.Stock;
import de.marcoedenhofer.stocktool.service.etfmanagement.api.IEtfManagementService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class MainScreenController {
    private final String ETF_STOCKCOUNT_MODEL_KEYWORD = "etfStockCount";
    private final String OTHER_ETF_STOCKCOUNT_MODEL_KEYWORD = "otherEtfStockCount";
    private final String ETF_TO_CREATE_MODEL_KEYWORD = "etfToCreate";
    private final String ETFS_MODEL_KEYWORD = "etfs";
    private final String ETF_COMMON_STOCKS_MODEL_KEYWORD = "etfDetailsWithCommonStocks";
    private final String OTHER_ETF_COMMON_STOCKS_MODEL_KEYWORD = "otherEtfDetailsWithCommonStocks";

    private final IEtfManagementService etfManagementService;

    public MainScreenController(IEtfManagementService etfManagementService) {
        this.etfManagementService = etfManagementService;
    }

    @RequestMapping("/")
    public String compare(@RequestParam(value = "etf", required = false) String etfIsin,
                          @RequestParam(value = "otherEtf", required = false) String otherEtfIsin,
                          Model model) {
        List<Etf> etfs = etfManagementService.getAllEtfs();
        model.addAttribute(ETFS_MODEL_KEYWORD, etfs);

        Etf etfToCreate = new Etf();

        EtfDto etfDto = null;
        EtfDto otherEtfDto = null;

        int etfStockCount = 0;
        int otherEtfStockCount = 0;

        if (etfIsin != null && otherEtfIsin != null) {
            try {
                Etf etf = etfManagementService.getEtfWithIsin(etfIsin);
                etfStockCount = etf.getStocks().size();
                Etf otherEtf = etfManagementService.getEtfWithIsin(otherEtfIsin);
                otherEtfStockCount = otherEtf.getStocks().size();

                List<Stock> commonStocks = etfManagementService.getCommonStocks(etf,otherEtf);
                List<StockWithWeightDto> commonStocksWithWeightForEtf =
                        etfManagementService.getStocksWithWeightingInEtf(commonStocks,etf);
                List<StockWithWeightDto> commonStocksWithWeightForOtherEtf =
                        etfManagementService.getStocksWithWeightingInEtf(commonStocks,otherEtf);

                etfDto = new EtfDto(etf.getName(), etf.getIsin(), etf.getWkn(), commonStocksWithWeightForEtf);
                otherEtfDto = new EtfDto(otherEtf.getName(), otherEtf.getIsin(), otherEtf.getWkn(),
                        commonStocksWithWeightForOtherEtf);
            } catch (NoSuchElementException noElemException) {
                noElemException.printStackTrace();
            }
        }

        model.addAttribute(ETF_COMMON_STOCKS_MODEL_KEYWORD, etfDto);
        model.addAttribute(OTHER_ETF_COMMON_STOCKS_MODEL_KEYWORD, otherEtfDto);
        model.addAttribute(ETF_TO_CREATE_MODEL_KEYWORD,etfToCreate);
        model.addAttribute(ETF_STOCKCOUNT_MODEL_KEYWORD,etfStockCount);
        model.addAttribute(OTHER_ETF_STOCKCOUNT_MODEL_KEYWORD,otherEtfStockCount);

        return "index";
    }
}
