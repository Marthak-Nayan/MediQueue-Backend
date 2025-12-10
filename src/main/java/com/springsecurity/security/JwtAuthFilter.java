package com.springsecurity.security;

import com.springsecurity.Repository.UserRepository;
import com.springsecurity.entities.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final AuthUtils authUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("incoming request: {}",request.getRequestURI());

        final String req_token_header=request.getHeader("Authorization");
        if(req_token_header==null || !req_token_header.startsWith("Bearer")){
            filterChain.doFilter(request,response);
            return;
        }

        String token = req_token_header.split("Bearer ")[1];
        String username = authUtils.getUsernameFromToken(token);

        if(username != null && SecurityContextHolder.getContext().getAuthentication()==null){
            User user = userRepository.findByUsername(username).orElseThrow();
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(user,null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        filterChain.doFilter(request,response);
    }
}
