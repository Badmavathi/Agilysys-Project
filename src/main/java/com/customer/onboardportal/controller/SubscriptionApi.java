package com.customer.onboardportal.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.customer.onboardportal.model.Customer;
import com.customer.onboardportal.model.ServiceType;
import com.customer.onboardportal.model.SubscriptionPlan;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

public interface SubscriptionApi {

	
	@Operation(
	          summary = "subscribe to a service plan",
	          description = "Customer subscribers to the specific service and plan")
	  @ApiResponses(value = {
	          @ApiResponse(responseCode = "200", description = "successful operation", 
	    	          content = { @Content(mediaType = "application/json", 
	  	            schema = @Schema(implementation = Customer.class)) }),
	          @ApiResponse(responseCode = "404", description = "Invalid customer id", 
	    	          content = @Content)
	  })
	 ResponseEntity<Customer> subscribe(@Valid @RequestBody SubscriptionRequestBody request) throws Exception;
	
	@Operation(
	          summary = "pause the subscription service",
	          description = "Customer pauses the subscribed service")
	  @ApiResponses(value = {
	          @ApiResponse(responseCode = "200", description = "successful operation", 
	    	          content = { @Content(mediaType = "application/json", 
	  	            schema = @Schema(implementation = Customer.class)) }),
	          @ApiResponse(responseCode = "204", description = "Invalid service", 
	    	          content = @Content),
	          @ApiResponse(responseCode = "404", description = "Invalid customer id", 
	    	          content = @Content)
	  })
	ResponseEntity<Customer> pause(@Valid @RequestBody SubscriptionRequestBody request) throws Exception;
	
	@Operation(
	          summary = "resume the subscription service",
	          description = "Customer resume the subscribed service")
	  @ApiResponses(value = {
	          @ApiResponse(responseCode = "200", description = "successful operation", 
	    	          content = { @Content(mediaType = "application/json", 
	  	            schema = @Schema(implementation = Customer.class)) }),
	          @ApiResponse(responseCode = "204", description = "Invalid service", 
	    	          content = @Content),
	          @ApiResponse(responseCode = "404", description = "Invalid customer id", 
	    	          content = @Content)
	  })
	ResponseEntity<Customer> resume(@Valid @RequestBody SubscriptionRequestBody request) throws Exception;
	
	@Operation(
	          summary = "Fetches all the service types",
	          description = "Fetches all the service types available for the customer to subscribe")
	  @ApiResponses(value = {
	          @ApiResponse(responseCode = "200", description = "Lists all available services")
	  })
	ResponseEntity<List<ServiceType>> getAllServiceTypes();
	
	@Operation(
	          summary = "Fetches all the subscription plans",
	          description = "Fetches all the subscription plans available for the customer to subscribe")
	  @ApiResponses(value = {
	          @ApiResponse(responseCode = "200", description = "Lists all available subscription plans")
	  })
	ResponseEntity<List<SubscriptionPlan>> getAllSubscriptionPlans();
}
