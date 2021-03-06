package com.example.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Employee;
import com.example.model.PayRecords;
import com.example.model.SocialContribution;
import com.example.model.TaxRange;
import com.example.repository.EmployeeRepository;
import com.example.repository.PayRecordsRepository;
import com.example.repository.SocialContributionRepository;
import com.example.repository.TaxRangeRepository;

@Service
public class NetPayService {

	@Autowired
	private final SocialContributionRepository socialRepository;

	@Autowired
	private final EmployeeRepository employeeRepository;

	@Autowired
	private final TaxRangeRepository taxRepository;
	

	@Autowired
	private final PayRecordsRepository payRecordsRepository;

	private List<TaxRange> taxRanges = new ArrayList<TaxRange>();

	public NetPayService(EmployeeRepository employeeRepository, SocialContributionRepository socialRepository,
			TaxRangeRepository taxRepository, PayRecordsRepository payRecordsRepository) {
		this.employeeRepository = employeeRepository;
		this.socialRepository = socialRepository;
		this.taxRepository = taxRepository;
		this.payRecordsRepository = payRecordsRepository;
	}

	
	public List<PayRecords> generateNetPay( ArrayList<String> ids,  int start, int end) throws Exception {

		if (ids.size() == 0 || start == 0 || end == 0 || end < start) {
			throw new Exception("Invalid Data");
		}
		Double netPay = 0.0;
		List<PayRecords> payRecordList = new ArrayList<>();
		try {
			Iterable<Employee> employees = employeeRepository.findAllById(ids);
			List<Employee> filteredEmployees = ((Collection<Employee>) employees).stream()
					.filter(x -> x.getJoiningDate() >= start && (x.getLeavingDate() == 0 || x.getLeavingDate() <= end))
					.collect(Collectors.toList());

			Collection<SocialContribution> socialContributions = socialRepository.findAll().stream()
					.filter(x -> x.getPeriod() >= start && x.getPeriod() <= end).collect(Collectors.toList());

			SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMM", Locale.ENGLISH);
			Date startDate = originalFormat.parse(String.valueOf(start + 1));
			Date endDate = originalFormat.parse(String.valueOf(end + 1));
			
			
			

			Calendar startCalender = Calendar.getInstance();
			startCalender.setTime(startDate);
			Calendar endCalender = Calendar.getInstance();
			endCalender.setTime(endDate);

			List<Integer> ds = new ArrayList<>();

			taxRanges = taxRepository.findAll();
			

			for (Date date = startCalender.getTime(); startCalender.before(endCalender)
					|| startCalender.equals(endCalender); startCalender.add(Calendar.MONTH,
							1), date = startCalender.getTime()) {
				int month = Integer.parseInt(originalFormat.format(date)) - 1;
				ds.add(Integer.parseInt(originalFormat.format(date)) - 1);
				List<Double> socialPercentages = socialContributions.stream().filter(x -> x.getPeriod() == month)
						.map(s -> s.getPercentage()).collect(Collectors.toList());
				Double socialPercentage = 0.0;

				if (socialPercentages.size() > 0) {
					socialPercentage = socialPercentages.get(0);
				}

				List<Employee> presentEmployee = filteredEmployees.stream().filter(
						x -> x.getJoiningDate() <= month && (x.getLeavingDate() == 0 || month <= x.getLeavingDate()))
						.collect(Collectors.toList());

				for (Employee employee : presentEmployee) {
					netPay = calculateNetSalary(employee, socialPercentage);
					PayRecords payRecords = new PayRecords();
					payRecords.setEmployeeId(employee.getId());
					payRecords.setPeriod(month);
					payRecords.setNetPay(netPay);
					payRecordList.add(payRecords);
					
					payRecordsRepository.save(payRecords);
				}

			}
		} catch (Exception ex) {
			throw new Exception("Invalid Data");
		}

		return payRecordList;

	}
	
	// list of employee

	private Double calculateNetSalary(Employee employee, Double socialPercentage) {
		Double tax = 0.0;
		List<Double> trs = taxRanges.stream()
				.filter(x -> x.getLowerSemiNetTax() <= employee.getGrossSalary()
						&& employee.getGrossSalary() <= x.getUpperSemiNetTax())
				.map(x -> x.getSalaryTaxAmount()).collect(Collectors.toList());

		if (trs.size() > 0) {
			tax = trs.get(0);
		}
		Double gross = employee.getGrossSalary();
		Double percentage = (gross * socialPercentage / 100);
		Double semiNet = gross - percentage;
		Double netPay = semiNet - tax;
		return netPay;
	}

}
