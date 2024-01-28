package com.customer.onboardportal.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customer.onboardportal.model.Customer;
import com.customer.onboardportal.model.ServiceType;
import com.customer.onboardportal.model.Subscription;
import com.customer.onboardportal.model.SubscriptionPlan;
import com.customer.onboardportal.repository.CustomerRepository;
import com.customer.onboardportal.repository.SubscriptionRepository;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Subscription API", description = "the Customer subscription Api")
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/customerSubscription")
public class SubscriptionController implements SubscriptionApi {

	 @Autowired
	  CustomerRepository customerRepository;
	  @Autowired
	  SubscriptionRepository subscriptionRepository;

	  
	  /**
	   * subscribe the customer for the selected service and plan
	   * @input : SubscriptionRequestBody
	   * @return
	   */
	  @PutMapping(value = "/subscribe", produces = "application/json")
	  public ResponseEntity<Customer> subscribe(@Valid @RequestBody SubscriptionRequestBody request) throws Exception{
		  Optional<Customer> cusotmerData = customerRepository.findById(request.getCustomerId());

		    if (cusotmerData.isPresent()) {
		      Customer _customer = cusotmerData.get();
		      ServiceType type = ServiceType.valueOf(request.getType()); //storage
		      SubscriptionPlan plan = SubscriptionPlan.valueOf(request.getPlan());
		      if(_customer.getSubscriptions()  == null || _customer.getSubscriptions().isEmpty()) {
		    	  //no subscriptions
		    	  Subscription subscription = new Subscription(type, plan);
		    	  subscription = subscriptionRepository.save(subscription);
		    	  List<Subscription> list = new ArrayList<Subscription>();
		    	  list.add(subscription);
		    	  _customer.setSubscriptions(list);
		      } else {
		    	  List<Subscription> list = _customer.getSubscriptions();
		    	  int index = -1;
		    	  for (int i = 0; i < list.size(); i++) {
		    		  if(list.get(i).getServiceName().equals(type)) {
		    			  index = i;
		    		  }
		    	  }
		    	  if(index != -1) {
		    		  //update the subscription type
			    	  Subscription subscription = list.get(index);
			    	  list.remove(index);
			    	  subscription.setPaused(false);
			    	  subscription.setPlan(plan);
			    	  subscription = subscriptionRepository.save(subscription);
			    	  _customer.getSubscriptions().add(subscription);
		    	  } else {
		    		  //add new subscription type
		    		  Subscription subscription = new Subscription(type, plan);
			    	  subscription = subscriptionRepository.save(subscription);
			    	  List<Subscription> list1 = _customer.getSubscriptions();
			    	  list1.add(subscription);
			    	  _customer.setSubscriptions(list1);
		    	  }
		      }
				
		      return new ResponseEntity<>(customerRepository.save(_customer), HttpStatus.OK);
		    } else {
		      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		    }
	  }
	  
	  /**
	   * pause the subscription for the select user
	   * @input : SubscriptionRequestBody
	   * @return
	   */
	  @PutMapping(value = "/pauseSubscription", produces = "application/json")
	  public ResponseEntity<Customer> pause(@Valid @RequestBody SubscriptionRequestBody request) throws Exception {
		  Optional<Customer> cusotmerData = customerRepository.findById(request.getCustomerId());

		    if (cusotmerData.isPresent()) {
		      Customer _customer = cusotmerData.get();
		      ServiceType type = ServiceType.valueOf(request.getType()); //storage
		      if(_customer.getSubscriptions() == null || _customer.getSubscriptions().isEmpty()) {
		    	  //no subscriptions
		    	  return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		      } else {
		    	  List<Subscription> list = _customer.getSubscriptions();
		    	  int index = -1;
		    	  for (int i = 0; i < list.size(); i++) {
		    		  if(list.get(i).getServiceName().equals(type) && !list.get(i).isPaused()) {
		    			  index = i;
		    		  }
		    	  }
		    	  if(index != -1) {
		    		  //update the subscription type
			    	  Subscription subscription = list.get(index);
			    	  list.remove(index);
			    	  subscription.setPaused(true);
			    	  subscription.setPauseDate(new Date());
			    	  subscription = subscriptionRepository.save(subscription);
			    	  _customer.getSubscriptions().add(subscription);
		    	  } else {
		    		  //no subscriptions with request type
		    		  return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		    	  }
		      }
				
		      return new ResponseEntity<>(customerRepository.save(_customer), HttpStatus.OK);
		    } else {
		      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		    }
	  }
	  
	  /**
	   * resume the paused subscription
	   * @input : SubscriptionRequestBody
	   * @return
	   */
	  @PutMapping(value = "/resumeSubscription", produces = "application/json")
	  public ResponseEntity<Customer> resume(@Valid @RequestBody SubscriptionRequestBody request) throws Exception{
		  Optional<Customer> cusotmerData = customerRepository.findById(request.getCustomerId());

		    if (cusotmerData.isPresent()) {
		      Customer _customer = cusotmerData.get();
		      ServiceType type = ServiceType.valueOf(request.getType()); //storage
		      if(_customer.getSubscriptions() == null || _customer.getSubscriptions().isEmpty()) {
		    	  //no subscriptions
		    	  return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		      } else {
		    	  List<Subscription> list = _customer.getSubscriptions();
		    	  int index = -1;
		    	  for (int i = 0; i < list.size(); i++) {
		    		  if(list.get(i).getServiceName().equals(type) && list.get(i).isPaused()) {
		    			  index = i;
		    		  }
		    	  }
		    	  if(index != -1) {
		    		  //update the subscription type
			    	  Subscription subscription = list.get(index);
			    	  list.remove(index);
			    	  subscription.setPaused(false);
			    	  subscription.setPauseDate(null);
			    	  subscription = subscriptionRepository.save(subscription);
			    	  _customer.getSubscriptions().add(subscription);
		    	  } else {
		    		  //no subscriptions with request type
		    		  return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		    	  }
		      }
				
		      return new ResponseEntity<>(customerRepository.save(_customer), HttpStatus.OK);
		    } else {
		      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		    }
	  }
	  
	  /**
	   * Retrieve and return the list of service types
	   * @return
	   */
	  @GetMapping(value = "/service-types", produces = "application/json")
	  public ResponseEntity<List<ServiceType>> getAllServiceTypes() {
	      List<ServiceType> services = Arrays.asList(ServiceType.values());
	      if (services.isEmpty()) {
		        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		      }
	      return new ResponseEntity<>(services, HttpStatus.OK);
	  }
	  
	  /**
	   * Retrieve and return the list of subscription plans
	   * @return
	   */
	  @GetMapping(value = "/subscription-plans", produces = "application/json")
	  public ResponseEntity<List<SubscriptionPlan>> getAllSubscriptionPlans() {
		  List<SubscriptionPlan> subscriptionPlans = Arrays.asList(SubscriptionPlan.values());
	      if (subscriptionPlans.isEmpty()) {
		        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		      }
	      return new ResponseEntity<>(subscriptionPlans, HttpStatus.OK);
	  }
}
