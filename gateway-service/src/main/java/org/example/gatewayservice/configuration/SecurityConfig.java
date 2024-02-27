package org.example.gatewayservice.configuration;

import org.example.gatewayservice.constant.PermissionConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.WebFilter;

import java.util.Map;

import static org.example.gatewayservice.constant.PermissionConstant.PERMISSION_CONSTANT;

@Configuration
@EnableWebFluxSecurity

public class SecurityConfig
{

    @Bean
    public WebFilter jwtFilter(){
        return new JwtFilterChain();
    }

    @Bean
    @Order(0)
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {
        return http.cors(Customizer.withDefaults())
                        .csrf().disable()
                        .authorizeExchange(auth -> {
                         auth.pathMatchers("/api/**").permitAll();
                        }).
        build();
    }
}
