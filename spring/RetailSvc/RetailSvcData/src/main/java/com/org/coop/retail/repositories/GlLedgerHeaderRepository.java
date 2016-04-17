package com.org.coop.retail.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.org.coop.retail.entities.GlLedgerHrd;

public interface GlLedgerHeaderRepository extends JpaRepository<GlLedgerHrd, Integer> {
	
	@Query(value="SELECT CONCAT(DATE_FORMAT(CURDATE(), '%d%m%Y'), '/', COUNT(*)+1) AS TRAN_ID "
			+ "FROM gl_ledger_hrd WHERE "
			+ "DATE(`create_date`) = CURDATE()", nativeQuery=true)
	public String getTransactionNumber();
}
