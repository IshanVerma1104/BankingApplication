package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	
	Optional<Account> findByAccountNumber(String accountNumber);
	List<Account> findByAccountType(String accountType);
	List<Account> findByStatus(String status);
	List<Account> findByCustomerId(Long customerId);
	List<Account> findByBalanceGreaterThan(Double balance);
}
