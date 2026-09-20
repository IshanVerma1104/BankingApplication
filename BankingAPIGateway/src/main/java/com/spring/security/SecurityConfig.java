package com.spring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.security.config.Customizer;
import org.springframework.security.core.userdetails.User;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {

    	http.cors(Customizer.withDefaults());
    	
        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(auth -> auth

                .requestMatchers(
                        "/swagger-ui/**",
                        "/v3/api-docs/**")
                .permitAll()

                .requestMatchers(HttpMethod.OPTIONS,
                		"/**")
                		.permitAll()
                // Public User APIs
                .requestMatchers("/account/**")
                .permitAll()

                // Protected Admin APIs
                .requestMatchers(
                        "/customers/register",
                        "/customers/login"
                )
                .permitAll()

                .requestMatchers("/customers/**")
                .authenticated()

                .requestMatchers("/accounts/**")
                .authenticated()

                .anyRequest()
                .authenticated());

        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails admin =
                User.withUsername("admin")
                        .password("{noop}admin123")
                        .roles("ADMIN")
                        .build();

        return new InMemoryUserDetailsManager(admin);
    }
   
        @Bean
        public WebMvcConfigurer corsConfigurer() {

            return new WebMvcConfigurer() {

                @Override
                public void addCorsMappings(
                        CorsRegistry registry) {

                    registry.addMapping("/**")
                            .allowedOrigins(
                                    "http://localhost:3000")
                            .allowedMethods(
                                    "GET",
                                    "POST",
                                    "PUT",
                                    "DELETE",
                                    "OPTIONS")
                            .allowedHeaders("*");
                }
            };
        }
}