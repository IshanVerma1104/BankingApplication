package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.client.CustomerFeignClient;
import com.example.demo.client.TransactionFeignClient;
import com.example.demo.dto.CustomerDTO;
import com.example.demo.entity.Account;
import com.example.demo.exception.AccountNotFoundException;
import com.example.demo.exception.CustomerNotActiveException;
import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.exception.InsufficientBalanceException;
import com.example.demo.exception.InvalidAmountException;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.FixedDepositRepository;
import com.example.demo.service.AccountServiceImpl;

@ExtendWith(MockitoExtension.class)
class BankingAdmin2ApplicationTests {

	    @Mock
	    private AccountRepository repository;

	    @Mock
	    private FixedDepositRepository fixedrepository;

	    @Mock
	    private CustomerFeignClient customerFeignClient;

	    @Mock
	    private TransactionFeignClient transactionFeignClient;

	    @InjectMocks
	    private AccountServiceImpl service;

	    private Account account;
	    private CustomerDTO customer;

	    @BeforeEach
	    void setUp() {

	        account = new Account();

	        account.setAccountId(1L);
	        account.setAccountNumber("ACC1001");
	        account.setBalance(10000.0);
	        account.setCustomerId(101L);
	        account.setStatus("ACTIVE");
	        account.setAccountType("SAVINGS");

	        customer = new CustomerDTO();

	        customer.setCustomerId(101L);
	        customer.setCustomerName("Ishan");
	        customer.setStatus("ACTIVE");
	    }
	    
	    //Test for creating account
	    @Test
	    void testCreateAccount() {

	        when(customerFeignClient
	                .getCustomerById(101L))
	                .thenReturn(customer);

	        when(repository.save(any(Account.class)))
	                .thenReturn(account);

	        Account result =
	                service.createAccount(account);

	        assertNotNull(result);

	        assertEquals(
	                "ACTIVE",
	                result.getStatus());
	    }
	    
	    //Test Exception for creating account when customer doesn't exist 
	    @Test
	    void testCreateAccountCustomerNotFound() {

	        when(customerFeignClient
	                .getCustomerById(101L))
	                .thenReturn(null);

	        assertThrows(
	                CustomerNotFoundException.class,

	                () -> service.createAccount(account)
	        );
	    }
	    
	    //Test Exception for creating account when customer is inactive
	    @Test
	    void testCreateAccountCustomerInactive() {

	        customer.setStatus("INACTIVE");

	        when(customerFeignClient
	                .getCustomerById(101L))
	                .thenReturn(customer);

	        assertThrows(
	                CustomerNotActiveException.class,

	                () -> service.createAccount(account)
	        );
	    }
	    
	    //Test for getting the account
	    @Test
	    void testGetAccount() {

	        when(repository.findById(1L))
	                .thenReturn(Optional.of(account));

	        Account result =
	                service.getAccount(1L);

	        assertEquals(
	                "ACC1001",
	                result.getAccountNumber());
	    }
	    
	    //Test Exception when account is not found
	    @Test
	    void testGetAccountNotFound() {

	        when(repository.findById(1L))
	                .thenReturn(Optional.empty());

	        assertThrows(
	                AccountNotFoundException.class,

	                () -> service.getAccount(1L)
	        );
	    }
	    
	    //Test to deposit money successfully
	    @Test
	    void testDepositMoney() {

	        when(repository.findById(1L))
	                .thenReturn(Optional.of(account));

	        when(repository.save(any(Account.class)))
	                .thenReturn(account);

	        Account result =
	                service.depositMoney(
	                        "ACC1001",
	                        1000.0);

	        assertNotNull(result);

	        verify(repository)
	                .save(any(Account.class));
	    }
	    
	    //Test deposit check Invalid Amount Exception
	    @Test
	    void testDepositMoneyInvalidAmount() {

	        when(repository.findById(1L))
	                .thenReturn(Optional.of(account));

	        assertThrows(
	                InvalidAmountException.class,

	                () -> service.depositMoney(
	                		"ACC1001",
	                        -500.0)
	        );
	    }
	    
	    //Test to withdrawal money
	    @Test
	    void testWithdrawMoney() {

	        when(repository.findById(1L))
	                .thenReturn(Optional.of(account));

	        when(repository.save(any(Account.class)))
	                .thenReturn(account);

	        Account result =
	                service.withdrawMoney(
	                		"ACC1001",
	                        1000.0);

	        assertNotNull(result);

	        verify(repository)
	                .save(any(Account.class));
	    }
	    
	    //Test to check Insufficient Balance Exception
	    @Test
	    void testWithdrawMoneyInsufficientBalance() {

	        account.setBalance(500.0);

	        when(repository.findById(1L))
	                .thenReturn(Optional.of(account));

	        assertThrows(
	                InsufficientBalanceException.class,

	                () -> service.withdrawMoney(
	                		"ACC1001",
	                        1000.0)
	        );
	    }
	    
	    //Test Withdrawal to check Invalid Amount Exception
	    @Test
	    void testWithdrawInvalidAmount() {

	        when(repository.findById(1L))
	                .thenReturn(Optional.of(account));

	        assertThrows(
	                InvalidAmountException.class,

	                () -> service.withdrawMoney(
	                		"ACC1001",
	                        -100.0)
	        );
	    }
	    
	    //Test to activate account
	    @Test
	    void testActivateAccount() {

	        when(repository.findById(1L))
	                .thenReturn(Optional.of(account));

	        when(repository.save(any(Account.class)))
	                .thenReturn(account);

	        Account result =
	                service.activateAccount(1L);

	        assertEquals(
	                "ACTIVE",
	                result.getStatus());
	    }
	    
	    //Test to deactivate account
	    @Test
	    void testDeactivateAccount() {

	        when(repository.findById(1L))
	                .thenReturn(Optional.of(account));

	        account.setStatus("INACTIVE");

	        when(repository.save(any(Account.class)))
	                .thenReturn(account);

	        Account result =
	                service.deactivateAccount(1L);

	        assertEquals(
	                "INACTIVE",
	                result.getStatus());
	    }
	    
	    //Test to close account
	    @Test
	    void testCloseAccount() {

	        when(repository.findById(1L))
	                .thenReturn(Optional.of(account));

	        account.setStatus("CLOSED");

	        when(repository.save(any(Account.class)))
	                .thenReturn(account);

	        Account result =
	                service.closeAccount(1L);

	        assertEquals(
	                "CLOSED",
	                result.getStatus());
	    }
	    
	    //Test to transfer money from one account to another
	    @Test
	    void testTransferFunds() {

	        Account sender = new Account();

	        sender.setAccountId(1L);
	        sender.setAccountNumber("ACC1001");
	        sender.setBalance(10000.0);

	        Account receiver = new Account();

	        receiver.setAccountId(2L);
	        receiver.setAccountNumber("ACC1002");
	        receiver.setBalance(5000.0);

	        when(repository.findByAccountNumber("ACC1001"))
	                .thenReturn(Optional.of(sender));

	        when(repository.findByAccountNumber("ACC1002"))
	                .thenReturn(Optional.of(receiver));

	        Account result =
	                service.transferFunds(
	                        "ACC1001",
	                        "ACC1002",
	                        1000.0);

	        verify(repository,times(2))
	                .save(any(Account.class));

	        verify(transactionFeignClient,times(1))
	                .saveTransferTransaction(any());

	        assertNotNull(result);
	    }
	    
	    //Test Exception in transfer funds for Insufficient Balance 
	    @Test
	    void testTransferFundsInsufficientBalance() {

	        account.setBalance(500.0);

	        when(repository.findByAccountNumber("ACC1001"))
	                .thenReturn(Optional.of(account));

	        when(repository.findByAccountNumber("ACC1002"))
	                .thenReturn(Optional.of(new Account()));

	        assertThrows(
	                InsufficientBalanceException.class,

	                () -> service.transferFunds(
	                        "ACC1001",
	                        "ACC1002",
	                        1000.0)
	        );
	    }
	    
	    
	}

