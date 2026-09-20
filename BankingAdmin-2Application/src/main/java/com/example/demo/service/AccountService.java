package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Account;
import com.example.demo.entity.FixedDeposit;

@Service
public interface AccountService {

	public Account createAccount(Account account);
	public Account updateAccount(Long id, String type);
	public Account activateAccount(Long id);
	public Account deactivateAccount(Long id);
	public Account closeAccount(Long id);
	public Account getAccount(Long id);
	public List<Account> getAllAccounts();
	public Account getByAccountNumber(String accountNumber);
	
	public List<Account> getByType(String type);
	
	public List<Account> getActiveAccounts();
	
	public List<Account> getInactiveAccounts();
	public List<Account> getAccountsByCustomer(Long customerId);

	public Account transferFunds(String fromAccount,String toAccount,Double amount);
	
	Account depositMoney(String accountNumber, Double amount);

	Account withdrawMoney(String accountNumber, Double amount);

	List<Account> findAccountsWithBalanceGreaterThan(Double balance);
	public List<Account> findByStatus(String status);

}
