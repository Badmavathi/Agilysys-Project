package com.customer.onboardportal.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.customer.onboardportal.model.Customer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

public interface CustomerApi {
	 @Operation(
	          summary = "Fetch all customers",
	          description = "fetches all customers entities and their data from data source")
	  @ApiResponses(value = {
	          @ApiResponse(responseCode = "200", description = "successful operation")
	  })
	  ResponseEntity<List<Customer>> getAllCusotmers(@RequestParam(required = false) String name);
	
	  
	  @Operation(
	          summary = "Fetch customer : customer id",
	          description = "fetches customer id entity and their data from data source")
	  @ApiResponses(value = {
	          @ApiResponse(responseCode = "200", description = "successful operation")
	  })
	  ResponseEntity<Customer> getCustomerById(@PathVariable("id") long id);
	  
	  
	  @Operation(
	            summary = "adds a customer",
	            description = "Adds a customer to the list of customers")
	  @ApiResponses(value = {
	            @ApiResponse(responseCode = "201", description = "successfully added a customer")
	    })
	  ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) ;
	  
	  @Operation(
	            summary = "updates a customer : customer id",
	            description = "updates a customer from the list of customers")
	  @ApiResponses(value = {
	            @ApiResponse(responseCode = "200", description = "successfully updated a customer", 
		    	          content = { @Content(mediaType = "application/json", 
			  	            schema = @Schema(implementation = Customer.class)) }),
	            @ApiResponse(responseCode = "404", description = "customer not found")
	    })
	  ResponseEntity<Customer> updateCustomer(@Valid @PathVariable("id") long id, @RequestBody Customer customer);
	  
	  @Operation(
	            summary = "deletes a customer : customer id",
	            description = "deletes a customer from the list of customers")
	  @ApiResponses(value = {
	            @ApiResponse(responseCode = "204", description = "successfully deleted a customer"),
	            @ApiResponse(responseCode = "500", description = "internal server error")
	    })
	  ResponseEntity<HttpStatus> deleteCustomer(@PathVariable("id") long id);
	  
	  @Operation(
	            summary = "deletes all customers",
	            description = "deletes all customers entities and their data from data source")
	  @ApiResponses(value = {
	            @ApiResponse(responseCode = "204", description = "successfully deleted a customer"),
	            @ApiResponse(responseCode = "500", description = "internal server error")
	    })
	  ResponseEntity<HttpStatus> deleteAllCustomers();
}
