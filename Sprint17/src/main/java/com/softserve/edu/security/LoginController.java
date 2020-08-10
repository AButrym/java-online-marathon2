package com.softserve.edu.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @GetMapping("/form-login")
    public String login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout
    ) {
        return "login-page";
    }

    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "403";
    }
}
