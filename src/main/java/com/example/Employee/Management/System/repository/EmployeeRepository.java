package com.example.Employee.Management.System.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Employee.Management.System.model.Department;
import com.example.Employee.Management.System.model.Employee;

@Repository
public interface EmployeeRepository  extends JpaRepository<Employee,Long>{
    boolean existsByDepartment(Department department);
    

    
}
