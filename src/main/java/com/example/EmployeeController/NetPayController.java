package com.example.EmployeeController;

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
import com.example.repository.SocialContributionRepository;
import com.example.repository.TaxRangeRepository;
import com.example.service.NetPayService;

@RestController
public class NetPayController {

	
	@Autowired
	private final NetPayService netPayService;

	

	public NetPayController(NetPayService netPayService) {
		
		this.netPayService = netPayService;
	}

	@PostMapping("/api/payroll/netpays")
	ResponseEntity<List<PayRecords>> all(@RequestBody ArrayList<String> ids, @RequestParam int start, @RequestParam int end) {
		List<PayRecords> payRecordList = new ArrayList<>();
		try {
			
			payRecordList = netPayService.generateNetPay(ids, start, end);
			
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return ResponseEntity.ok(payRecordList);

	}
	
	// list of employee

	

}
