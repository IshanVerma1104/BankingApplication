package com.abc.user.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.user.entity.Transaction;
import com.abc.user.repository.ITransactionRepository;
@Service
public class ITransactionServiceImpl implements ITransactionService {

	@Autowired
	private ITransactionRepository repository;
	
	@Override
	public List<Transaction> viewTransactions(Long accountId) {
		// TODO Auto-generated method stub
		return repository.findByAccountId(accountId);
	}
	

}
