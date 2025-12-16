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
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final AuthUtils authUtils;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        log.info("incoming request: {}", request.getRequestURI());

        final String authHeader = request.getHeader("Authorization");

        // ✅ NO TOKEN → SKIP AUTH
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.split("Bearer ")[1];


        // ✅ INVALID TOKEN → SKIP AUTH
//        if (!authUtils.isValid(token)) {
//            filterChain.doFilter(request, response);
//            return;
//        }
        try {
            String username = authUtils.getUsernameFromToken(token);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = userRepository.findByUsername(username).orElseThrow();
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                user.getAuthorities()
                        );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        }catch (Exception e){
            filterChain.doFilter(request,response);
        }
    }


    /*@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try{
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
        }catch (Exception ex){
            handlerExceptionResolver.resolveException(request,response,null,ex);
        }
    }
    /*@Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {
            log.info("Incoming request: {}", request.getRequestURI());

            // Allow preflight requests
            if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
                filterChain.doFilter(request, response);
                return;
            }

            // Public endpoints
            if (request.getServletPath().equals("/auth/login")
                    || request.getServletPath().equals("/auth/signup")) {
                filterChain.doFilter(request, response);
                return;
            }

            String authHeader = request.getHeader("Authorization");

            // 1️⃣ Header missing or invalid
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token missing");
                return;
            }

            // 2️⃣ Token empty check
            String token = authHeader.substring(7).trim();
            if (token.isEmpty()) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token empty");
                return;
            }

            // 3️⃣ Extract username safely
            String username;
            try {
                username = authUtils.getUsernameFromToken(token);
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
                return;
            }

            // 4️⃣ Authenticate user
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                User user = userRepository.findByUsername(username)
                        .orElse(null);

                if (user == null) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not found");
                    return;
                }

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                user.getAuthorities()
                        );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

            filterChain.doFilter(request, response);

        } catch (Exception ex) {
            handlerExceptionResolver.resolveException(request, response, null, ex);
        }
    }*/
}
