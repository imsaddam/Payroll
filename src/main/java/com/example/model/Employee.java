package com.example.model;

import java.util.Objects;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Employee {

	private @Id String id;
	private String name;
	private String role;
	private String companyName;
	private Double grossSalary;
	private int joiningDate;
	private int leavingDate;

	Employee() {
		this.id = UUID.randomUUID().toString();
	}

	public Employee(String name, String role, String companyName, double grossSalary, int joiningDate,
			int leavingDate) {
		this.id = UUID.randomUUID().toString();
		this.name = name;
		this.role = role;
		this.companyName = companyName;
		this.grossSalary = grossSalary;
		this.joiningDate = joiningDate;
		this.leavingDate = leavingDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Double getGrossSalary() {
		return grossSalary;
	}

	public void setGrossSalary(Double grossSalary) {
		this.grossSalary = grossSalary;
	}

	public int getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(int joiningDate) {
		this.joiningDate = joiningDate;
	}

	public int getLeavingDate() {
		return leavingDate;
	}

	public void setLeavingDate(int leavingDate) {
		this.leavingDate = leavingDate;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;
		if (!(o instanceof Employee))
			return false;
		Employee employee = (Employee) o;
		return Objects.equals(this.id, employee.id) && Objects.equals(this.name, employee.name)
				&& Objects.equals(this.role, employee.role);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.name, this.role);
	}

	@Override
	public String toString() {
		return "Employee{" + "id=" + this.id + ", name='" + this.name + '\'' + ", role='" + this.role + '\'' + '}';
	}
}