package com.example.Employee.Management.System.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Employee.Management.System.model.Department;
import com.example.Employee.Management.System.model.Employee;
import com.example.Employee.Management.System.repository.DepartmentRepository;
import com.example.Employee.Management.System.repository.EmployeeRepository;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    
    public Department createDepartment(Department department) {
        Optional<Employee> optionalDepartmentHead = employeeRepository.findById(department.getDepartmentHead().getId());
        if (optionalDepartmentHead.isPresent()) {
            return departmentRepository.save(department);
        } else {
            Employee departmentHead = department.getDepartmentHead();
            employeeRepository.save(departmentHead);
            return departmentRepository.save(department);
        }
    }

    @Override
    public boolean deleteDepartment(Long id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (optionalDepartment.isPresent()) {
            Department department = optionalDepartment.get();
            if (!hasEmployees(department)) { // Check if department has employees
                departmentRepository.delete(department);
                return true;
            }
        }
        return false;
    }

    private boolean hasEmployees(Department department) {
        return employeeRepository.existsByDepartment(department);
    }

    @Override
    public Optional<Department> updateDepartment(Long departmentId, Department departmentDetails) {
        Optional<Department> optionalDepartment = departmentRepository.findById(departmentId);
        if (optionalDepartment.isPresent()) {
            Department department = optionalDepartment.get();
            department.setName(departmentDetails.getName());
            department.setCreationDate(departmentDetails.getCreationDate());

            return Optional.of(departmentRepository.save(department));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<Department> fetchAllDepartments() {
        List<Department> allDepartments = departmentRepository.findAll();
        return allDepartments;
    }

    public List<Department> getDepartmentsWithEmployees() {
        List<Department> departments = departmentRepository.findAll();

        return departments.stream()
                .map(this::mapToDepartment)
                .collect(Collectors.toList());
    }

    private Department mapToDepartment(Department department) {
        Department dto = new Department();
        dto.setId(department.getId());
        dto.setName(department.getName());

        List<Employee> employees = department.getEmployees().stream()
                .map(this::mapToEmployee)
                .collect(Collectors.toList());
        dto.setEmployees(employees);

        return dto;
    }

    private Employee mapToEmployee(Employee employee) {
        Employee dto = new Employee();
        dto.setId(employee.getId());
        dto.setName(employee.getName());

        return dto;
    }

}
