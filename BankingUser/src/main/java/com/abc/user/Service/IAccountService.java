package com.abc.user.Service;

import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

import com.abc.user.dto.AccountDTO;
import com.abc.user.dto.FixedDepositDTO;
import com.abc.user.exceptions.InsufficientBalanceException;
import com.abc.user.exceptions.InvalidAmountException;

public interface IAccountService 
{
	AccountDTO viewAccount(String accountNumber);
	AccountDTO depositMoney(String accountNumber , double amount);
	AccountDTO withdrawMoney(String accountNumber , double amount);
	public double checkBalance(String accountNumber);
	
	
	AccountDTO findByAccountNo(String accountNo);
//	Account findByCid(Customer cid);
    List<AccountDTO> findAccountsByAccType(String accountType);
	List<AccountDTO> findAccountsWithBalanceGreaterThan(double balance);
	List<AccountDTO> findByStatus(String status);
	FixedDepositDTO createFd(FixedDepositDTO fd);
	
}
