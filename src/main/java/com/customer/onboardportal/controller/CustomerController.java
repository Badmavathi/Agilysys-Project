package com.customer.onboardportal.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.customer.onboardportal.model.Customer;
import com.customer.onboardportal.repository.CustomerRepository;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Customer API", description = "the Customer Onboarding online portal Api")
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/onboardingPortal")
public class CustomerController implements CustomerApi{

  @Autowired
  CustomerRepository customerRepository;


  /**
   * Retrieve and return all the Customer
   * @return
   */
  @GetMapping(value = "/customers", produces = "application/json")
  public ResponseEntity<List<Customer>> getAllCusotmers(@RequestParam(required = false) String name) {
    try {
      List<Customer> customers = new ArrayList<Customer>();

      if (name == null)
    	  customerRepository.findAll().forEach(customers::add);
     
      if (customers.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(customers, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Retrieve and return the Customer based on the input
   * @input : id - Customer id
   * @return
   */
  @GetMapping(value = "/customers/{id}", produces = "application/json")
  public ResponseEntity<Customer> getCustomerById(@PathVariable("id") long id) {
    Optional<Customer> customerData = customerRepository.findById(id);

    if (customerData.isPresent()) {
      return new ResponseEntity<>(customerData.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  
  /**
   * create the customer
   * @input : Customer
   * @return
   */
  @PostMapping(value = "/customers", produces = "application/json")
  public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
	  Customer _customer = customerRepository.save(new Customer(customer.getName(),
			  customer.getEmail(), new Date(), customer.getStreetName(),
			  customer.getPostalCode(), customer.getCity(), customer.getState()));
      return new ResponseEntity<>(_customer, HttpStatus.CREATED);
  }

  /**
   * update the customer
   * @input : id - customer id
   * @input : Customer
   * @return
   */
  @PutMapping(value = "/customers/{id}", produces = "application/json")
  public ResponseEntity<Customer> updateCustomer(@Valid @PathVariable("id") long id, @RequestBody Customer customer) {
    Optional<Customer> cusotmerData = customerRepository.findById(id);

    if (cusotmerData.isPresent()) {
      Customer _customer = cusotmerData.get();
      _customer.setName(customer.getName());
      _customer.setEmail(customer.getEmail());
      _customer.setStreetName(customer.getStreetName());
      _customer.setCity(customer.getCity());
      _customer.setPostalCode(customer.getPostalCode());
      _customer.setState(customer.getState());
      return new ResponseEntity<>(customerRepository.save(_customer), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
  
  /**
   * Delete the input customer id
   * @id : customer id
   * @return
   */
  @DeleteMapping(value = "/customers/{id}", produces = "application/json")
  public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable("id") long id) {
    try {
    	customerRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Delete all customers
   * @return
   */
  @DeleteMapping(value = "/customers", produces = "application/json")
  public ResponseEntity<HttpStatus> deleteAllCustomers() {
    try {
    	customerRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }
}
