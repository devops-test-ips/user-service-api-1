package com.devops.user_service_api_1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthConvertor jwtAuthConvertor;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        security.csrf(AbstractHttpConfigurer::disable);
        security.authorizeHttpRequests(authorize -> {
            authorize
                    .requestMatchers(HttpMethod.POST, "user-service/api/v1/users/signup").permitAll()
                    .requestMatchers(HttpMethod.POST, "user-service/api/v1/users/login").permitAll()
                    .anyRequest().authenticated();
        });
        security.oauth2ResourceServer(t -> {
            t.jwt(jwtConfigure -> jwtConfigure.jwtAuthenticationConverter(jwtAuthConvertor));
            //t.jwt((Customizer.withDefaults()));
        });
        security.sessionManagement(t -> t.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return security.build();
    }

    @Bean
    public DefaultMethodSecurityExpressionHandler methodSecurity() {
        DefaultMethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler =
                new DefaultMethodSecurityExpressionHandler();
        defaultMethodSecurityExpressionHandler.setDefaultRolePrefix("");
        return defaultMethodSecurityExpressionHandler;
    }
}
