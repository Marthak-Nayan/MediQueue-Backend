package com.springsecurity.Repository;

import com.springsecurity.entities.Department;
import com.springsecurity.entities.PlaceName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface DepartmentRepositary extends JpaRepository<Department,Long> {

    boolean existsByDepartmentNameAndPlaceName(String departmentName, PlaceName placeName);

    List<Department> findByPlaceName(PlaceName placeName);

    Optional<Department> findByIdAndPlaceName(Long aLong, PlaceName placeName);

    boolean existsByDepartmentNameAndPlaceNameAndIdNot(String departmentName, PlaceName placeName, Long id);

}
