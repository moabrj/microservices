package com.in28minutes.microservices.limitsservice.bean;

public class Limits {

	private int minimum;
	private int maximum;

	public Limits() {
		super();
	}

	public Limits(int minimun, int maximun) {
		super();
		this.minimum = minimun;
		this.maximum = maximun;
	}

	public int getMinimun() {
		return minimum;
	}

	public void setMinimun(int minimun) {
		this.minimum = minimun;
	}

	public int getMaximun() {
		return maximum;
	}

	public void setMaximun(int maximun) {
		this.maximum = maximun;
	}

}
