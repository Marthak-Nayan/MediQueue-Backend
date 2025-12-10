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

    //
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "doctor_speciality",
//            joinColumns = @JoinColumn(name = "doctor_id"),
//            inverseJoinColumns = @JoinColumn(name = "speciality_id")
//    )
    @ElementCollection
    @CollectionTable(name = "doctor_specialities", joinColumns = @JoinColumn(name = "doctor_id"))
    @Column(name = "speciality")
    private List<String> specialties;

    @ManyToOne
    @JoinColumn(name = "place_id",nullable = false)
    private PlaceName placeName;
}
