package com.springsecurity.config.security;

import com.springsecurity.common.error.CoustomAccessDeniedHandler;
import com.springsecurity.services.CoutomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final CoutomUserDetailsService coutomUserDetailsService;
    private final JwtAuthFilter jwtAuthFilter;

    private final CoustomAccessDeniedHandler coustomAccessDeniedHandler;
    private final CustomAuthEntryPoint customAuthEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(coustomAccessDeniedHandler)   // 403
                        .authenticationEntryPoint(customAuthEntryPoint)  // 401
                )
                .authorizeHttpRequests(auth -> auth
                                    .requestMatchers("/auth/**","/swagger-ui/**",
                                            "/v3/api-docs/**",
                                            "/swagger-ui.html").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/doctor/**").hasRole("DOCTOR")
                        .requestMatchers("/recipient/**").hasRole("RECIPIENT")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}



    /*.userDetailsService(coutomUserDetailsService)
    .requestMatchers(HttpMethod.DELETE, "/admin/**")
                            .hasAnyAuthority(APPOINTMENT_DELETE.name(),
                                USER_MANAGE.name())
                        .requestMatchers("/admin/**").hasRole(ADMIN.name())
                        .requestMatchers("/doctors/**").hasAnyRole(DOCTOR.name(), ADMIN.name())
    //IMemoryUserDetailsManager use
    //@Bean
    UserDetailsService userDetailsService(){
        UserDetails user1 = User.withUsername("admin")
                .password(passwordEncoder.encode("pass"))
                .roles("ADMIN")
                .build();
        UserDetails user2 = User.withUsername("doctor")
                .password(passwordEncoder.encode("pass"))
                .roles("DOCTOR")
                .build();

        return  new InMemoryUserDetailsManager(user1,user2);
    }*/

