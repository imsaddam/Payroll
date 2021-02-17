package com.example.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TaxRange {

	private @Id String id;
	private double lowerSemiNetTax;
	private double upperSemiNetTax;
	private double salaryTaxAmount;

	public TaxRange() {
		this.id = UUID.randomUUID().toString();
	}

	public TaxRange(double lowerSemiNetTax, double upperSemiNetTax, double salaryTaxAmount) {

		this.lowerSemiNetTax = lowerSemiNetTax;
		this.upperSemiNetTax = upperSemiNetTax;
		this.salaryTaxAmount = salaryTaxAmount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getLowerSemiNetTax() {
		return lowerSemiNetTax;
	}

	public void setLowerSemiNetTax(double lowerSemiNetTax) {
		this.lowerSemiNetTax = lowerSemiNetTax;
	}

	public double getUpperSemiNetTax() {
		return upperSemiNetTax;
	}

	public void setUpperSemiNetTax(double upperSemiNetTax) {
		this.upperSemiNetTax = upperSemiNetTax;
	}

	public double getSalaryTaxAmount() {
		return salaryTaxAmount;
	}

	public void setSalaryTaxAmount(double salaryTaxAmount) {
		this.salaryTaxAmount = salaryTaxAmount;
	}

}
