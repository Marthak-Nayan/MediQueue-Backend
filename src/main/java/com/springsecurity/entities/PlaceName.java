package com.springsecurity.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
}
