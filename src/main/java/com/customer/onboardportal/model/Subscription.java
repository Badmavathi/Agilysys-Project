package com.customer.onboardportal.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "subscriptions")
public class Subscription {

	   @Id
	   @GeneratedValue(strategy = GenerationType.AUTO)
	   private long id;

	   @Column(name = "serviceName")
	   private ServiceType serviceName;

	   @Column(name = "plan")
	   private SubscriptionPlan plan;

	   @Column(name = "paused")
	   private boolean paused;
	   
	   @Column(name = "subscriptionDate")
	   private Date subscriptionDate;
	  
	   @Column(name = "pauseDate")
	   private Date pauseDate;
	   
	   public Subscription() {

	   }

	   public Subscription(ServiceType serviceName, SubscriptionPlan subscriptionPlan) {
	     this.serviceName = serviceName;
	     this.plan = subscriptionPlan;
	     this.paused = false;
	     this.subscriptionDate = new Date();
	   }
	   
	   public Subscription(ServiceType serviceName, SubscriptionPlan subscriptionPlan, boolean paused, Date pausedDate, Date subscriptionDate) {
		     this.serviceName = serviceName;
		     this.plan = subscriptionPlan;
		     this.paused = paused;
		     this.pauseDate = pausedDate;
		     this.subscriptionDate = subscriptionDate;
		   }

	   public long getId() {
		    return id;
	   }
	   
	   public ServiceType getServiceName() {
			return serviceName;
		}

		public void setServiceName(ServiceType serviceName) {
			this.serviceName = serviceName;
		}

		public SubscriptionPlan getPlan() {
			return plan;
		}

		public void setPlan(SubscriptionPlan plan) {
			this.plan = plan;
		}

		public boolean isPaused() {
			return paused;
		}

		public void setPaused(boolean paused) {
			this.paused = paused;
		}
		
		 
		public Date getSubscriptionDate() {
			return subscriptionDate;
		}

		public void setSubscriptionDate(Date subscriptionDate) {
			this.subscriptionDate = subscriptionDate;
		}

		public Date getPauseDate() {
			return pauseDate;
		}

		public void setPauseDate(Date pauseDate) {
			this.pauseDate = pauseDate;
		}
		
		@Override
		  public String toString() {
			  return "Subscription [id=" + id + ", serviceName=" + serviceName + ", plan=" + plan + ", pauseDate =" + pauseDate +  
				", subscriptionDate=" +subscriptionDate +", paused= "+ paused + "]";
		  }

}
