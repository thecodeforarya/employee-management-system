package com.example.Employee.Management.System.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Employee.Management.System.model.Department;
import com.example.Employee.Management.System.model.Employee;
import com.example.Employee.Management.System.repository.DepartmentRepository;
import com.example.Employee.Management.System.repository.EmployeeRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Optional<Employee> updateEmployee(Long employeeId, Employee employeeDetails) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            employee.setName(employeeDetails.getName());
            employee.setDateOfBirth(employeeDetails.getDateOfBirth());
            employee.setSalary(employeeDetails.getSalary());
            employee.setDepartment(employeeDetails.getDepartment());
            employee.setAddress(employeeDetails.getAddress());
            employee.setRole(employeeDetails.getAddress());
            employee.setJoiningDate(employeeDetails.getJoiningDate());
            employee.setYearlyBonusPercentage(employeeDetails.getYearlyBonusPercentage());
            employee.setReportingManager(employeeDetails.getReportingManager());
            return Optional.of(employeeRepository.save(employee));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<Employee> fetchAllEmployees() {
        List<Employee> allEmployees = employeeRepository.findAll();
        return allEmployees;
    }

    @Override
    public Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.employeeRepository.findAll(pageable);
    }

    @Transactional
    public void moveEmployeeToDepartment(Long employeeId, Long newDepartmentId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + employeeId));

        Department newDepartment = departmentRepository.findById(newDepartmentId)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with id: " + newDepartmentId));

        employee.setDepartment(newDepartment.getName());
        employeeRepository.save(employee);
    }

    @Override
    public List<Object[]> getEmployeeNamesAndIds() {
        List<Employee> employees = employeeRepository.findAll();

        return employees.stream()
                .map(employee -> new Object[] { employee.getId(), employee.getName() })
                .collect(Collectors.toList());
    }
}