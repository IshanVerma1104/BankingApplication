package com.abc.user.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.abc.user.dto.AccountDTO;
@FeignClient(
        name="account-service",
        url="http://localhost:7082")
public interface AccountFeignClient {

    @GetMapping("/accounts/{id}")
    AccountDTO getAccountById(
            @PathVariable Long id);

    @GetMapping("/accounts/number/{accountNumber}")
    AccountDTO getByAccountNumber(
            @PathVariable("accountNumber") String accountNumber);

    @GetMapping("/accounts/type/{type}")
    List<AccountDTO> findAccountsByType(
            @PathVariable String type);

    @GetMapping("/accounts/customer/{customerId}")
    List<AccountDTO> getCustomerAccounts(
            @PathVariable Long customerId);

    @GetMapping("/accounts/active")
    List<AccountDTO> getActiveAccounts();

    @GetMapping("/accounts/inactive")
    List<AccountDTO> getInactiveAccounts();

    @PutMapping("/accounts/deposit/{accountNumber}/{amount}")
    AccountDTO depositMoney(
            @PathVariable String accountNumber,
            @PathVariable Double  amount);

    @PutMapping("/accounts/withdraw/{accountNumber}/{amount}")
    AccountDTO withdrawMoney(
            @PathVariable String accountNumber,
            @PathVariable Double amount);

    @GetMapping("/accounts/balanceGreater/{balance}")
    List<AccountDTO> findAccountsWithBalanceGreaterThan(
            @PathVariable Double balance);
    
    @GetMapping("/accounts/status/{status}")
    List<AccountDTO> findByStatus(
            @PathVariable String status);
}