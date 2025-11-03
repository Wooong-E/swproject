package com.example.swproject.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/api/places/**", "/users/login", "/users/signup", "/css/**", "/js/**", "/images/**", "/uploads/**", "/attractions", "/attractions/**", "/restaurants", "/restaurants/**", "/cafes", "/cafes/**","/reviews/**", "/my-place", "/suggest", "/report", "/monthly-magazine", "/monthly-magazine/**").permitAll()
                        .requestMatchers("/courses/**").authenticated()
                        .anyRequest().authenticated()
                )
            .formLogin(form -> form
                .loginPage("/users/login")
                .loginProcessingUrl("/users/login")
                .usernameParameter("loginId")
                .passwordParameter("loginPw")
                .defaultSuccessUrl("/", true)
                .failureUrl("/users/login?error")
                .permitAll()
            )
                .logout(logout -> logout
                        .logoutUrl("/users/logout")
                        .logoutSuccessUrl("/users/login?logout") // Redirect to login page with a logout message
                        .permitAll()
                );
        return http.build();

    }
}
