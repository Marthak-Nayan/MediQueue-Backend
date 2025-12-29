package com.springsecurity.Repository;

import com.springsecurity.entities.Doctor;
import com.springsecurity.entities.PlaceName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {
    //Optional<Doctor> findByUsername(String username);
    List<Doctor> findByPlaceName(PlaceName placeName);

    Optional<Doctor> getByIdAndPlaceName(Long id,PlaceName placeName);
}
