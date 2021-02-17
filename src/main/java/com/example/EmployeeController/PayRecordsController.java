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
import com.example.model.PayRecords;
import com.example.model.SocialContribution;
import com.example.model.TaxRange;
import com.example.repository.EmployeeRepository;
import com.example.repository.PayRecordsRepository;
import com.example.repository.SocialContributionRepository;
import com.example.repository.TaxRangeRepository;

@RestController
class PayRecordsController {

	@Autowired
	private final PayRecordsRepository repository;

	PayRecordsController(PayRecordsRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/api/payroll/payRecords")
	List<PayRecords> all() {

		return repository.findAll();
	}
	// end::get-aggregate-root[]

	@PostMapping("/api/payroll/payRecords")
	PayRecords newPayRecords(@RequestBody PayRecords payRecords) {
		payRecords.setId(UUID.randomUUID().toString());
		return repository.save(payRecords);
	}

	// Single item

	@GetMapping("/api/payroll/payRecords/{id}")
	PayRecords one(@PathVariable String id) throws Throwable {

		return repository.findById(id).orElseThrow(() -> new Exception());
	}

	@PutMapping("/api/payroll/payRecords/{id}")
	PayRecords replacePayRecords(@RequestBody PayRecords payRecords, @PathVariable String id) {

		return repository.findById(id).map(pr -> {
			pr.setNetPay(payRecords.getNetPay());
			pr.setPeriod(payRecords.getPeriod());
			return repository.save(pr);
		}).orElseGet(() -> {
			payRecords.setId(id);
			return repository.save(payRecords);
		});
	}

	@DeleteMapping("/api/payroll/payRecords/{id}")
	void deletePayRecords(@PathVariable String id) {
		repository.deleteById(id);
	}
}