package com.example.springbootproject.repository;

import com.example.springbootproject.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Employee findByNameAndPassword(String name, String password);

    List<Employee> findByName(String name);
}

