package com.softserve.edu.controller;


import com.softserve.edu.model.Marathon;
import com.softserve.edu.model.User;
import com.softserve.edu.service.MarathonService;
import com.softserve.edu.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;


@Controller
@AllArgsConstructor
public class MarathonController {
    private final Logger logger = LoggerFactory.getLogger(MarathonController.class);

    private final MarathonService marathonService;
    private final UserService studentService;


    @GetMapping("/create-marathon")
    public String createMarathon(Model model) {
        logger.info("GET '/create-marathon': Create marathon");
        model.addAttribute("marathon", new Marathon());
        return "create-marathon";
    }

    @PostMapping("/marathons")
    public String createMarathon(
            @Validated @ModelAttribute Marathon marathon,
            BindingResult result)
    {
        logger.info("POST '/marathons': Create marathon");
        if (result.hasErrors()) {
            return "create-marathon";
        }
        marathonService.createOrUpdate(marathon);
        return "redirect:/marathons";
    }

    @GetMapping("/marathons/edit/{id}")
    public String updateMarathon(@PathVariable long id, Model model) {
        logger.info("GET '/marathons/edit/{}': Edit marathon with id = '{}'", id, id);
        Marathon marathon = marathonService.getMarathonById(id);
        model.addAttribute("marathon", marathon);
        return "update-marathon";
    }

    @PostMapping("/marathons/edit/{id}")
    public String updateMarathon(@PathVariable long id, @ModelAttribute Marathon marathon, BindingResult result) {
        logger.info("POST '/marathons/edit/{}': Edit marathon[id='{}']", id, id);
        if (result.hasErrors()) {
            return "update-marathon";
        }
        marathonService.createOrUpdate(marathon);
        return "redirect:/marathons";
    }

    @GetMapping("/marathons/delete/{id}")
    public String deleteMarathon(@PathVariable long id) {
        logger.info("GET '/marathons/delete/{}': Delete marathon[id='{}']", id, id);
        marathonService.deleteMarathonById(id);
        return "redirect:/marathons";
    }

    @GetMapping("/students/{marathonId}")
    public String getStudentsFromMarathon(@PathVariable("marathonId") long marathonId, Model model) {
        logger.info("GET '/students/{}': Students for marathon[id='{}']",
                marathonId, marathonId);
        List<User> students = studentService.getAll().stream().filter(
                student -> student.getMarathons().stream().anyMatch(
                        marathon -> marathon.getId() == marathonId)).collect(Collectors.toList());
        Marathon marathon = marathonService.getMarathonById(marathonId);
        model.addAttribute("students", students);
        model.addAttribute("all_students", studentService.getAll());
        model.addAttribute("marathon", marathon);
        return "marathon-students";
    }

    @GetMapping("/marathons")
    public String getAllMarathons(Model model) {
        logger.info("GET '/marathons': Show all marathons");
        List<Marathon> marathons = marathonService.getAll();
        model.addAttribute("marathons", marathons);
        return "marathons";
    }

}
