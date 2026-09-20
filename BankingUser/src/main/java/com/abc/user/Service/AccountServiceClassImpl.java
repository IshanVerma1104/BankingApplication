package com.abc.user.Service;


import java.time.LocalDateTime;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.user.client.AccountFeignClient;
import com.abc.user.client.FixedDepositFeignClient;
import com.abc.user.dto.AccountDTO;
import com.abc.user.dto.FixedDepositDTO;
import com.abc.user.entity.Transaction;
import com.abc.user.exceptions.AccountNotActiveException;
import com.abc.user.exceptions.AccountNotFoundException;
import com.abc.user.exceptions.InsufficientBalanceException;
import com.abc.user.exceptions.InvalidAmountException;
import com.abc.user.repository.ITransactionRepository;

import jakarta.transaction.Transactional;

@Service
public class AccountServiceClassImpl implements IAccountService
{
	
	@Autowired
	private AccountFeignClient accountFeignClient;
	
	@Autowired
	private FixedDepositFeignClient fdFeignClient;
	
	@Autowired
	private ITransactionRepository transactionRepository;
	
	@Override
	public AccountDTO viewAccount(String accountNumber)
	        throws AccountNotFoundException {

	    AccountDTO account =
	            accountFeignClient
	                .getByAccountNumber(accountNumber);

	    //Validation
	    if(account == null) {

	        throw new AccountNotFoundException(
	                "Account not found");
	    }

	    return account;
	}
	

	@Override
	public AccountDTO depositMoney(
	        String accountNumber,
	        double amount)
	        throws InvalidAmountException,
	               AccountNotFoundException {
		
		//Validation
	    if(amount <= 0) {

	        throw new InvalidAmountException(
	                "Invalid Deposit Amount");
	    }

	    AccountDTO account =
	            accountFeignClient
	                .getByAccountNumber(accountNumber);
	    
	    //Validation
	    if(account == null) {
	    	throw new AccountNotFoundException("Account does not exist.");
	    }
	    
	    //Validation
	    if(!"ACTIVE".equalsIgnoreCase(account.getStatus())) {

	        throw new AccountNotActiveException(
	                "Account is not active");
	    }
	    
	    AccountDTO accountDTO =
	            accountFeignClient.depositMoney(
	                    accountNumber,
	                    amount);
	    
	    Transaction transaction = new Transaction();
	    transaction.setAccountNumber(account.getAccountNumber());
	    transaction.setFromAccountNumber(account.getAccountNumber());
	    transaction.setToAccountNumber(account.getAccountNumber());
	    transaction.setAccountId(account.getAccountId());
	    transaction.setAmount(amount);
	    transaction.setStatus("SUCCESS");
	    transaction.setTransactionType("DEPOSIT");
	    transaction.setTransactionDate(LocalDateTime.now());

	    transactionRepository.save(transaction);

	    return accountDTO;
	}

	@Override
	public AccountDTO withdrawMoney(
	        String accountNumber,
	        double amount)
	        throws InvalidAmountException,
	               AccountNotFoundException,
	               InsufficientBalanceException {

		//Validation
	    if(amount <= 0) {

	        throw new InvalidAmountException(
	                "Invalid Withdrawal Amount");
	    }

	    AccountDTO account =
	            accountFeignClient.getByAccountNumber(accountNumber);

	    //Validation
	    if(account == null) {

	        throw new AccountNotFoundException(
	                "Account not found");
	    }
        
	    //Validation
	    if(account.getBalance() < amount) {

	        throw new InsufficientBalanceException(
	                "Insufficient balance");
	    }
	    
	    //Validation
	    if(!"ACTIVE".equalsIgnoreCase(account.getStatus())) {

	        throw new AccountNotActiveException(
	                "Account is not active");
	    }
	    AccountDTO accountDTO =
	            accountFeignClient.withdrawMoney(
	                    accountNumber,
	                    amount);
	    
	    Transaction transaction = new Transaction();
	    transaction.setAccountNumber(account.getAccountNumber());
	    transaction.setFromAccountNumber(account.getAccountNumber());
	    transaction.setToAccountNumber(account.getAccountNumber());
	    transaction.setAccountId(account.getAccountId());
	    transaction.setAmount(amount);
	    transaction.setStatus("SUCCESS");
	    transaction.setTransactionType("WITHDRAWAL");
	    transaction.setTransactionDate(LocalDateTime.now());

	    transactionRepository.save(transaction);

	    return accountDTO;
	}

	@Override
	public double checkBalance(String accountNumber)
	        throws AccountNotFoundException {

	    AccountDTO account = viewAccount(accountNumber);

	    //Validation
	    if(account == null) {
	    	throw new AccountNotFoundException("Kindly enter a valid AccountId");
	    }
	    return account.getBalance();
	}

	@Override
	public AccountDTO findByAccountNo(String accountNo) {
		return accountFeignClient
		        .getByAccountNumber(accountNo);
	}

	@Override
	public List<AccountDTO> findAccountsByAccType(String accountType) {
		return accountFeignClient
		        .findAccountsByType(accountType);
		
	}

	@Override
	public List<AccountDTO> findAccountsWithBalanceGreaterThan(double balance) {
		return accountFeignClient
		        .findAccountsWithBalanceGreaterThan(balance);

	}

	@Override
	public List<AccountDTO> findByStatus(String status) {
		return accountFeignClient
		        .findByStatus(status);
	}

	@Override
	public FixedDepositDTO createFd(
	        FixedDepositDTO fd) {

	    return fdFeignClient.createFd(fd);
	}
	
}
