package com.springsecurity.security;

import com.springsecurity.services.CoutomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(sessionConfig ->
                        sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**", "/auth/**").permitAll()
                        .requestMatchers("/adminDashBoard/**").hasRole("ADMIN")
                        .requestMatchers("/doctorDashBoard/**").hasRole("DOCTOR")
                        .requestMatchers("/recipientDashBoard/**").hasRole("RECIPIENT")
                        /*.requestMatchers(HttpMethod.DELETE, "/admin/**")
                            .hasAnyAuthority(APPOINTMENT_DELETE.name(),
                                USER_MANAGE.name())
                        .requestMatchers("/admin/**").hasRole(ADMIN.name())
                        .requestMatchers("/doctors/**").hasAnyRole(DOCTOR.name(), ADMIN.name())*/
                        .anyRequest().authenticated()
                );

        //.formLogin(Customizer.withDefaults());
        return http.build();
    }
}

    /*
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

