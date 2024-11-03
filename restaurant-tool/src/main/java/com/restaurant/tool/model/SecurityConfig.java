package com.restaurant.tool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/admin/**").authenticated()  
                .anyRequest().permitAll() 
            )
            .formLogin((form) -> form
                .loginPage("/login")  
                .defaultSuccessUrl("/admin", true)  
                .permitAll()
            )
            .logout((logout) -> logout
                .logoutUrl("/logout")
                .invalidateHttpSession(true)  // Invalide la session
                .clearAuthentication(true)    // Efface l'authentification
                .deleteCookies("JSESSIONID")  // Supprime les cookies de session
                .logoutSuccessUrl("/")        // Redirige vers la page d'accueil après déconnexion
                .permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  
    }
}
