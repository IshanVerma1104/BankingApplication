package com.abc.user.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.abc.user.Service.IAccountService;
import com.abc.user.Service.ITransactionService;
import com.abc.user.client.AccountFeignClient;
import com.abc.user.dto.AccountDTO;
import com.abc.user.dto.FixedDepositDTO;
import com.abc.user.dto.TransactionDTO;
import com.abc.user.entity.Transaction;
import com.abc.user.exceptions.InsufficientBalanceException;
import com.abc.user.exceptions.InvalidAmountException;
import com.abc.user.repository.ITransactionRepository;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @Autowired
    private ITransactionService transactionService;
    
    @Autowired
	private AccountFeignClient accountFeignClient;
    
    @Autowired
    private ITransactionRepository repository;
    
    @GetMapping("/{accountNumber}")
    public AccountDTO viewAccount(@PathVariable String accountNumber)
    {
        return accountService.viewAccount(accountNumber);
    }

    @PutMapping("/deposit/{accountNumber}/{amount}")
    public AccountDTO depositMoney(@PathVariable String accountNumber,
                                @PathVariable double amount)
    {
        return accountService.depositMoney(accountNumber, amount);
    }

    @PutMapping("/withdraw/{accountNumber}/{amount}")
    public AccountDTO withdrawMoney(@PathVariable String accountNumber,
                                 @PathVariable double amount) 
    {
        return accountService.withdrawMoney(accountNumber, amount);
    }

    @GetMapping("/balance/{accountNumber}")
    public double checkBalance(@PathVariable String accountNumber)
    {
        return accountService.checkBalance(accountNumber);
    }

    @GetMapping("/accountNo/{accountNo}")
    public AccountDTO findByAccountNo(@PathVariable String accountNo)
    {
        return accountService.findByAccountNo(accountNo);
    }

    @GetMapping("/accountType/{accountType}")
    public List<AccountDTO> findAccountsByAccType(
            @PathVariable String accountType)
    {
        return accountService.findAccountsByAccType(accountType);
    }

    @GetMapping("/balanceGreater/{balance}")
    public List<AccountDTO> findAccountsWithBalanceGreaterThan(
            @PathVariable double balance)
    {
        return accountService
                .findAccountsWithBalanceGreaterThan(balance);
    }

    @GetMapping("/status/{status}")
    public List<AccountDTO> findByStatus(
            @PathVariable String status)
    {
        return accountService.findByStatus(status);
    }
    
    @GetMapping("/view/transactions/{accountId}")
    public List<Transaction> viewTransactions(@PathVariable Long accountId){
    	return transactionService.viewTransactions(accountId);
    }
    
    @PostMapping("/transactions/transfer")
    public String saveTransferTransaction(
            @RequestBody TransactionDTO dto) {

    	System.out.println(
    			"FROM = " + dto.getFromAccountNumber()
    			);
    			System.out.println(
    			"TO = " + dto.getToAccountNumber()
    			);
    	AccountDTO account =
	            accountFeignClient
	                .getByAccountNumber(dto.getFromAccountNumber());
    	
        Transaction transaction = new Transaction();
         
        transaction.setAccountId(
                account.getAccountId()
        );
        transaction.setFromAccountNumber(
                dto.getFromAccountNumber());
        System.out.println(dto.getFromAccountNumber());
        System.out.println(dto.getToAccountNumber());
        transaction.setToAccountNumber(
                dto.getToAccountNumber());

        transaction.setAccountNumber(dto.getFromAccountNumber());
        transaction.setAccountNumber(account.getAccountNumber());
        transaction.setAmount(
                dto.getAmount());

        transaction.setTransactionType(
                "TRANSFER");

        transaction.setStatus(
                "SUCCESS");

        accountFeignClient.withdrawMoney(
                dto.getFromAccountNumber(),
                dto.getAmount());
        
        accountFeignClient.depositMoney(
                dto.getToAccountNumber(),
                dto.getAmount());
        
        transaction.setTransactionDate(
                LocalDateTime.now());

        repository.save(transaction);

        return "{\"message\":\"Transaction Saved\"}";
    }
    
    @PostMapping("/fd/create")
    public FixedDepositDTO createFd(
            @RequestBody FixedDepositDTO fd) {

        return accountService.createFd(fd);
    }
}