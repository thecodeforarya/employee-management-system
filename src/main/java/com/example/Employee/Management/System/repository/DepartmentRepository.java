package com.example.Employee.Management.System.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Employee.Management.System.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long>{
    
}
