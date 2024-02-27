package org.example.gatewayservice.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.gatewayservice.entity.CustomUserDetail;
import org.example.gatewayservice.entity.Permission;
import org.example.gatewayservice.entity.User;
import org.example.gatewayservice.repository.PermissionRepository;
import org.example.gatewayservice.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class JwtFilterChain implements WebFilter {

    private static final String TOKEN_PREFIX = "Bearer ";

    private static final String HEADER_AUTHORIZATION = "Authorization";
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PermissionRepository permissionRepository;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String bearerToken =  exchange.getRequest().getHeaders().getFirst(HEADER_AUTHORIZATION);
        if(bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            assert bearerToken != null;
            String token = bearerToken.substring(TOKEN_PREFIX.length());
            Set<Permission> credentialSet = new HashSet<>();
            List<String> permissions = jwtUtil.extractPermission(token);
            credentialSet.addAll(permissions.stream().map(p -> permissionRepository.getPermissionByName(p).get()).toList());
            User user = User.builder()
                    .username(jwtUtil.extractUsername(token))
                    .extraPermission(credentialSet)
                    .build();
            UserDetails userDetails = new CustomUserDetail(user);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );
//            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(exc));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        return chain.filter(exchange);
    }
}
