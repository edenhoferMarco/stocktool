package de.marcoedenhofer.stocktool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AddEtfController {

    @RequestMapping(value = "/addEtf", method = RequestMethod.POST)
    public String addEtf() {
        return "redirect:/";
    }

}
