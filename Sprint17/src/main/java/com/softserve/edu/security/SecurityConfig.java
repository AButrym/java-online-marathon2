package com.softserve.edu.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final AuthenticationProvider provider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/img/**").permitAll()
                .anyRequest().authenticated()
            .and()
            .formLogin()
                .loginPage("/form-login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/home")
                .failureUrl("/form-login?error=true")
                .permitAll()
            .and()
            .logout()
                .logoutUrl("/perform-logout")
                .logoutSuccessUrl("/form-login?logout=true")
                .deleteCookies("JSESSIONID")
            .and()
            .exceptionHandling()
                .accessDeniedPage("/accessDenied")
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(provider);
    }

    @Bean
    public static PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
