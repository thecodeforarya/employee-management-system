package com.example.Employee.Management.System.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Employee.Management.System.model.Employee;
import com.example.Employee.Management.System.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    public EmployeeService getEmployeeService() {
        return employeeService;
    }

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/create_employee")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee createdEmployee = employeeService.createEmployee(employee);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,
            @Validated @RequestBody Employee employeeDetails) {
        Optional<Employee> optionalEmployee = employeeService.updateEmployee(employeeId, employeeDetails);
        if (optionalEmployee.isPresent()) {
            return ResponseEntity.ok(optionalEmployee.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/fetch_employees")
    public List<Employee> getAllEmployees() {
        return employeeService.fetchAllEmployees();
    }

    @PutMapping("/{employeeId}/move-department/{newDepartmentId}")
    public ResponseEntity<Object> moveEmployeeToDepartment(@PathVariable Long employeeId,
            @PathVariable Long newDepartmentId) {
        try {
            employeeService.moveEmployeeToDepartment(employeeId, newDepartmentId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to move employee: " + e.getMessage());
        }
    }

    @GetMapping("list_name_id")
    public ResponseEntity<List<Object[]>> listEmployeeNamesAndIds(
            @RequestParam(name = "lookup", required = false) boolean lookup) {
        if (lookup) {
            List<Object[]> employeeNamesAndIds = employeeService.getEmployeeNamesAndIds();
            return ResponseEntity.ok(employeeNamesAndIds);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

}
