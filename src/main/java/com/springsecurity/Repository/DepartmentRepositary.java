package com.springsecurity.Repository;

import com.springsecurity.entities.Department;
import com.springsecurity.entities.PlaceName;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DepartmentRepositary extends JpaRepository<Department,Long> {

    boolean existsByDepartmentNameAndPlaceName(String departmentName, PlaceName placeName);

    //List<Department> findbyPlace(PlaceName placeName);

}
