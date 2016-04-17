package com.org.coop.retail.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.coop.retail.entities.CreditRegister;

public interface CreditPaymentRepository extends JpaRepository<CreditRegister, Integer> {
	
	
}
