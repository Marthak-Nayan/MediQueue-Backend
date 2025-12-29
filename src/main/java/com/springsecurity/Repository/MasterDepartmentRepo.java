package com.springsecurity.Repository;


import com.springsecurity.entities.MasterDepartment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MasterDepartmentRepo extends JpaRepository<MasterDepartment,Long> {

}
