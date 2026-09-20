package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.client.CustomerFeignClient;
import com.example.demo.client.TransactionFeignClient;
import com.example.demo.dto.CustomerDTO;
import com.example.demo.dto.TransactionDTO;
import com.example.demo.entity.Account;
import com.example.demo.entity.FixedDeposit;
import com.example.demo.exception.AccountNotFoundException;
import com.example.demo.exception.CustomerNotActiveException;
import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.exception.InsufficientBalanceException;
import com.example.demo.exception.InvalidAmountException;
import com.example.demo.exception.NoDataFoundException;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.FixedDepositRepository;

import jakarta.transaction.Transactional;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
    private AccountRepository repository;

	@Autowired
    private FixedDepositRepository fixedrepository;
    
	@Autowired 
	TransactionFeignClient transactionFeignClient;
	
	@Autowired
	CustomerFeignClient customerFeignClient;
	
    @Override
    @Transactional
    public Account createAccount(Account account) {

    	System.out.println(" --- Inside service Impl -- "+account);
        
        CustomerDTO customer = customerFeignClient.getCustomerById(
        		account.getCustomerId());
        
        //Validation
        if(customer == null) {
        			throw new CustomerNotFoundException("Customer not found");
        		}
        
        //Validation
        if(!customer.getStatus().equalsIgnoreCase("ACTIVE")) {
        	throw new CustomerNotActiveException("Customer is not Active.");
        }
        
        account.setStatus("ACTIVE");

        Account savedAccount =  repository.save(account);
    	System.out.println(" --- Inside service Impl saved acc -- "+savedAccount);
        return savedAccount;
    
    }
    
    @Override
    @Transactional
    public Account updateAccount(Long id, String type) {

    	//Validation
        Account dbAccount = repository.findById(id)
                .orElseThrow(() ->
                        new AccountNotFoundException("Account Not Found"));

        dbAccount.setAccountType(type);

        return repository.save(dbAccount);
    }

    @Override
    @Transactional
    public Account activateAccount(Long id) {

    	//Validation
        Account account = repository.findById(id)
                .orElseThrow(() ->
                        new AccountNotFoundException("Account Not Found"));

        account.setStatus("ACTIVE");

        return repository.save(account);
    }

    @Override
    @Transactional
    public Account deactivateAccount(Long id) {

    	//Validation
        Account account = repository.findById(id)
                .orElseThrow(() ->
                        new AccountNotFoundException("Account Not Found"));

        account.setStatus("INACTIVE");

        return repository.save(account);
    }

    @Override
    @Transactional
    public Account closeAccount(Long id) {

    	//Validation
        Account account = repository.findById(id)
                .orElseThrow(() ->
                        new AccountNotFoundException("Account Not Found"));

        account.setStatus("CLOSED");

        return repository.save(account);
    }

    @Override
    public Account getAccount(Long id) {

    	//Validation
        return repository.findById(id)
                .orElseThrow(() ->
                        new AccountNotFoundException("Account Not Found"));
    }

    @Override
    public List<Account> getAllAccounts() {
        return repository.findAll();
    }

    @Override
    public List<Account> findAccountsWithBalanceGreaterThan(
            Double balance) {
        List<Account> result = repository.findByBalanceGreaterThan(balance);
        
        //Validation
        if(result == null) {
        	throw new NoDataFoundException("No Accounts exceding the balance : " + balance);
        }
        return result;
    }
    
    @Override
    @Transactional
    public Account depositMoney(String accountNumber, Double amount) {

    	//Validation
        Account account = repository.findByAccountNumber(accountNumber)
                .orElseThrow(() ->
                        new AccountNotFoundException("Account Not Found"));

        //Validation
        if(amount == null || amount <= 0) {
        	throw new InvalidAmountException("Invalid Amount !! Enter a valid amount.");
        }
        
        account.setBalance(account.getBalance() + amount);

        return repository.save(account);
    }
    
    @Override
    @Transactional
    public Account withdrawMoney(String accountNumber, Double amount) {

        Account account = repository.findByAccountNumber(accountNumber)
                .orElseThrow(() ->
                        new AccountNotFoundException("Account Not Found"));

        //Validation
        if(amount > account.getBalance()) {
            throw new InsufficientBalanceException(
                    "Insufficient Balance");
        }
        //Validation
        if(amount == null || amount <= 0) {
        	throw new InvalidAmountException("Invalid Amount !! Enter a valid amount.");
        }

        account.setBalance(account.getBalance() - amount);

        return repository.save(account);
    }
    
    @Override
    @Transactional
    public Account getByAccountNumber(String accountNumber) {

    	//Validation
        return repository.findByAccountNumber(accountNumber)
                .orElseThrow(() ->
                        new AccountNotFoundException("Account Not Found"));
    }

    @Override
    public List<Account> getByType(String type) {
        return repository.findByAccountType(type);
    }

    @Override
    public List<Account> getActiveAccounts() {
        return repository.findByStatus("ACTIVE");
    }

    @Override
    public List<Account> getInactiveAccounts() {
        return repository.findByStatus("INACTIVE");
    }

    @Override
    public List<Account> getAccountsByCustomer(Long customerId) {
        return repository.findByCustomerId(customerId);
    }
    
    @Override
    public Account transferFunds(String fromAccount,String toAccount,Double amount) {

    	
    	//Validation
        Account sender =
                repository.findByAccountNumber(fromAccount)
                .orElseThrow(() ->
                  new AccountNotFoundException("Sender not found"));

        //Validation
        Account receiver =
                repository.findByAccountNumber(toAccount)
                .orElseThrow(() ->
                  new AccountNotFoundException("Receiver not found"));

        //Validation
        if(sender.getBalance() < amount) {
            throw new InsufficientBalanceException(
                    "Insufficient balance");
        }

        //Validation
        if(amount == null || amount <= 0) {
        	throw new InvalidAmountException("Invalid Amount !! Enter a valid amount.");
        }
        
        sender.setBalance(
                sender.getBalance() - amount);

        receiver.setBalance(
                receiver.getBalance() + amount);

        System.out.println("Transfer successful");

        repository.save(sender);
        repository.save(receiver);

        System.out.println("Creating DTO");

        TransactionDTO dto = new TransactionDTO();

        dto.setFromAccountNumber(sender.getAccountNumber());
        dto.setToAccountNumber(receiver.getAccountNumber());
        dto.setAmount(amount);
        
        System.out.println("Calling BankingUser");

        transactionFeignClient.saveTransferTransaction(dto);

        
        System.out.println("Feign call completed");

        return sender;
    }

	@Override
	public List<Account> findByStatus(String status) {
		return repository.findByStatus(status);
	}
	
}
