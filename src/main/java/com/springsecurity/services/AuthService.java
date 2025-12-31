package com.springsecurity.services;

import com.springsecurity.DTO.*;
import com.springsecurity.Repository.*;
import com.springsecurity.entities.*;
import com.springsecurity.security.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.Exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtils authUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PlaceRepository placeRepository;
    private final DepartmentRepositary departmentRepository;
    private final MasterDepartmentRepo masterDepartmentRepo;

    @Transactional(rollbackFor = Exception.class)
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );

        User user = (User) authentication.getPrincipal();

        String token = authUtils.generateAccessToken(user);

        return new LoginResponseDto(token, user.getRole(),user.getId());
    }
    @Transactional(rollbackFor = Exception.class)
    public SignUpResponseDto signup(SignUpRequestDto signupRequestDto) {
        User user = userRepository.findByUsername(signupRequestDto.getUsername()).orElse(null);

        if(user != null) throw new IllegalArgumentException("User Already Exist");

        user = userRepository.save(User.builder()
                .role("ROLE_ADMIN")
                .username(signupRequestDto.getUsername())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .build()
        );
        PlaceName placeName = placeRepository.save(PlaceName.builder()
                .placename(signupRequestDto.getPlacename())
                .email(signupRequestDto.getEmail())
                .mobileNo(signupRequestDto.getMobile())
                .address(signupRequestDto.getAddress())
                .user(user)
                .build()
        );
        List<Long> deptIds = signupRequestDto.getDepartmentIds()
                .stream()
                .map(DepartmentDto::getId)
                .toList();

        List<MasterDepartment> masterdepartments = masterDepartmentRepo.findAllById(deptIds);
        List<Department> departments = new ArrayList<>();


        for(MasterDepartment department : masterdepartments){
            Department depart = departmentRepository.save(Department.builder()
                    .departmentName(department.getName())
                    .placeName(placeName)
                    .description(department.getDescription())
                    .active(true)
                    .place(placeName.getPlacename())
                    .build());
            departments.add(depart);
        }
        placeName.setDepartments(departments);
        placeRepository.save(placeName);

        // Simulate internal error
        if ("error".equals(signupRequestDto.getPlacename())) {
            throw new RuntimeException("Internal error! Rolling back...");
        }

        return new SignUpResponseDto(user.getId(),user.getUsername(),placeName.getPlacename());
    }

    public List<MasterDepartment> getAllDepartments(){
        return masterDepartmentRepo.findAll();
    }
}
