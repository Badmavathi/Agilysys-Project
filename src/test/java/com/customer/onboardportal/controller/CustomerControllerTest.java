package com.customer.onboardportal.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.customer.onboardportal.OnboardportalApplication;
import com.customer.onboardportal.model.Customer;
import com.customer.onboardportal.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CustomerController.class)
@ContextConfiguration(classes = OnboardportalApplication.class)
public class CustomerControllerTest {

	@Autowired
    private MockMvc mockMvc;
	
	 @MockBean
	 private CustomerRepository customerRepository;
	    
	    @Autowired
	    private ObjectMapper objectMapper;
	    
	    
	    @Test
	    void shouldCreateCustomer() throws Exception {
	    	long id = 1L;
	      Customer customer = new Customer(id, "abc", "abc@email.com", new Date(),
	    			  "abc street", "30319", "Atlanta", "GA");

	      mockMvc.perform(post("/onboardingPortal/customers").contentType(MediaType.APPLICATION_JSON)
	          .content(objectMapper.writeValueAsString(customer)))
	          .andExpect(status().isCreated())
	          .andDo(print());
	    }
	    
	    @Test
	    void shouldReturnCustomer() throws Exception {
	      long id = 1L;
	      Customer customer = new Customer(id, "abc", "abc@email.com", new Date(),
    			  "abc street", "30319", "Atlanta", "GA");

	      when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
	      mockMvc.perform(get("/onboardingPortal/customers/{id}", id)).andExpect(status().isOk())
	          .andExpect(jsonPath("$.name").value(customer.getName()))
	          .andExpect(jsonPath("$.email").value(customer.getEmail()))
	          .andExpect(jsonPath("$.state").value(customer.getState()))
	          .andDo(print());
	    }

	    @Test
	    void shouldReturnNotFoundCustomer() throws Exception {
	      long id = 1L;

	      when(customerRepository.findById(id)).thenReturn(Optional.empty());
	      mockMvc.perform(get("/onboardingPortal/customers/{id}", id))
	           .andExpect(status().isNotFound())
	           .andDo(print());
	    }
	    
	    @Test
	    void shouldReturnListOfCustomers() throws Exception {
	      List<Customer> customers = new ArrayList<>(
	          Arrays.asList(new Customer(1, "Ram", "ram@email.com", new Date(),
	    			  		"Glen street", "30319", "Atlanta", "GA"),
	        		  new Customer(2, "Martin", "martin@email.com", new Date(),
	            			"fifth street", "30319", "Atlanta", "GA"),
	            	  new Customer(3, "William", "william@email.com", new Date(),
	            	    	"Peachtree street", "30319", "Atlanta", "GA")));

	      when(customerRepository.findAll()).thenReturn(customers);
	      mockMvc.perform(get("/onboardingPortal/customers"))
	          .andExpect(status().isOk())
	          .andExpect(jsonPath("$.size()").value(customers.size()))
	          .andDo(print());
	    }
	    
	    @Test
	    void shouldUpdateCustomer() throws Exception {
	      long id = 1L;

	      Customer customer = new Customer(id, "abc", "abc@email.com", new Date(),
    			  "abc street", "30319", "Atlanta", "GA");

	      Customer updatedCustomer = new Customer(id, "updated", "updated@email.com", new Date(),
    			  "abc street", "30319", "Atlanta", "GA");

	      when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
	      when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);

	      mockMvc.perform(put("/onboardingPortal/customers/{id}", id).contentType(MediaType.APPLICATION_JSON)
	          .content(objectMapper.writeValueAsString(updatedCustomer)))
	          .andExpect(status().isOk())
	          .andExpect(jsonPath("$.name").value(updatedCustomer.getName()))
	          .andExpect(jsonPath("$.email").value(updatedCustomer.getEmail()))
	          .andDo(print());
	    }
	    
	    @Test
	    void shouldReturnNotFoundUpdateCustomer() throws Exception {
	      long id = 1L;

	      Customer updatedCustomer = new Customer(id, "updated", "updated@email.com", new Date(),
    			  "abc street", "30319", "Atlanta", "GA");


	      when(customerRepository.findById(id)).thenReturn(Optional.empty());
	      when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);

	      mockMvc.perform(put("/onboardingPortal/customers/{id}", id).contentType(MediaType.APPLICATION_JSON)
	          .content(objectMapper.writeValueAsString(updatedCustomer)))
	          .andExpect(status().isNotFound())
	          .andDo(print());
	    }
	    
	    @Test
	    void shouldDeleteCustomer() throws Exception {
	      long id = 1L;

	      doNothing().when(customerRepository).deleteById(id);
	      mockMvc.perform(delete("/onboardingPortal/customers/{id}", id))
	           .andExpect(status().isNoContent())
	           .andDo(print());
	    }
	    
	    @Test
	    void shouldDeleteAllCustomers() throws Exception {
	      doNothing().when(customerRepository).deleteAll();
	      mockMvc.perform(delete("/onboardingPortal/customers"))
	           .andExpect(status().isNoContent())
	           .andDo(print());
	      assertTrue(customerRepository.findAll().isEmpty());
	    }
	    
}
