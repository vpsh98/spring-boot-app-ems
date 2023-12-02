package com.crud.spring_crudApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.crud.spring_crudApp.exception.ResourceNotFoundException;
import com.crud.spring_crudApp.model.Employee;
import com.crud.spring_crudApp.repository.EmployeeRepository;

@RequestMapping("api/v1/employees")
@RestController
public class EmployeeController {

	@Autowired
	private EmployeeRepository empRepo;
	
	@GetMapping("/getAll")
	public List<Employee> getAllEmployees() {
		return empRepo.findAll();
	}
	
	@PostMapping("/")
	public Employee createEmployee(@RequestBody Employee emp) {
		return empRepo.save(emp);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable long id) {
		Employee employee = empRepo.findById(id).orElseThrow(()->
		new ResourceNotFoundException("Emp does not exist with id : "+id));
		
		return ResponseEntity.ok(employee);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable long id, @RequestBody Employee emp) {
		Employee e = empRepo.findById(id).orElseThrow(() -> 
			new ResourceNotFoundException("Emp not found with id : "+id));
		
		e.setLastName(emp.getLastName());
		e.setEmailid(emp.getEmailid());
		e.setFirstName(emp.getFirstName());
		
		empRepo.save(e);
		return ResponseEntity.ok(e);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable long id) {
		
		Employee e = empRepo.findById(id).orElseThrow(() -> 
		new ResourceNotFoundException("Emp not found with id : "+id));
		empRepo.delete(e);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
