package com.abc.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.abc.user.Service.AccountServiceClassImpl;
import com.abc.user.client.AccountFeignClient;
import com.abc.user.client.FixedDepositFeignClient;
import com.abc.user.dto.AccountDTO;
import com.abc.user.dto.FixedDepositDTO;
import com.abc.user.entity.Transaction;
import com.abc.user.exceptions.AccountNotActiveException;
import com.abc.user.exceptions.AccountNotFoundException;
import com.abc.user.exceptions.BankingUserExceptionHandler;
import com.abc.user.exceptions.ErrorResponse;
import com.abc.user.exceptions.InsufficientBalanceException;
import com.abc.user.exceptions.InvalidAmountException;
import com.abc.user.exceptions.TransactionNotFoundException;
import com.abc.user.repository.ITransactionRepository;


@ExtendWith(MockitoExtension.class)
class BankingUserApplicationTests {

	    @InjectMocks
	    private AccountServiceClassImpl service;

	    @Mock
	    private AccountFeignClient accountFeignClient;

	    @Mock
	    private FixedDepositFeignClient fdFeignClient;

	    @Mock
	    private ITransactionRepository transactionRepository;
	    
//	    //Test to view Account
//	    @Test
//	    void testViewAccountSuccess() throws AccountNotFoundException{
//
//	        AccountDTO account = new AccountDTO();
//	        account.setAccountNumber("cs101");
//
//	        when(accountFeignClient.getByAccountNumber("cs101"))
//	                .thenReturn(account);
//
//	        AccountDTO result = service.viewAccount("cs101");
//
//	        assertNotNull(result);
//	        assertEquals("cs101", result.getAccountNumber());
//	    }
	    
	    //Test Exception to view Account
	    @Test
	    void testViewAccountNotFound() {

	        when(accountFeignClient.getByAccountNumber("cs101"))
	                .thenReturn(null);

	        assertThrows(
	                AccountNotFoundException.class,
	                () -> service.viewAccount("cs101"));
	    }
	    
	    //Test Deposit Money
	    @Test
	    void testDepositMoneySuccess() throws Exception {

	        AccountDTO account = new AccountDTO();
	        account.setAccountId(1L);
	        account.setAccountNumber("cs101");
	        account.setStatus("ACTIVE");

	        when(accountFeignClient.getByAccountNumber("cs101"))
	                .thenReturn(account);

	        when(accountFeignClient.depositMoney("cs101", 1000.00))
	                .thenReturn(account);

	        AccountDTO result =
	                service.depositMoney("cs101", 1000);

	        assertNotNull(result);

	        verify(transactionRepository,
	                times(1)).save(any(Transaction.class));
	    }
	    
	    //Test Deposit Invalid Amount Exception
	    @Test
	    void testDepositMoneyInvalidAmount() {

	        assertThrows(
	                InvalidAmountException.class,
	                () -> service.depositMoney("cs101", -100));
	    }
	    
	    //Test Deposit Account Not Found Exception
	    @Test
	    void testDepositMoneyAccountNotFound() {

	        when(accountFeignClient.getByAccountNumber("cs101"))
	                .thenReturn(null);

	        assertThrows(
	                AccountNotFoundException.class,
	                () -> service.depositMoney("cs101", 1000));
	    }
	    
	    //Test Deposit Account Inactive Exception
	    @Test
	    void testDepositMoneyInactiveAccount() {

	        AccountDTO account = new AccountDTO();

	        account.setAccountNumber("cs101");
	        account.setStatus("INACTIVE");

	        when(accountFeignClient.getByAccountNumber("cs101"))
	                .thenReturn(account);

	        assertThrows(
	                AccountNotActiveException.class,
	                () -> service.depositMoney("cs101", 1000));
	    }
	    
	    //Test Withdrawal Money
	    @Test
	    void testWithdrawMoneySuccess() throws Exception {

	        AccountDTO account = new AccountDTO();

	        account.setAccountId(1L);
	        account.setAccountNumber("cs101");
	        account.setBalance(5000.00);
	        account.setStatus("ACTIVE");

	        when(accountFeignClient.getByAccountNumber("cs101"))
	                .thenReturn(account);

	        when(accountFeignClient.withdrawMoney("cs101", 1000.00))
	                .thenReturn(account);

	        AccountDTO result =
	                service.withdrawMoney("cs101", 1000);

	        assertNotNull(result);

	        verify(transactionRepository)
	                .save(any(Transaction.class));
	    }
	    
	    //Test Withdrawal Invalid Amount Exception
	    @Test
	    void testWithdrawMoneyInvalidAmount() {

	        assertThrows(
	                InvalidAmountException.class,
	                () -> service.withdrawMoney("cs101", -500));
	    }
	    
	    //Test Withdrawal Insufficient Balance Exception
	    @Test
	    void testWithdrawMoneyInsufficientBalance() {

	        AccountDTO account = new AccountDTO();

	        account.setAccountNumber("cs101");
	        account.setBalance(500.00);

	        when(accountFeignClient.getByAccountNumber("cs101"))
	                .thenReturn(account);

	        assertThrows(
	                InsufficientBalanceException.class,
	                () -> service.withdrawMoney("cs101", 1000));
	    }

	    //Test Withdrawal Account Not Active Exception
	    @Test
	    void testWithdrawMoneyAccountNotActive() {

	        AccountDTO account = new AccountDTO();

	        account.setAccountNumber("cs101");
	        account.setBalance(5000.00);
	        account.setStatus("INACTIVE");

	        when(accountFeignClient.getByAccountNumber("cs101"))
	                .thenReturn(account);

	        assertThrows(
	                AccountNotActiveException.class,
	                () -> service.withdrawMoney("cs101", 1000));
	    }
	    
	    //Test Check Balance
	    @Test
	    void testCheckBalance() throws Exception {

	        AccountDTO account = new AccountDTO();

	        account.setAccountNumber("cs101");
	        account.setBalance(7000.00);

	        when(accountFeignClient.getByAccountNumber("cs101"))
	                .thenReturn(account);

	        double result =
	                service.checkBalance("cs101");

	        assertEquals(7000, result);
	    }
	    
	    //Test to check for Account Number
	    @Test
	    void testFindByAccountNo() {

	        AccountDTO account = new AccountDTO();

	        when(accountFeignClient
	                .getByAccountNumber("cs101"))
	                .thenReturn(account);

	        AccountDTO result =
	                service.findByAccountNo("cs101");

	        assertNotNull(result);
	    }
	    //Test to check for Account Type
	    @Test
	    void testFindAccountsByAccType() {

	        List<AccountDTO> list =
	                List.of(new AccountDTO());

	        when(accountFeignClient
	                .findAccountsByType("SAVINGS"))
	                .thenReturn(list);

	        List<AccountDTO> result =
	                service.findAccountsByAccType("SAVINGS");

	        assertEquals(1, result.size());
	    }
	    
	    //Test Find Balance greater than value
	    @Test
	    void testFindAccountsWithBalanceGreaterThan() {

	        List<AccountDTO> list =
	                List.of(new AccountDTO());

	        when(accountFeignClient
	                .findAccountsWithBalanceGreaterThan(1000.00))
	                .thenReturn(list);

	        List<AccountDTO> result =
	                service.findAccountsWithBalanceGreaterThan(1000);

	        assertEquals(1, result.size());
	    }
	    
	    //Test to find Status
	    @Test
	    void testFindByStatus() {

	        List<AccountDTO> list =
	                List.of(new AccountDTO());

	        when(accountFeignClient
	                .findByStatus("ACTIVE"))
	                .thenReturn(list);

	        List<AccountDTO> result =
	                service.findByStatus("ACTIVE");

	        assertEquals(1, result.size());
	    }
	    
	    //Test to create FD
	    @Test
	    void testCreateFd() {

	        FixedDepositDTO fd =
	                new FixedDepositDTO();

	        when(fdFeignClient.createFd(fd))
	                .thenReturn(fd);

	        FixedDepositDTO result =
	                service.createFd(fd);

	        assertNotNull(result);
	    }
	    @Test
	    void testDepositAccountNotFound() {

	        when(accountFeignClient.getByAccountNumber("CS999"))
	                .thenReturn(null);

	        assertThrows(
	                AccountNotFoundException.class,
	                () -> service.depositMoney("CS999", 1000)
	        );
	    }
	    @Test
	    void testInvalidDepositAmount() {

	        assertThrows(
	                InvalidAmountException.class,
	                () -> service.depositMoney("CS101", -100)
	        );
	    }@Test
	    void testWithdrawInvalidAmount() {

	        assertThrows(
	                InvalidAmountException.class,
	                () -> service.withdrawMoney("CS101", -100)
	        );
	    }
	    @Test
	    void testWithdrawAccountNotFound() {

	        when(accountFeignClient.getByAccountNumber("CS999"))
	                .thenReturn(null);

	        assertThrows(
	                AccountNotFoundException.class,
	                () -> service.withdrawMoney("CS999", 1000)
	        );
	    }
	    @Test
	    void testWithdrawInsufficientBalance() {

	        AccountDTO dto = new AccountDTO();
	        dto.setAccountNumber("CS101");
	        dto.setBalance(1000.0);
	        dto.setStatus("ACTIVE");

	        when(accountFeignClient.getByAccountNumber("CS101"))
	                .thenReturn(dto);

	        assertThrows(
	                InsufficientBalanceException.class,
	                () -> service.withdrawMoney("CS101", 2000)
	        );
	    }
	    @Test
	    void testDepositInactiveAccount() {

	        AccountDTO dto = new AccountDTO();
	        dto.setStatus("INACTIVE");

	        when(accountFeignClient.getByAccountNumber("CS101"))
	                .thenReturn(dto);

	        assertThrows(
	                AccountNotActiveException.class,
	                () -> service.depositMoney("CS101", 1000)
	        );
	    }
	    @Test
	    void testHandleAccountNotFoundException() {

	        BankingUserExceptionHandler handler =
	                new BankingUserExceptionHandler();

	        ResponseEntity<ErrorResponse> response =
	                handler.handleAccountNotFoundException(
	                        new AccountNotFoundException(
	                                "Account not found"));

	        assertEquals(
	                HttpStatus.NOT_FOUND,
	                response.getStatusCode());

	        assertEquals(
	                "Account not found",
	                response.getBody().getIssueName());
	    }
	    @Test
	    void testErrorResponseDefaultConstructor() {

	        ErrorResponse error = new ErrorResponse();

	        error.setIssueName("Account Not Found");
	        error.setDate(LocalDate.of(2026, 9, 7));

	        assertEquals(
	                "Account Not Found",
	                error.getIssueName());

	        assertEquals(
	                LocalDate.of(2026, 9, 7),
	                error.getDate());
	    }
	    @Test
	    void testErrorResponseParameterizedConstructor() {

	        LocalDate date =
	                LocalDate.of(2026, 9, 7);

	        ErrorResponse error =
	                new ErrorResponse(
	                        "Account Not Found",
	                        date);

	        assertEquals(
	                "Account Not Found",
	                error.getIssueName());

	        assertEquals(
	                date,
	                error.getDate());
	    }
	    @Test
	    void testErrorResponseEquals() {

	        LocalDate date =
	                LocalDate.now();

	        ErrorResponse error1 =
	                new ErrorResponse(
	                        "Invalid Amount",
	                        date);

	        ErrorResponse error2 =
	                new ErrorResponse(
	                        "Invalid Amount",
	                        date);

	        assertEquals(
	                error1,
	                error2);
	    }
	    @Test
	    void testErrorResponseNotEquals() {

	        ErrorResponse error1 =
	                new ErrorResponse(
	                        "Invalid Amount",
	                        LocalDate.now());

	        ErrorResponse error2 =
	                new ErrorResponse(
	                        "Account Not Found",
	                        LocalDate.now());

	        assertNotEquals(
	                error1,
	                error2);
	    }
	    @Test
	    void testErrorResponseHashCode() {

	        LocalDate date =
	                LocalDate.now();

	        ErrorResponse error1 =
	                new ErrorResponse(
	                        "Account Not Found",
	                        date);

	        ErrorResponse error2 =
	                new ErrorResponse(
	                        "Account Not Found",
	                        date);

	        assertEquals(
	                error1.hashCode(),
	                error2.hashCode());
	    }
	    @Test
	    void testErrorResponseToString() {

	        ErrorResponse error =
	                new ErrorResponse(
	                        "Invalid Amount",
	                        LocalDate.of(2026, 9, 7));

	        String result =
	                error.toString();

	        assertTrue(
	                result.contains("Invalid Amount"));

	        assertTrue(
	                result.contains("2026-09-07"));
	    }
	    @Test
	    void testHandleInsufficientBalanceException() {

	        BankingUserExceptionHandler handler =
	                new BankingUserExceptionHandler();

	        ResponseEntity<ErrorResponse> response =
	                handler.handleInsufficientBalanceException(
	                        new InsufficientBalanceException(
	                                "Insufficient Balance"));

	        assertEquals(
	                HttpStatus.NOT_FOUND,
	                response.getStatusCode());

	        assertEquals(
	                "Insufficient Balance",
	                response.getBody().getIssueName());
	    }
	    @Test
	    void testHandleTransactionNotFoundException() {

	        BankingUserExceptionHandler handler =
	                new BankingUserExceptionHandler();

	        ResponseEntity<ErrorResponse> response =
	                handler.handleTransactionNotFoundException(
	                        new TransactionNotFoundException(
	                                "Transaction Not Found"));

	        assertEquals(
	                HttpStatus.NOT_FOUND,
	                response.getStatusCode());

	        assertEquals(
	                "Transaction Not Found",
	                response.getBody().getIssueName());
	    }
	    @Test
	    void testHandleAccountNotActiveException() {

	        BankingUserExceptionHandler handler =
	                new BankingUserExceptionHandler();

	        ResponseEntity<ErrorResponse> response =
	                handler.handleAccountNotActiveException(
	                        new AccountNotActiveException(
	                                "Account not active"));

	        assertEquals(
	                HttpStatus.NOT_FOUND,
	                response.getStatusCode());

	        assertEquals(
	                "Account not active",
	                response.getBody().getIssueName());
	    }
	    
}



