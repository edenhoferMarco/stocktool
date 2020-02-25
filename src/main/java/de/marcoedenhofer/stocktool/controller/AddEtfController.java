package de.marcoedenhofer.stocktool.controller;

import de.marcoedenhofer.stocktool.model.Etf;
import de.marcoedenhofer.stocktool.service.etfmanagement.api.IEtfManagementService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class AddEtfController {
    private final IEtfManagementService etfManagementService;

    public AddEtfController(IEtfManagementService etfManagementService) {
        this.etfManagementService = etfManagementService;
    }


    @RequestMapping(value = "/addEtf", method = RequestMethod.POST)
    public String addEtf(@RequestParam("newEtf") Etf etfDetails,
                         @RequestParam("stocksCsvFile") MultipartFile stocksCsvFile) {

        try {
            etfManagementService.createEtf(etfDetails, stocksCsvFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/";
    }

}
