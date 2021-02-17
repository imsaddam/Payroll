package com.example.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SocialContribution {

	private @Id String id;
	private double percentage;
	private int period;

	public SocialContribution() {
		this.id = UUID.randomUUID().toString();
	}

	public SocialContribution(double percentage, int period) {
		this.id = UUID.randomUUID().toString();
		this.percentage = percentage;
		this.period = period;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

}
