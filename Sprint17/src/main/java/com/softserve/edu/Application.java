package com.softserve.edu;

import com.softserve.edu.service.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@AllArgsConstructor
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class Application {
    private final UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner init() { return (String[] args) ->
        {
            System.out.println("INIT STARTED");
            userService.registerAccounts();
            System.out.println("INIT ENDED");
        };
    }
}
