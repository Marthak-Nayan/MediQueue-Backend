package com.springsecurity.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "placename")
public class PlaceName {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false,unique = true)
    private String placename;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private Long mobileNo;

    @Column(nullable = false)
    private String address;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true, updatable = false)
    private User user;

    @OneToMany(mappedBy = "placeName", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Department> departments = new ArrayList<>();

}
