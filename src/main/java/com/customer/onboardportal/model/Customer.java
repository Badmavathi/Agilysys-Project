package com.customer.onboardportal.model;


import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "customers")
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(name = "name")
  @Size(min = 2, message = "Name should have at least 2 characters")
  private String name;

  @Column(name = "email")
  @NotBlank(message = "Email cannot be blank")
  @Email(message = "Please enter a valid email Id")
  private String email;

  @Column(name = "onboardingDate")
  private Date onboardingDate;
  
  @OneToMany(cascade = CascadeType.ALL)
  @Column(name = "subscriptions")
  private List<Subscription> subscriptions;

  //Address : street, postal code, city, state
  @Column(name = "streetName")
  private String streetName; 
 
  @Column(name = "postalCode")
  private String postalCode;
  
  @Column(name = "city")
  private String city;
  
  @Column(name = "state")
  private String state;
  

  public Customer() {

  }

  public Customer(String name, String email, Date onboardingDate, String street,
		  String postal, String city, String state) {
    this.name = name;
    this.email = email;
    this.onboardingDate = onboardingDate;
    this.streetName = street;
    this.postalCode = postal;
    this.city = city;
    this.state = state;
  }

  public Customer(long id,String name, String email, Date onboardingDate, String street,
		  String postal, String city, String state) {
	this(name, email, onboardingDate, street, postal, city, state);
	this.id = id;
}

public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Date getOnboardingDate() {
    return onboardingDate;
  }

  public void setOnboardingDate(Date onboardingDate) {
    this.onboardingDate = onboardingDate;
  }

  public String getStreetName() {
	return streetName;
  }

  public void setStreetName(String streetName) {
	this.streetName = streetName;
  }

  public String getPostalCode() {
	return postalCode;
  }

  public void setPostalCode(String postalCode) {
	this.postalCode = postalCode;
  }

  public String getCity() {
	return city;
  }

  public void setCity(String city) {
	this.city = city;
  }

  public String getState() {
	return state;
  }

  public void setState(String state) {
	this.state = state;
  }
  

public List<Subscription> getSubscriptions() {
	return subscriptions;
}

public void setSubscriptions(List<Subscription> subscriptions) {
	this.subscriptions = subscriptions;
}


@Override
  public String toString() {
	  return "Customer [id=" + id + ", name=" + name + ", email=" + email + ", onboardingDate =" + onboardingDate +  
		", street=" +streetName +", city= "+ city + ", postal code=" + postalCode + ", state =" + state +""
				+", subscriptions =" + subscriptions + "]";
  }

}

