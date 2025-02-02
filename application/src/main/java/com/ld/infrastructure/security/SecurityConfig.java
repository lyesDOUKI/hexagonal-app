package com.ld.infrastructure.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtConverter jwtConverter;

    @Autowired
    public SecurityConfig(JwtConverter jwtConverter) {
        this.jwtConverter = jwtConverter;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity security) throws Exception{
        return security
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/api-docs/**")
                                .permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(this.jwtConverter))
                )
                .build();
    }
}
