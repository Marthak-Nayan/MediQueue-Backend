package com.springsecurity.admin.services;

import com.springsecurity.Repository.*;
import com.springsecurity.admin.DTO.*;
import com.springsecurity.entities.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServices {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final DoctorRepository doctorRepository;
    private final StaffRepository staffRepository;
    private final DepartmentRepositary departmentRepository;

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

    public PlaceResponse updatePlace(PlaceUpdateReq placeUpdateReq, User user){
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

    //Department Services
    public CreateDepartmentResp createDepartment(CreateDepartmentReq req, User user){
        PlaceName placeName = placeRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Place not found"));

        if(departmentRepository.existsByDepartmentNameAndPlaceName(req.getDepartmentName(), placeName)){
            throw new RuntimeException("Department already exists");
        }
        Department department = Department.builder()
                .departmentName(req.getDepartmentName())
                .placeName(placeName)
                .description(req.getDescription())
                .active(true)
                .place(placeName.getPlacename())
                .build();
        departmentRepository.save(department);

        return CreateDepartmentResp.builder()
                .id(department.getId())
                .departmentName(department.getDepartmentName())
                .description(department.getDescription())
                .active(department.getActive())
                .placeId(placeName.getId())
                .placeName(placeName.getPlacename())
                .build();
    }

    public CreateDepartmentResp updateDepartment(Long id,UpdateDepartmentReq req,User user) {

        PlaceName placeName = placeRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Place not found"));

        Department department = departmentRepository
                .findByIdAndPlaceName(id, placeName)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        if (req.getDepartmentName() != null &&
                departmentRepository.existsByDepartmentNameAndPlaceNameAndIdNot(
                        req.getDepartmentName(),
                        placeName,
                        id)) {

            throw new RuntimeException("Department already exists");
        }

        if (req.getDepartmentName() != null) {
            department.setDepartmentName(req.getDepartmentName());
        }

        if (req.getDescription() != null) {
            department.setDescription(req.getDescription());
        }

        if (req.getActive() != null){
            department.setActive(req.getActive());
        }

        departmentRepository.save(department);

        return CreateDepartmentResp.builder()
                .id(department.getId())
                .departmentName(department.getDepartmentName())
                .description(department.getDescription())
                .active(department.getActive())
                .placeId(placeName.getId())
                .placeName(placeName.getPlacename())
                .build();
    }

    public void deleteDepartment(Long depId,User user){
        PlaceName placeName = placeRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Place not found"));
        Department department = departmentRepository
                .findByIdAndPlaceName(depId, placeName)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        department.setActive(false);
        departmentRepository.save(department);
    }

    public List<CreateDepartmentResp> getAllDepartment(User user){
        PlaceName placeName = placeRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Place not found"));
        List<Department> departments = departmentRepository.findByPlaceName(placeName);


        return  departments.stream()
                .map(department -> CreateDepartmentResp.builder()
                        .id(department.getId())
                        .departmentName(department.getDepartmentName())
                        .description(department.getDescription())
                        .active(department.getActive())
                        .placeName(department.getPlace())
                        .placeId(department.getPlaceName().getId())
                        .build()
                )
                .toList();
    }



    //Doctor Tasks
    @Transactional(rollbackFor = Exception.class)
    public CreateDoctorRespDto createDoctor(CreateDoctorRequestDto req, User user2) {

        User user = userRepository.findByUsername(req.getUsername()).orElse(null);
        if (user != null) throw new IllegalArgumentException("Doctor already exists");

        // Get place
        PlaceName placeName = placeRepository.findByUser(user2)
                .orElseThrow(() -> new RuntimeException("User not belongs to this Place"));
        user = userRepository.save(User.builder()
                .role("ROLE_"+req.getRole())
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .build()
        );
        // Load departments from DB
        List<Department> departments = departmentRepository.findAllById(req.getDepartmentIds());

        // Optional: validate departments belong to the same place
        for (Department dep : departments) {
            if (!dep.getPlaceName().getId().equals(placeName.getId())) {
                throw new RuntimeException("Department does not belong to user's place");
            }
        }
        // Create doctor
        Doctor doctor = Doctor.builder()
                .doctorName(req.getDoctorName())
                .mobileNo(req.getMobileNo())
                .designation(req.getDesignation())
                .dateOfBirth(req.getDob())
                .email(req.getEmail())
                .gender(req.getGender())
                .experienceYears(req.getExperienceYears())
                .joiningDate(req.getJoiningDate())
                .active(false)
                .employmentType(req.getEmploymentType())
                .address(req.getAddress())
                .registrationNumber(req.getRegistrationNo())
                .qualification(req.getQualification())
                .user(user)
                .placeName(placeName)
                .departments(departments)  // JPA managed entities
                .build();
        doctorRepository.save(doctor);

        return CreateDoctorRespDto.builder()
                .id(doctor.getId())
                .doctorName(doctor.getDoctorName())
                .role(user.getRole())
                .mobileNo(doctor.getMobileNo())
                .gender(doctor.getGender())
                .email(doctor.getEmail())
                .registrationNo(doctor.getRegistrationNumber())
                .username(user.getUsername())
                .depIds(doctor.getDepartments().stream().map(Department::getId).toList())
                .placeId(placeName.getId())
                .build();
    }

    public List<DoctorRecordsResp> getAllDoctors(User user){
        PlaceName placeName = placeRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("User not belongs to this Place"));

        List<Doctor> doctors = doctorRepository.findByPlaceName(placeName);

        return doctors.stream().map(doctor -> DoctorRecordsResp.builder()
                .id(doctor.getId())
                .placeId(doctor.getPlaceName().getId())
                .role(doctor.getUser().getRole())
                .username(doctor.getUser().getUsername())
                .departmentName(
                        doctor.getDepartments()
                                .stream()
                                .map(Department::getDepartmentName)
                                .toList()
                )
                .doctorName(doctor.getDoctorName())
                .build()
        ).toList();
    }

    public DoctorRecordsResp getSpecificDoctor(Long id,User user){
        PlaceName placeName = placeRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("User not belongs to this Place"));

        Doctor doctor = doctorRepository.getByIdAndPlaceName(id,placeName).orElseThrow(() -> new RuntimeException("Doctor was not Found"));
        return DoctorRecordsResp.builder()
                .id(doctor.getId())
                .placeId(doctor.getPlaceName().getId())
                .role(doctor.getUser().getRole())
                .username(doctor.getUser().getUsername())
                .departmentName(
                        doctor.getDepartments()
                                .stream()
                                .map(Department::getDepartmentName)
                                .toList()
                )
                .doctorName(doctor.getDoctorName())
                .build();
    }




    //Staff Services Operations
    public CreateStaffResDto createStaff(CreateStafftReqDto req, User user2){
        User user = userRepository.findByUsername(req.getUsername()).orElse(null);
        if (user != null) throw new IllegalArgumentException("Staff already exists");

        // Get place
        PlaceName placeName = placeRepository.findByUser(user2)
                .orElseThrow(() -> new RuntimeException("User not belongs to this Place"));


        user = userRepository.save(User.builder()
                .role("ROLE_"+req.getRole())
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .build()
        );

        Staff staff = staffRepository.save(Staff.builder()
                        .staffName(req.getStaffName())
                        .gender(req.getGender())
                        .email(req.getEmail())
                        .role(user.getRole())
                        .dob(req.getDob())
                        .address(req.getAddress())
                        .employmentType(req.getEmploymentType())
                        .experienceYears(req.getExperienceYears())
                        .joiningDate(req.getJoiningDate())
                        .mobileNo(req.getMobileNo())
                        .active(req.getActive())
                        .user(user)
                        .placeName(placeName)
                        .build()
        );

        return CreateStaffResDto.builder()
                .id(staff.getId())
                .username(user.getUsername())
                .placeId(staff.getPlaceName().getId())
                .role(staff.getRole())
                .joiningDate(staff.getJoiningDate())
                .active(staff.getActive())
                .email(staff.getEmail())
                .employmentType(req.getEmploymentType())
                .mobileNo(staff.getMobileNo())
                .staffName(staff.getStaffName())
                .build();
    }


//    public List<CreateStaffResDto> getAllStaff(User user){
//        PlaceName placeName = placeRepository.findByUser(user)
//                .orElseThrow(() -> new RuntimeException("User not belongs to this Place"));
//
//        List<Staff> staff = staffRepository.findByPlaceName(placeName);
//
//        return staff.stream().map(Staff -> CreateStaffResDto.builder()
//                .
//                .build();
//    }
//    public List<CreateStaffResDto> getAllSpecificStaffRole(User user,String staffRole){
//
//    }
//    public CreateStaffResDto getSpecificStaff(User user,Long id){
//
//    }

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
