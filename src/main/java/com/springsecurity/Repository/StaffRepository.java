package com.springsecurity.Repository;

import com.springsecurity.entities.PlaceName;
import com.springsecurity.entities.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StaffRepository extends JpaRepository<Staff,Long> {

    List<Staff> findByPlaceName(PlaceName placeName);

}
