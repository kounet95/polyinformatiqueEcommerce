package org.example.queryblog.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
  private JwtAuthConverter jwtAuthConverter;

  public SecurityConfig(JwtAuthConverter jwtAuthConverter) {
    this.jwtAuthConverter = jwtAuthConverter;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
      .cors(Customizer.withDefaults())
      .authorizeHttpRequests(ar->ar.requestMatchers("/products/**").permitAll())
      .authorizeHttpRequests(ar->ar.requestMatchers("/h2-console/**","/swagger-ui.html","/v3/**","/swagger-ui/**").permitAll())
      .authorizeHttpRequests(ar->ar.anyRequest().authenticated())
      .oauth2ResourceServer(o2->o2.jwt(jwt->jwt.jwtAuthenticationConverter(jwtAuthConverter)))
      .headers(h->h.frameOptions(fo->fo.disable()))
      .csrf(csrf->csrf.ignoringRequestMatchers("/h2-console/**"))
      .build();
  }



}

