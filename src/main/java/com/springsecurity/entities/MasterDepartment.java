package com.springsecurity.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "master_department")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MasterDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    private Boolean active = true;
}
