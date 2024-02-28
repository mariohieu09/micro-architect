package org.example.gatewayservice.configuration;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GatewayConfig {


//    @Bean
//    public GlobalFilter customGlobalFilter(){
//        return new CustomGatewayFilter();
//    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
