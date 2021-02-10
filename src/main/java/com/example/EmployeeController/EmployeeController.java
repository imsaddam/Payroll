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

@RestController
class EmployeeController {

  @Autowired
  private final EmployeeRepository repository;

  EmployeeController(EmployeeRepository repository) {
    this.repository = repository;
  }



  @GetMapping("/api/payroll/employees")
  List<Employee> all() {
	
   return repository.findAll();
  }
  
  @GetMapping("/api/payroll/employeesByRange")
  ResponseEntity<List<Employee>> employeesByRange(@RequestParam int start, @RequestParam int end)  {
	    
	  if(start == 0 || end == 0 || end < start)
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	  
		Iterable<Employee> employees =   repository.findAll();		
		List<Employee> filteredEmployees  = ((Collection<Employee>) employees).stream()
										.filter(x->x.getJoiningDate()>=start && (x.getLeavingDate() == 0  || x.getLeavingDate()>= end))
										.collect(Collectors.toList());
		
		return ResponseEntity.ok(filteredEmployees);
  }
  // end::get-aggregate-root[]

  @PostMapping("/api/payroll/employees")
  Employee newEmployee(@RequestBody Employee newEmployee) {
	newEmployee.setId(UUID.randomUUID().toString());
    return repository.save(newEmployee);
  }

  // Single item
  
  @GetMapping("/api/payroll/employees/{id}")
  Employee one(@PathVariable String id) throws Throwable {
    
    return repository.findById(id)
      .orElseThrow(() -> new Exception());
  }

  @PutMapping("/api/payroll/employees/{id}")
  Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable String id) {
    
    return repository.findById(id)
      .map(employee -> {
        employee.setName(newEmployee.getName());
        employee.setRole(newEmployee.getRole());
        employee.setGrossSalary(newEmployee.getGrossSalary());
        employee.setLeavingDate(newEmployee.getLeavingDate());
        return repository.save(employee);
      })
      .orElseGet(() -> {
        newEmployee.setId(id);
        return repository.save(newEmployee);
      });
  }

  @DeleteMapping("/api/payroll/employees/{id}")
  void deleteEmployee(@PathVariable String id) {
    repository.deleteById(id);
  }
}