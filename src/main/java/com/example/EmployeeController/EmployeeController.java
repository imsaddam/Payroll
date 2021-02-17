package com.example.EmployeeController;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.example.service.EmployeeService;

@RestController
class EmployeeController {

	@Autowired
	private final EmployeeService employeeService;

	EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@GetMapping("/api/payroll/employees")
	List<Employee> all() {

		return employeeService.getAll();
	}

	@GetMapping("/api/payroll/employeesByRange")
	ResponseEntity<List<Employee>> employeesByRange(@RequestParam int start, @RequestParam int end) {

		
		List<Employee> filteredEmployees = new ArrayList<>();
		try {
			
		 filteredEmployees = employeeService.employeesByRange(start, end);
			
		}catch(Exception ex) {
			
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return ResponseEntity.ok(filteredEmployees);
	}
	// end::get-aggregate-root[]

	@PostMapping("/api/payroll/employees")
	Employee newEmployee(@RequestBody Employee newEmployee) {
		return employeeService.createEmployee(newEmployee);
	}

	// Single item

	@GetMapping("/api/payroll/employees/{id}")
	Employee one(@PathVariable String id) throws Throwable {

		return employeeService.getByID(id).orElseThrow(() -> new Exception());
	}

	@PutMapping("/api/payroll/employees/{id}")
	Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable String id) {

		return employeeService.updateEmployee(newEmployee, id);
	}

	@DeleteMapping("/api/payroll/employees/{id}")
	void deleteEmployee(@PathVariable String id) {
		employeeService.deleteById(id);
	}
}