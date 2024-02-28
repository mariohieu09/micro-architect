package org.example.gatewayservice.configuration;


import org.example.gatewayservice.dto.AuthorizeRequest;
import org.example.gatewayservice.exception.ForbiddenException;
import org.example.gatewayservice.exception.UnAuthorizeException;
import org.example.gatewayservice.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;

import static org.example.gatewayservice.constant.PermissionConstant.PERMISSION_CONSTANT;

@Component
public class CustomGatewayFilter implements GlobalFilter, Ordered {

    //TODO Encrypt the data

    private static final String TOKEN_PREFIX = "Bearer ";

    private static final String HEADER_AUTHORIZATION = "Authorization";
    @Autowired
    private  RestTemplate restTemplate;

    private static final String AUTHORIZE_URL_PATH = "http://localhost:8080/api/auth/authorize";


    @Autowired
    private  JwtUtil jwtUtil;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain){
        String urlPath = exchange.getRequest().getPath().value();
        if(PERMISSION_CONSTANT.getPermissions().containsKey(urlPath)) {
            String bearerToken = exchange.getRequest().getHeaders().getFirst(HEADER_AUTHORIZATION);
            if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
                String token = bearerToken.substring(TOKEN_PREFIX.length());
                // This list contains user credential (ex: user's permission to an endpoint)
                List<String> permissions = jwtUtil.extractPermission(token);
                //The specific permission to a resource endpoint
                String definePermission = PERMISSION_CONSTANT.getPermissions().get(urlPath);
//                String url = UriComponentsBuilder.fromUriString(AUTHORIZE_URL_PATH)
//                        .queryParam("token", token)
//                        .queryParam("permission", definePermission)
//                        .toUriString();
                AuthorizeRequest authorizeRequest = AuthorizeRequest.builder()
                        .token(token)
                        .checkingPermission(definePermission)
                        .build();
                ResponseEntity<Boolean> responseEntity = restTemplate.postForEntity(AUTHORIZE_URL_PATH, authorizeRequest,Boolean.class);
                Boolean bool = responseEntity.getBody();
                if(Boolean.FALSE.equals(bool)){
                    throw new ForbiddenException("User has no permission to access this resource!", new Date());
                }
            }else{
                throw new UnAuthorizeException("Unauthorized!", new Date());
            }
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
