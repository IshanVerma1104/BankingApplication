package com.abc.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abc.user.entity.Transaction;
@Repository
public interface ITransactionRepository extends JpaRepository<Transaction, Long> 
{
	public List<Transaction> findByAccountId(Long accountId);
}
