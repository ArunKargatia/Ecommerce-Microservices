package com.exercise.orderManagement.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private JwtAuthFilter jwtAuthFilter;
	
	@Bean 
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
            		.requestMatchers("/api/orders/all", "/api/orders/order-details/all").hasRole("ADMIN")
            		.requestMatchers("/api/analytics/**").hasRole("ADMIN")
            		.requestMatchers(HttpMethod.POST, "/api/orders").authenticated()
            		.requestMatchers(HttpMethod.GET, "/api/orders/*").hasAnyRole("USER", "ADMIN")
            		.requestMatchers(HttpMethod.DELETE, "/api/orders/*/cancel").authenticated()
                .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex
            	    .authenticationEntryPoint((request, response, authException) -> {
            	        response.setContentType("application/json");
            	        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            	        response.getWriter().write("{\"error\": \"" + authException.getMessage() + "\"}");
            	    })
            	    .accessDeniedHandler((request, response, accessDeniedException) -> {
            	        response.setContentType("application/json");
            	        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            	        response.getWriter().write("{\"error\": \"" + accessDeniedException.getMessage() + "\"}");
            	    })
            	)

            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
	 
	 @Bean
	 AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		 return config.getAuthenticationManager();
	 }
}
