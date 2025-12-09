package com.springsecurity.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name ="users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true,nullable = false)
    private String username;

    @Column(unique = true,nullable = false)
    private String password;

    @Column(unique = false,nullable = false)
    private String role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
