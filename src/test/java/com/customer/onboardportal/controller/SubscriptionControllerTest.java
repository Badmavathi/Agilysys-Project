package com.customer.onboardportal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
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
import org.springframework.test.web.servlet.MvcResult;

import com.customer.onboardportal.OnboardportalApplication;
import com.customer.onboardportal.model.Customer;
import com.customer.onboardportal.model.ServiceType;
import com.customer.onboardportal.model.Subscription;
import com.customer.onboardportal.model.SubscriptionPlan;
import com.customer.onboardportal.repository.CustomerRepository;
import com.customer.onboardportal.repository.SubscriptionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(SubscriptionController.class)
@ContextConfiguration(classes = OnboardportalApplication.class)
public class SubscriptionControllerTest {

	@Autowired
    private MockMvc mockMvc;
	
	 @MockBean
	 private CustomerRepository customerRepository;
	 @MockBean
	 private SubscriptionRepository subscriptionRepository;
	    
	    @Autowired
	    private ObjectMapper objectMapper;
	    
	    @Test
	    void shouldCustomerSubscribe() throws Exception {
	    	long id = 1L;
	      Customer customer = new Customer(id, "abc", "abc@email.com", new Date(),
	    			  "abc street", "30319", "Atlanta", "GA");
			  
	      when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
	      
	      Subscription subscription = new Subscription(ServiceType.STORAGE, SubscriptionPlan.BASIC); 
	      List<Subscription> subscriptionList = new ArrayList<Subscription>();
	      subscriptionList.add(subscription);
		  customer.setSubscriptions(subscriptionList);
		  
		  when(subscriptionRepository.save(subscription)).thenReturn(subscription);
		  when(customerRepository.save(customer)).thenReturn(customer);
				
	      SubscriptionRequestBody req = new SubscriptionRequestBody(id, "BASIC", "STORAGE");

	      MvcResult result = mockMvc.perform(put("/customerSubscription/subscribe").contentType(MediaType.APPLICATION_JSON)
	          .content(objectMapper.writeValueAsString(req)))
	          .andExpect(status().isOk())
	          .andExpect(jsonPath("$.id").value(req.getCustomerId()))
	          .andDo(print()).andReturn();
	      String json = result.getResponse().getContentAsString();
	      Customer returnedCustomer = objectMapper.readValue(json, Customer.class);

	      assertFalse(returnedCustomer.getSubscriptions().isEmpty());
	      assertEquals(returnedCustomer.getSubscriptions().size(), customer.getSubscriptions().size());
	      assertEquals(returnedCustomer.getSubscriptions().get(0).getServiceName(), customer.getSubscriptions().get(0).getServiceName());
	      assertEquals(returnedCustomer.getSubscriptions().get(0).getPlan(), customer.getSubscriptions().get(0).getPlan());
	      assertFalse(returnedCustomer.getSubscriptions().get(0).isPaused());
	      assertNull(returnedCustomer.getSubscriptions().get(0).getPauseDate());
	    }

	    
	    @Test
	    void shouldCustomerSubscribeWithNewService() throws Exception {
	    	long id = 1L;
	      Customer customer = new Customer(id, "abc", "abc@email.com", new Date(),
	    			  "abc street", "30319", "Atlanta", "GA");
			  
	      Subscription subscription = new Subscription(ServiceType.STORAGE, SubscriptionPlan.PREMIUM); 
	      List<Subscription> initialSubscriptionList = new ArrayList<Subscription>();
	      initialSubscriptionList.add(subscription);
		  customer.setSubscriptions(initialSubscriptionList);
		  
	      when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
	      
	      Customer updatedCustomer = new Customer(id, "abc", "abc@email.com", new Date(),
    			  "abc street", "30319", "Atlanta", "GA");
	      Subscription subscription1 = new Subscription(ServiceType.STORAGE, SubscriptionPlan.PREMIUM);
	      Subscription subscription2 = new Subscription(ServiceType.NETWORK, SubscriptionPlan.PREMIUM); 
	      List<Subscription> updatedSubscriptionList = new ArrayList<Subscription>();
	      updatedSubscriptionList.add(subscription1);
	      updatedSubscriptionList.add(subscription2);
	      updatedCustomer.setSubscriptions(updatedSubscriptionList);
	      
		  when(subscriptionRepository.saveAll(updatedSubscriptionList)).thenReturn(updatedSubscriptionList);
		  when(customerRepository.save(customer)).thenReturn(updatedCustomer);
				
	      SubscriptionRequestBody req = new SubscriptionRequestBody(id, "PREMIUM", "NETWORK");

	      MvcResult result = mockMvc.perform(put("/customerSubscription/subscribe").contentType(MediaType.APPLICATION_JSON)
	          .content(objectMapper.writeValueAsString(req)))
	          .andExpect(status().isOk())
	          .andExpect(jsonPath("$.id").value(req.getCustomerId()))
	          .andDo(print()).andReturn();
	      String json = result.getResponse().getContentAsString();
	      Customer returnedCustomer = objectMapper.readValue(json, Customer.class);
	      assertFalse(returnedCustomer.getSubscriptions().isEmpty());
	      assertEquals(returnedCustomer.getSubscriptions().size(), updatedCustomer.getSubscriptions().size());
	      assertEquals(returnedCustomer.getSubscriptions().size(),2);
	      
	    }
	    
	    
	    @Test
	    void shouldCustomerUpgradeWithNewPlan() throws Exception {
	    	long id = 1L;
	      Customer customer = new Customer(id, "abc", "abc@email.com", new Date(),
	    			  "abc street", "30319", "Atlanta", "GA");
			  
	      Subscription subscription = new Subscription(ServiceType.STORAGE, SubscriptionPlan.PREMIUM); 
	      List<Subscription> initialSubscriptionList = new ArrayList<Subscription>();
	      initialSubscriptionList.add(subscription);
		  customer.setSubscriptions(initialSubscriptionList);
		  
	      when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
	      
	      Customer updatedCustomer = new Customer(id, "abc", "abc@email.com", new Date(),
    			  "abc street", "30319", "Atlanta", "GA");
	      Subscription subscription1 = new Subscription(ServiceType.STORAGE, SubscriptionPlan.BASIC);
	      List<Subscription> updatedSubscriptionList = new ArrayList<Subscription>();
	      updatedSubscriptionList.add(subscription1);
	      updatedCustomer.setSubscriptions(updatedSubscriptionList);
	      
		  when(subscriptionRepository.saveAll(updatedSubscriptionList)).thenReturn(updatedSubscriptionList);
		  when(customerRepository.save(customer)).thenReturn(updatedCustomer);
				
	      SubscriptionRequestBody req = new SubscriptionRequestBody(id, "BASIC", "STORAGE");

	      MvcResult result = mockMvc.perform(put("/customerSubscription/subscribe").contentType(MediaType.APPLICATION_JSON)
	          .content(objectMapper.writeValueAsString(req)))
	          .andExpect(status().isOk())
	          .andExpect(jsonPath("$.id").value(req.getCustomerId()))
	          .andDo(print()).andReturn();
	      String json = result.getResponse().getContentAsString();
	      Customer returnedCustomer = objectMapper.readValue(json, Customer.class);
	      assertFalse(returnedCustomer.getSubscriptions().isEmpty());
	      assertEquals(returnedCustomer.getSubscriptions().size(), updatedCustomer.getSubscriptions().size());
	      assertEquals(returnedCustomer.getSubscriptions().size(),1);
	      assertEquals(returnedCustomer.getSubscriptions().get(0).getPlan(),SubscriptionPlan.BASIC);
	    }
	    
	    @Test
	    void shouldSubscribeWithoutCustomer() throws Exception {
	    	
	      SubscriptionRequestBody req = new SubscriptionRequestBody(2, "PREMIUM", "NETWORK");
	      mockMvc.perform(put("/customerSubscription/subscribe").contentType(MediaType.APPLICATION_JSON)
	          .content(objectMapper.writeValueAsString(req)))
	          .andExpect(status().isNotFound())
	          .andDo(print()).andReturn();
	    }
	    
	    @Test
	    void shouldPauseSubscription() throws Exception {
	    	long id = 1L;
	      Customer customer = new Customer(id, "abc", "abc@email.com", new Date(),
	    			  "abc street", "30319", "Atlanta", "GA");
			  
	      Subscription subscription = new Subscription(ServiceType.STORAGE, SubscriptionPlan.PREMIUM); 
	      List<Subscription> initialSubscriptionList = new ArrayList<Subscription>();
	      initialSubscriptionList.add(subscription);
		  customer.setSubscriptions(initialSubscriptionList);
		  
	      when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
	      
	      Customer updatedCustomer = new Customer(id, "abc", "abc@email.com", new Date(),
    			  "abc street", "30319", "Atlanta", "GA");
	      Subscription pauseSubscription = new Subscription(ServiceType.STORAGE, SubscriptionPlan.PREMIUM, true, new Date(), new Date()); 
	      List<Subscription> pausedSubscriptionList = new ArrayList<Subscription>();
	      pausedSubscriptionList.add(pauseSubscription);
	      updatedCustomer.setSubscriptions(pausedSubscriptionList);
	      
		  when(subscriptionRepository.save(pauseSubscription)).thenReturn(pauseSubscription);
		  when(customerRepository.save(customer)).thenReturn(updatedCustomer);
				
	      SubscriptionRequestBody req = new SubscriptionRequestBody(id, "PREMIUM", "STORAGE");

	      MvcResult result = mockMvc.perform(put("/customerSubscription/pauseSubscription").contentType(MediaType.APPLICATION_JSON)
	          .content(objectMapper.writeValueAsString(req)))
	          .andExpect(status().isOk())
	          .andExpect(jsonPath("$.id").value(req.getCustomerId()))
	          .andDo(print()).andReturn();
	      String json = result.getResponse().getContentAsString();
	      Customer returnedCustomer = objectMapper.readValue(json, Customer.class);

	      assertFalse(returnedCustomer.getSubscriptions().isEmpty());
	      assertEquals(returnedCustomer.getSubscriptions().size(), updatedCustomer.getSubscriptions().size());
	      assertEquals(returnedCustomer.getSubscriptions().size(),1);
	      assertEquals(returnedCustomer.getSubscriptions().get(0).getServiceName(), updatedCustomer.getSubscriptions().get(0).getServiceName());
	      assertEquals(returnedCustomer.getSubscriptions().get(0).getPlan(), updatedCustomer.getSubscriptions().get(0).getPlan());
	      assertTrue(returnedCustomer.getSubscriptions().get(0).isPaused());
	      assertNotNull(returnedCustomer.getSubscriptions().get(0).getPauseDate());
	      
	    }
	    
	    
	    @Test
	    void shouldPauseSubscriptionWithoutSubscribe() throws Exception {
	    	long id = 1L;
	      Customer customer = new Customer(id, "abc", "abc@email.com", new Date(),
	    			  "abc street", "30319", "Atlanta", "GA");
			  
	      when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
	      
	      SubscriptionRequestBody req = new SubscriptionRequestBody(id, "PREMIUM", "NETWORK");

	      mockMvc.perform(put("/customerSubscription/pauseSubscription").contentType(MediaType.APPLICATION_JSON)
	          .content(objectMapper.writeValueAsString(req)))
	          .andExpect(status().isNoContent())
	          .andDo(print()).andReturn();
	    }
	    
	    @Test
	    void shouldPauseSubscriptionWithDifferentService() throws Exception {
	    	long id = 1L;
	      Customer customer = new Customer(id, "abc", "abc@email.com", new Date(),
	    			  "abc street", "30319", "Atlanta", "GA");
			  
	      Subscription subscription = new Subscription(ServiceType.STORAGE, SubscriptionPlan.PREMIUM); 
	      List<Subscription> initialSubscriptionList = new ArrayList<Subscription>();
	      initialSubscriptionList.add(subscription);
		  customer.setSubscriptions(initialSubscriptionList);
		  
	      when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
	      
	      
	      SubscriptionRequestBody req = new SubscriptionRequestBody(id, "PREMIUM", "NETWORK");

	      mockMvc.perform(put("/customerSubscription/pauseSubscription").contentType(MediaType.APPLICATION_JSON)
	          .content(objectMapper.writeValueAsString(req)))
	          .andExpect(status().isNoContent())
	          .andDo(print()).andReturn();
	    }
	    
	    
	    @Test
	    void shouldResumeSubscription() throws Exception {
	    	long id = 1L;
	      Customer customer = new Customer(id, "abc", "abc@email.com", new Date(),
	    			  "abc street", "30319", "Atlanta", "GA");
			  
	      Subscription pausedSubscription = new Subscription(ServiceType.STORAGE, SubscriptionPlan.STANDARD, true, new Date(), new Date()); 
	      List<Subscription> initialSubscriptionList = new ArrayList<Subscription>();
	      initialSubscriptionList.add(pausedSubscription);
		  customer.setSubscriptions(initialSubscriptionList);
		  
	      when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
	      
	      Customer updatedCustomer = new Customer(id, "abc", "abc@email.com", new Date(),
    			  "abc street", "30319", "Atlanta", "GA");
	      Subscription resumeSubscription = new Subscription(ServiceType.STORAGE, SubscriptionPlan.STANDARD, false, null, new Date()); 
	      List<Subscription> resumedSubscriptionList = new ArrayList<Subscription>();
	      resumedSubscriptionList.add(resumeSubscription);
	      updatedCustomer.setSubscriptions(resumedSubscriptionList);
	      
		  when(subscriptionRepository.save(resumeSubscription)).thenReturn(resumeSubscription);
		  when(customerRepository.save(customer)).thenReturn(updatedCustomer);
				
	      SubscriptionRequestBody req = new SubscriptionRequestBody(id, "STANDARD", "STORAGE");

	      MvcResult result = mockMvc.perform(put("/customerSubscription/resumeSubscription").contentType(MediaType.APPLICATION_JSON)
	          .content(objectMapper.writeValueAsString(req)))
	          .andExpect(status().isOk())
	          .andExpect(jsonPath("$.id").value(req.getCustomerId()))
	          .andDo(print()).andReturn();
	      String json = result.getResponse().getContentAsString();
	      Customer returnedCustomer = objectMapper.readValue(json, Customer.class);

	      assertFalse(returnedCustomer.getSubscriptions().isEmpty());
	      assertEquals(returnedCustomer.getSubscriptions().size(), updatedCustomer.getSubscriptions().size());
	      assertEquals(returnedCustomer.getSubscriptions().size(),1);
	      assertEquals(returnedCustomer.getSubscriptions().get(0).getServiceName(), updatedCustomer.getSubscriptions().get(0).getServiceName());
	      assertEquals(returnedCustomer.getSubscriptions().get(0).getPlan(), updatedCustomer.getSubscriptions().get(0).getPlan());
	      assertFalse(returnedCustomer.getSubscriptions().get(0).isPaused());
	      assertNull(returnedCustomer.getSubscriptions().get(0).getPauseDate());
	      
	    }
	    
}
