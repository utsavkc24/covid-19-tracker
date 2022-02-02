package com.selfproject.coronavirus.covid19tracker.controllers;

import java.util.List;

import com.selfproject.coronavirus.covid19tracker.models.LocationStats;
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

        List<LocationStats> allStats = coronavirusDataService.getAllStats();
        int totalReportedCases = allStats.stream().mapToInt(stats -> stats.getLatestTotalCases()).sum();
        int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();

        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);

        return "home";
    }
}
