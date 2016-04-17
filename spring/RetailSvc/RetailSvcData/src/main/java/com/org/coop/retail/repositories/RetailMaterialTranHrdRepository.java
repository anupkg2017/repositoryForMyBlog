package com.org.coop.retail.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.org.coop.retail.entities.MaterialTranHrd;

public interface RetailMaterialTranHrdRepository extends JpaRepository<MaterialTranHrd, Integer> {
	
}
