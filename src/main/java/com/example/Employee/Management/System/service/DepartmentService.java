package com.example.Employee.Management.System.service;

import java.util.List;
import java.util.Optional;

import com.example.Employee.Management.System.model.Department;

public interface DepartmentService {

    Department createDepartment(Department department);

    Optional<Department> updateDepartment(Long departmentId, Department departmentDetails);

    boolean deleteDepartment(Long id);

    List<Department> fetchAllDepartments();

    List<Department> getDepartmentsWithEmployees();

}
