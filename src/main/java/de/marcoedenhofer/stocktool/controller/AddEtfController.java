package de.marcoedenhofer.stocktool.controller;

import de.marcoedenhofer.stocktool.dto.EtfDto;
import de.marcoedenhofer.stocktool.service.etfmanagement.api.IEtfManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@Slf4j
public class AddEtfController {
    public static final String ETF_CREATION_ERROR_KEY = "etfCreationError";
    private final IEtfManagementService etfManagementService;

    public AddEtfController(IEtfManagementService etfManagementService) {
        this.etfManagementService = etfManagementService;
    }


    @PostMapping(value = "/addEtf")
    public String addEtf(@ModelAttribute("etfToCreate") EtfDto etfDetails,
                         @ModelAttribute("stocksCsvFile") MultipartFile stocksCsvFile,
                         RedirectAttributes redirectAttributes) {
        if (stocksCsvFile != null) {
            try {
                etfManagementService.createEtf(etfDetails, stocksCsvFile.getInputStream());
            } catch (IOException e) {
                log.error("IOException", e);
            } catch (IllegalArgumentException argEx) {
                log.error("ETF Input Error:", argEx);
                redirectAttributes.addFlashAttribute(ETF_CREATION_ERROR_KEY, argEx.getMessage());
                return "redirect:/";
            }
        }

        return "redirect:/";
    }

}
