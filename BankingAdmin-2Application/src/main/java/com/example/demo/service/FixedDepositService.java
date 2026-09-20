package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.FixedDeposit;

public interface FixedDepositService {

	public FixedDeposit createFd(FixedDeposit fd);
	
	public FixedDeposit getFdByNumber(String fdNumber);
	
	public List<FixedDeposit> getAllFdByAccount(String accountNumber);
	
	public FixedDeposit activateFd(String fdNumber);
	
	public FixedDeposit deactivateFd(String fdNumber);
	
}
