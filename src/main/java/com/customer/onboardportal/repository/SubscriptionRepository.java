package com.customer.onboardportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.customer.onboardportal.model.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

	}