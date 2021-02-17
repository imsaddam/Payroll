package com.example.EmployeeController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Employee;
import com.example.model.SocialContribution;
import com.example.model.TaxRange;
import com.example.repository.EmployeeRepository;
import com.example.repository.SocialContributionRepository;
import com.example.repository.TaxRangeRepository;

@RestController
class TaxRangeController {

	@Autowired
	private final TaxRangeRepository repository;

	TaxRangeController(TaxRangeRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/api/payroll/taxRange")
	List<TaxRange> all() {

		return repository.findAll();
	}
	// end::get-aggregate-root[]

	@PostMapping("/api/payroll/taxRange")
	TaxRange newTaxRange(@RequestBody TaxRange taxRange) {
		taxRange.setId(UUID.randomUUID().toString());
		return repository.save(taxRange);
	}

	// Single item

	@GetMapping("/api/payroll/taxRange/{id}")
	TaxRange one(@PathVariable String id) throws Throwable {

		return repository.findById(id).orElseThrow(() -> new Exception());
	}

	@PutMapping("/api/payroll/taxRange/{id}")
	TaxRange replaceTaxRange(@RequestBody TaxRange taxRange, @PathVariable String id) {

		return repository.findById(id).map(tr -> {
			tr.setLowerSemiNetTax(taxRange.getLowerSemiNetTax());
			tr.setUpperSemiNetTax(taxRange.getUpperSemiNetTax());
			tr.setSalaryTaxAmount(taxRange.getSalaryTaxAmount());
			return repository.save(tr);
		}).orElseGet(() -> {
			taxRange.setId(id);
			return repository.save(taxRange);
		});
	}

	@DeleteMapping("/api/payroll/taxRange/{id}")
	void deleteTaxRange(@PathVariable String id) {
		repository.deleteById(id);
	}
}