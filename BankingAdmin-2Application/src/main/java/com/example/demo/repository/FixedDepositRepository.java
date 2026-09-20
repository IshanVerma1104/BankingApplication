package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.FixedDeposit;
@Repository
public interface FixedDepositRepository extends JpaRepository<FixedDeposit, Long>{
	
	Optional<FixedDeposit> findByFdNumber(String fdNumber);
	
	List<FixedDeposit> findByStatus(String status);

	List<FixedDeposit> findByAccountNumber(String accountNumber);
}
