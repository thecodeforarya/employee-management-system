package com.example.Employee.Management.System.model;

import java.util.List;

public class DepartmentWithEmployees {
    private Long id;
    private String name;
    private List<Employee> employees;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Employee> getEmployees() {
        return employees;
    }
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
    
}
