package org.example.ecpolyquery.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
   private JwtAuthConverter jwtAuthConverter;

    public SecurityConfig(JwtAuthConverter jwtAuthConverter) {
        this.jwtAuthConverter = jwtAuthConverter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {


        return httpSecurity
                .cors(Customizer.withDefaults())
                .sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/api/categories/**").hasAnyAuthority("ADMIN", "USER","TAILLEUR")
                                .requestMatchers("api/customers/**").hasAnyAuthority("ADMIN", "USER","TAILLEUR")
                                .requestMatchers("/api/invoices/**").hasAnyAuthority("ADMIN", "USER","TAILLEUR")
                                .requestMatchers("/api/orders/**").hasAnyAuthority("ADMIN", "USER","TAILLEUR")
                                .requestMatchers("/api/orderstatus/**").hasAnyAuthority("ADMIN", "USER","TAILLEUR")
                                .requestMatchers("/api/orderlines/**").hasAnyAuthority("ADMIN", "USER","TAILLEUR")
                                .requestMatchers("api/products/**").hasAnyAuthority("ADMIN", "USER","TAILLEUR")
                                .requestMatchers("/api/productsizes/**").hasAnyAuthority("ADMIN", "USER","TAILLEUR")
                                .requestMatchers("/api/purchases/**").hasAnyAuthority("ADMIN", "USER","TAILLEUR")
                                .requestMatchers("/api/purchaseitems/**").hasAnyAuthority("ADMIN", "USER","TAILLEUR")
                                .requestMatchers("/api/shippings/**").hasAnyAuthority("ADMIN", "USER","TAILLEUR")
                                .requestMatchers("/api/socialgroups/**").hasAnyAuthority("ADMIN")
                                .requestMatchers("/api/stocks/**").hasAnyAuthority("ADMIN")
                                .requestMatchers("/api/suppliers/**").hasAnyAuthority("ADMIN", "USER","TAILLEUR")
                                .requestMatchers("/api/subcategories/**").hasAnyAuthority("ADMIN", "USER","TAILLEUR")
                                .requestMatchers("/swagger-ui/**").hasAnyAuthority( "USER")
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)
                        ))

                .build();


    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
