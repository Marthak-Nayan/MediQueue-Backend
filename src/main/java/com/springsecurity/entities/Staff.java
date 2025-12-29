package com.springsecurity.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "staff")
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Basic details
    private String staffName;
    private Long mobileNo;
    private String email;
    private String gender;
    private LocalDate dob;
    private String address;

    // Role & job details
    private String role;              // RECEPTIONIST, NURSE, LAB_TECHNICIAN
    private Integer experienceYears;
    private LocalDate joiningDate;
    private String employmentType;    // FULL_TIME / PART_TIME / CONTRACT
    private Boolean active = true;

    // Login / Security
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    // Hospital / Clinic
    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false)
    private PlaceName placeName;
}
