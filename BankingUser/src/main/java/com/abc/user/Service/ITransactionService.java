package com.abc.user.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.abc.user.entity.Transaction;
@Service
public interface ITransactionService 
{
	public List<Transaction> viewTransactions(Long accountId);
}
