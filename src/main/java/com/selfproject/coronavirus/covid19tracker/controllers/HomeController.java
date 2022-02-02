package com.selfproject.coronavirus.covid19tracker.controllers;

import com.selfproject.coronavirus.covid19tracker.services.CoronavirusDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private CoronavirusDataService coronavirusDataService;

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("locationStats", coronavirusDataService.getAllStats());

        return "home";
    }
}
