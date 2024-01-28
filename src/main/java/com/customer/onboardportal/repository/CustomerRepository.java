package com.customer.onboardportal.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.customer.onboardportal.model.Customer;


public interface CustomerRepository extends JpaRepository<Customer, Long> {

	  List<Customer> findByNameContainingIgnoreCase(String name);
	}