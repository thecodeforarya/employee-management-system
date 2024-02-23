package com.example.Employee.Management.System.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.Employee.Management.System.model.Employee;

public interface EmployeeService {
    
    Employee createEmployee(Employee employee);
    Optional<Employee> updateEmployee(Long employeeId, Employee employeeDetails);
    List<Employee> fetchAllEmployees();
    Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
    void moveEmployeeToDepartment(Long employeeId, Long newDepartmentId);
    List<Object[]> getEmployeeNamesAndIds();
    
}
