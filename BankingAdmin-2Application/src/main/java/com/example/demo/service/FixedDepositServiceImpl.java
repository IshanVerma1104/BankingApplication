package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.client.CustomerFeignClient;
import com.example.demo.dto.CustomerDTO;
import com.example.demo.entity.Account;
import com.example.demo.entity.FixedDeposit;
import com.example.demo.exception.AccountNotFoundException;
import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.exception.FDAlreadyExistsException;
import com.example.demo.exception.FDNotFoundException;
import com.example.demo.exception.InputNotCorrectException;
import com.example.demo.exception.InvalidAmountException;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.FixedDepositRepository;

@Service
public class FixedDepositServiceImpl implements FixedDepositService {

    @Autowired
    private FixedDepositRepository repository;

    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private CustomerFeignClient feignClient;
    @Override
    public FixedDeposit createFd(FixedDeposit fd) {

    	String fdNumber = generateFdNumber();
    	
    	fd.setFdNumber(fdNumber);
    	
    	//Validation
    	if(repository.findByFdNumber(
    	        fd.getFdNumber()).isPresent()) {

    	    throw new FDAlreadyExistsException(
    	            "FD Number already exists");
    	}
        //Validation
    	Account account =
    	        accountRepository
    	            .findByAccountNumber(fd.getAccountNumber())
    	            .orElseThrow(
    	                () ->
    	                new AccountNotFoundException(
    	                    "Account Number "
    	                    + fd.getAccountNumber()
    	                    + " does not exist"
    	                )
    	            );
    	
    	//Validation if the amount <= 0
    	if(fd.getFdAmount() <= 0) {
    		throw new InvalidAmountException("Amount is Invalid");
    	}
    	
    	//Validation if tenure months is less than 12
    	if(fd.getTenureMonths() < 12) {
    		throw new InputNotCorrectException("The Input Tenure Months is not correct.");
    	}
    	
        //Validation
    	CustomerDTO customer = feignClient.getCustomerById(account.getCustomerId());
    	if(customer == null) {
    		throw new CustomerNotFoundException("Customer data not there. Cannot create FD.");
    	}
        return repository.save(fd);
    }

    private String generateFdNumber() {

        Long count = repository.count();

        long nextNumber = 110000 + count;

        return "FD" + nextNumber;
    }

	@Override
    public FixedDeposit getFdByNumber(String fdNumber) {

        return repository.findByFdNumber(fdNumber)
                .orElseThrow(() ->
                        new FDNotFoundException("FD not found"));
    }

    @Override
    public List<FixedDeposit> getAllFdByAccount(String accountNumber) {

        return repository.findByAccountNumber(accountNumber);
    }

    @Override
    public FixedDeposit activateFd(String fdNumber) {

        FixedDeposit fd =
                repository.findByFdNumber(fdNumber)
                .orElseThrow(() ->
                    new FDNotFoundException("FD not found"));


        return repository.save(fd);
    }

    @Override
    public FixedDeposit deactivateFd(String fdNumber) {

        FixedDeposit fd =
                repository.findByFdNumber(fdNumber)
                .orElseThrow(() ->
                    new FDNotFoundException("FD not found"));

        fd.setStatus("INACTIVE");

        return repository.save(fd);
    }
}
