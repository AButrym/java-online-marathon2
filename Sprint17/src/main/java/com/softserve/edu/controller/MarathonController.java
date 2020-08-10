package com.softserve.edu.controller;

import com.softserve.edu.model.Marathon;
import com.softserve.edu.model.User;
import com.softserve.edu.service.MarathonService;
import com.softserve.edu.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class MarathonController {
    private final MarathonService marathonService;
    private final UserService studentService;

    @GetMapping("/create-marathon")
    public String createMarathon(Model model) {
        model.addAttribute("marathon", new Marathon());
        return "create-marathon";
    }

    @PostMapping("/marathons")
    public String createMarathon(
            @Validated @ModelAttribute Marathon marathon,
            BindingResult result) {
        if (result.hasErrors()) {
            return "create-marathon";
        }
        marathonService.createOrUpdate(marathon);
        return "redirect:/marathons";
    }

    @GetMapping("/marathons/edit/{id}")
    public String updateMarathon(@PathVariable long id, Model model) {
        Marathon marathon = marathonService.getMarathonById(id);
        model.addAttribute("marathon", marathon);
        return "update-marathon";
    }

    @PostMapping("/marathons/edit/{id}")
    public String updateMarathon(@PathVariable long id, @ModelAttribute Marathon marathon, BindingResult result) {
        if (result.hasErrors()) {
            return "update-marathon";
        }
        marathonService.createOrUpdate(marathon);
        return "redirect:/marathons";
    }

    @GetMapping("/marathons/delete/{id}")
    public String deleteMarathon(@PathVariable long id) {
        marathonService.deleteMarathonById(id);
        return "redirect:/marathons";
    }

    @GetMapping("/students/{marathon_id}")
    public String getStudentsFromMarathon(@PathVariable("marathon_id") long marathonId, Model model) {
        Marathon marathon = marathonService.getMarathonById(marathonId);
        Set<User> students = marathon.getUsers();
        model.addAttribute("students", students);
        model.addAttribute("all_students", studentService.getAll());
        model.addAttribute("marathon", marathon);
        return "marathon-students";
    }

    @GetMapping("/marathons")
    public String getAllMarathons(
            Model model,
            Authentication authentication
    ) {
        List<Marathon> marathons = marathonService.getAll();
        boolean isMentor = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ROLE_MENTOR"::equals);
        if (isMentor) {
            model.addAttribute("mentor", true);
        } else {
            String userEmail = authentication.getName();
            marathons.removeIf(m -> m.getUsers().stream()
                    .map(User::getEmail)
                    .noneMatch(userEmail::equals)
            );
        }
        model.addAttribute("marathons", marathons);
        return "marathons";
    }

}
