package com.crud.spring_crudApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crud.spring_crudApp.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
