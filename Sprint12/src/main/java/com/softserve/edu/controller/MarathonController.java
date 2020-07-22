package com.softserve.edu.controller;

import com.softserve.edu.service.MarathonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MarathonController {

    private MarathonService marathonService;

    @Value("${welcome.message}")
    private String message;

    @Autowired
    public MarathonController(MarathonService marathonService) {
        this.marathonService = marathonService;
    }

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        model.addAttribute("message", message);
        return "index";
    }

}
