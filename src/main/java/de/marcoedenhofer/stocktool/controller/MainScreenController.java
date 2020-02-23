package de.marcoedenhofer.stocktool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainScreenController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/compare")
    public String compare(@RequestParam("etf") String etfIsin,
                          @RequestParam("otherEtf") String otherEtfIsin) {
        return "compare";
    }
}
