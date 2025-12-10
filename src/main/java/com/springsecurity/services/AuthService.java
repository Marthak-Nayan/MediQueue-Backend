package com.springsecurity.services;

import com.springsecurity.DTO.*;
import com.springsecurity.Repository.DoctorRepository;
import com.springsecurity.Repository.PlaceRepository;
import com.springsecurity.Repository.UserRepository;
import com.springsecurity.entities.Doctor;
import com.springsecurity.entities.PlaceName;
import com.springsecurity.entities.User;
import com.springsecurity.security.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtils authUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DoctorRepository doctorRepository;
    private final PlaceRepository placeRepository;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );

        User user = (User) authentication.getPrincipal();

        String token = authUtils.generateAccessToken(user);

        return new LoginResponseDto(token, user.getRole(),user.getId());
    }

    public SignUpResponseDto signup(SignUpRequestDto signupRequestDto) {
        User user = userRepository.findByUsername(signupRequestDto.getUsername()).orElse(null);

        if(user != null) throw new IllegalArgumentException("User Already Exist");

         user = userRepository.save(User.builder()
                .role(signupRequestDto.getRole())
                .username(signupRequestDto.getUsername())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .build()
        );
        PlaceName placeName = placeRepository.save(PlaceName.builder()
                .placename(signupRequestDto.getPlace_name())
                .user(user)
                .build()
        );
        return new SignUpResponseDto(user.getId(),placeName.getPlacename(), user.getUsername());
    }
}
