package com.example.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class PayRecords {

	private @Id String id;
	private  String employeeId;
	private double netPay;
	private int period;

	public PayRecords() {
		this.id = UUID.randomUUID().toString();
	}

	public PayRecords(double lowerSemiNetTax, double netPay, int period) {

		this.netPay = netPay;
		this.period = period;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getNetPay() {
		return netPay;
	}

	public void setNetPay(double netPay) {
		this.netPay = netPay;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

}
