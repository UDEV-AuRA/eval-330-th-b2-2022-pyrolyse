package com.ipiecoles.java.eval.th330.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class accueil {

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/"
    )
    public String employe() {

        return "accueil";
    }

}
