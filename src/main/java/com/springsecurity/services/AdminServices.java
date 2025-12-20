package com.springsecurity.services;

import com.springsecurity.DTO.*;
import com.springsecurity.Repository.*;
import com.springsecurity.entities.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServices {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final DoctorRepository doctorRepository;
    private final RecipientRepository recipientRepository;
    private final DepartmentRepositary departmentRepositary;

    public PlaceResponse getPlaceByUsername(String username){
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        PlaceName placeName = placeRepository.findByUser(currentUser)
                .orElseThrow(() -> new RuntimeException("Place not found"));

        return PlaceResponse.builder()
                .id(placeName.getId())
                .placename(placeName.getPlacename())
                .email(placeName.getEmail())
                .address(placeName.getAddress())
                .mobileno(placeName.getMobileNo())
                .username(currentUser.getUsername())
                .build();
    }

    public PlaceResponse updatePlace(PlaceUpdateReq placeUpdateReq,User user){
        PlaceName placeName = placeRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Place not found"));
        if(placeUpdateReq.getPlacename() != null){
            placeName.setPlacename(placeUpdateReq.getPlacename());
        }
        if(placeUpdateReq.getAddress() != null){
            placeName.setAddress(placeUpdateReq.getAddress());
        }
        if(placeUpdateReq.getEmail() != null){
            placeName.setEmail(placeUpdateReq.getEmail());
        }
        if(placeUpdateReq.getMobileNo() != null){
            placeName.setMobileNo(placeUpdateReq.getMobileNo());
        }
        placeRepository.save(placeName);
        return PlaceResponse.builder()
                .id(placeName.getId())
                .username(user.getUsername())
                .placename(placeName.getPlacename())
                .email(placeName.getEmail())
                .address(placeName.getAddress())
                .mobileno(placeName.getMobileNo())
                .build();
    }

    public CreateDepartmentResp createDepartment(CreateDepartmentReq req,User user){
        PlaceName placeName = placeRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Place not found"));

        if(departmentRepositary.existsByDepartmentNameAndPlaceName(req.getDepartmentName(), placeName)){
            throw new RuntimeException("Department already exists");
        }
        Department department = Department.builder()
                .departmentName(req.getDepartmentName())
                .placeName(placeName)
                .description(req.getDescription())
                .active(true)
                .build();
        departmentRepositary.save(department);
        return CreateDepartmentResp.builder()
                .id(department.getId())
                .departmentName(department.getDepartmentName())
                .description(department.getDescription())
                .active(department.getActive())
                .build();
    }


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

    public List<Doctor> getAllDoctors(){
        return doctorRepository.findAll();
    }

    public List<Recipient> getAllRecipient(){
        return recipientRepository.findAll();
    }


    /*
    get Place details
    Create a Doctor/Recipient
    Get all Doctor/Recipient
    get specific Doctor/Recipient
    update data of specific Doctor/Recipient
    get All/specific Patient Records (Can not do Update)
    Get Total Revnue
    Manage Departments (Cardiology, Orthopedic, etc.)
    View appointment reports

    Can See Doctor/Recipient active time when Login and Logout
    Daily / monthly reports
    Update Admin/Place Information
     */
}
