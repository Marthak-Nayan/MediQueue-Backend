package com.springsecurity.services;

import com.springsecurity.DTO.CreateDoctorRequestDto;
import com.springsecurity.DTO.CreateDoctorRespDto;
import com.springsecurity.DTO.CreateRecipientReqDto;
import com.springsecurity.DTO.CreateRecipientResDto;
import com.springsecurity.Repository.DoctorRepository;
import com.springsecurity.Repository.PlaceRepository;
import com.springsecurity.Repository.RecipientRepository;
import com.springsecurity.Repository.UserRepository;
import com.springsecurity.entities.Doctor;
import com.springsecurity.entities.PlaceName;
import com.springsecurity.entities.Recipient;
import com.springsecurity.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServices {

    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final PasswordEncoder passwordEncoder;
    private final DoctorRepository doctorRepository;
    private final RecipientRepository recipientRepository;

    public CreateDoctorRespDto createDoctor(CreateDoctorRequestDto createDoctorRequestDto){
        User user = userRepository.findByUsername(createDoctorRequestDto.getUsername()).orElse(null);
        if(user != null) throw new IllegalArgumentException("User Already Exist");


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); // logged-in username

        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        PlaceName placeName = placeRepository.findByUser(currentUser)
                .orElseThrow(() -> new RuntimeException("Place not found"));


        user = userRepository.save(User.builder()
                .role(createDoctorRequestDto.getRole())
                .username(createDoctorRequestDto.getUsername())
                .password(passwordEncoder.encode(createDoctorRequestDto.getPassword()))
                .build()
        );

        Doctor doctor = doctorRepository.save(Doctor.builder()
                .doctorname(createDoctorRequestDto.getDoctorName())
                .user(user)
                .placeName(placeName)
                .specialties(createDoctorRequestDto.getSpeciality())
                .build()
        );

        return new CreateDoctorRespDto(doctor.getId(), doctor.getDoctorname(), user.getUsername());
    }

    public CreateRecipientResDto createRecipient(CreateRecipientReqDto createRecipientReqDto){
        User user = userRepository.findByUsername(createRecipientReqDto.getUsername()).orElse(null);
        if(user != null) throw new IllegalArgumentException("User Already Exist");


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); // logged-in username

        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        PlaceName placeName = placeRepository.findByUser(currentUser)
                .orElseThrow(() -> new RuntimeException("Place not found"));


        user = userRepository.save(User.builder()
                .role(createRecipientReqDto.getRole())
                .username(createRecipientReqDto.getUsername())
                .password(passwordEncoder.encode(createRecipientReqDto.getPassword()))
                .build()
        );

        Recipient recipient = recipientRepository.save(Recipient.builder()
                .recipientName(createRecipientReqDto.getRecipientName())
                .user(user)
                .placeName(placeName)
                .build()
        );

        return new CreateRecipientResDto(recipient.getId(), recipient.getRecipientName(), user.getUsername());
    }
}
