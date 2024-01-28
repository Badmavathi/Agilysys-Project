package com.customer.onboardportal.controller;

public class SubscriptionRequestBody {
	
	public long customerId;
	public String plan;
	public String type;
	public boolean paused;
	
	public SubscriptionRequestBody(long id, String plan, String type) {
		this.customerId = id;
		this.plan = plan;
		this.type = type;
	}
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public String getPlan() {
		return plan;
	}
	public void setPlan(String plan) {
		this.plan = plan;
	}
	public boolean isPaused() {
		return paused;
	}
	public void setPaused(boolean paused) {
		this.paused = paused;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
