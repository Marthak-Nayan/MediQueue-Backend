package com.springsecurity.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ================= BASIC INFO =================
    @Column(nullable = false)
    private String doctorName;

    @Column(nullable = false, unique = true)
    private Long mobileNo;

    @Column(unique = true)
    private String email;

    private String gender;

    private LocalDate dateOfBirth;

    private String address;

    // ================= PROFESSIONAL INFO =================
    @Column(nullable = false, unique = true)
    private String registrationNumber; // Medical License No

    private String qualification; // MBBS, MD, MS

    private Integer experienceYears;

    private String designation; // Consultant, Surgeon, etc.

    // ================= HOSPITAL INFO =================
    private String employmentType; // FULL_TIME / PART_TIME / VISITING

    private LocalDate joiningDate;

    @Column(nullable = false)
    private Boolean active = false;

    // ================= RELATIONSHIPS =================

    // Login Mapping
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    // Hospital / Place
    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false)
    private PlaceName placeName;

    // Doctor can belong to multiple departments
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "doctor_department",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id")
    )
    private List<Department> departments;
}


/*
package com.springsecurity.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String doctorname;

    @Column(nullable = false,unique = true)
    private Long mobileNo;



    //
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "place_id",nullable = false)
    private PlaceName placeName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "doctor_department",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id")
    )
    private List<Department> departments;
}
*/