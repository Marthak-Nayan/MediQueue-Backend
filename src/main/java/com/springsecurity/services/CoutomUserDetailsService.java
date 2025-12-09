package com.springsecurity.services;

import com.springsecurity.Repository.UserRepository;
import com.springsecurity.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoutomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =(User) userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepository.findByUsername(username).orElseThrow();
//    }
}
