package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Account;
import com.example.demo.entity.FixedDeposit;
import com.example.demo.service.AccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	@Autowired
    AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(
            @Valid @RequestBody Account account) {

        service.createAccount(account);

        return ResponseEntity.ok(
                "Account Created Successfully"
        );
    }

    @PutMapping("updateAccountType/{id}/{type}")
    public Account update(@PathVariable Long id, @PathVariable String type) {

        return service.updateAccount(id, type);
    }

    @GetMapping("/status/{status}")
    public List<Account> findByStatus(
            @PathVariable String status) {

        return service.findByStatus(status);
    }
    
    @PutMapping("/transfer")
    public ResponseEntity<Account> transferFunds(

            @RequestParam String fromAccount,

            @RequestParam String toAccount,

            @RequestParam Double amount) {

        return ResponseEntity.ok(
                service.transferFunds(
                        fromAccount,
                        toAccount,
                        amount));
    }
    
    @PutMapping("/deposit/{accountNumber}/{amount}")
    public Account depositMoney(
            @PathVariable String accountNumber,
            @PathVariable Double amount) {

        return service.depositMoney(accountNumber, amount);
    }
    
    @PutMapping("/withdraw/{accountNumber}/{amount}")
    public Account withdrawMoney(
            @PathVariable String accountNumber,
            @PathVariable Double amount) {

        return service.withdrawMoney(accountNumber, amount);
    }
    
    @GetMapping("/balanceGreater/{balance}")
    public List<Account> findAccountsWithBalanceGreaterThan(
            @PathVariable Double balance) {

        return service.findAccountsWithBalanceGreaterThan(balance);
    }
    
    @PutMapping("/activate/{id}")
    public Account activate(@PathVariable Long id) {
        return service.activateAccount(id);
    }

    @PutMapping("/deactivate/{id}")
    public Account deactivate(@PathVariable Long id) {
        return service.deactivateAccount(id);
    }

    @PutMapping("/close/{id}")
    public Account close(@PathVariable Long id) {
        return service.closeAccount(id);
    }

    @GetMapping("/{id}")
    public Account getAccount(@PathVariable Long id) {
        return service.getAccount(id);
    }

   
    @GetMapping("/all")
    public List<Account> getAllAccounts() {

        List<Account> accounts = service.getAllAccounts();

        System.out.println(accounts);

        return accounts;
    }
    
    @GetMapping("/number/{accountNumber}")
    public Account getByAccountNumber(
            @PathVariable String accountNumber) {

        return service.getByAccountNumber(accountNumber);
    }

    @GetMapping("/type/{type}")
    public List<Account> getByType(@PathVariable String type) {
        return service.getByType(type);
    }

    @GetMapping("/active")
    public List<Account> activeAccounts() {
        return service.getActiveAccounts();
    }

    @GetMapping("/inactive")
    public List<Account> inactiveAccounts() {
        return service.getInactiveAccounts();
    }

    @GetMapping("/customer/{customerId}")
    public List<Account> getCustomerAccounts(
            @PathVariable Long customerId) {

        return service.getAccountsByCustomer(customerId);
    }
}