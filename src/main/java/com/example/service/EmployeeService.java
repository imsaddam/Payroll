package com.example.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Employee;
import com.example.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private final EmployeeRepository repository;

	EmployeeService(EmployeeRepository repository) {
		this.repository = repository;
	}
	
	public Employee createEmployee(Employee employee) {
		employee.setId(UUID.randomUUID().toString());
		return repository.save(employee);
	}
	
	
	// Retrieve operation
	
	public List<Employee> getAll(){
		return repository.findAll();
	}
	
	public List<Employee> employeesByRange( int start,  int end) throws Exception {

		if (start == 0 || end == 0 || end < start) {
			throw new Exception("Invalid Data");
		}
		Iterable<Employee> employees = repository.findAll();
		List<Employee> filteredEmployees = ((Collection<Employee>) employees).stream()
				.filter(x -> x.getJoiningDate() >= start && (x.getLeavingDate() == 0 || x.getLeavingDate() >= end))
				.collect(Collectors.toList());

		return filteredEmployees;
	}

	// Find by ID
	
	public Optional<Employee> getByID(String id) {
		return repository.findById(id);
	}

	
	public Employee updateEmployee(Employee newEmployee, String id) {
		
		return repository.findById(id).map(employee -> {
			employee.setName(newEmployee.getName());
			employee.setRole(newEmployee.getRole());
			employee.setGrossSalary(newEmployee.getGrossSalary());
			employee.setLeavingDate(newEmployee.getLeavingDate());
			return repository.save(newEmployee);
		}).orElseGet(() -> {
			newEmployee.setId(id);
			return repository.save(newEmployee);
		});
		
	}
	
	public void  deleteById(String id) {
		
		 repository.deleteById(id);
		
	}


}