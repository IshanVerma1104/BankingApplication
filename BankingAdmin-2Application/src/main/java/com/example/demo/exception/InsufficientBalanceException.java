package com.example.demo.exception;

public class InsufficientBalanceException extends RuntimeException
{
	double balance;
	public InsufficientBalanceException(String message) {
		super(message);
		this.balance = balance;
	}
		
		@Override
		public String toString() {
			return "InsufficientBalanceException [balance=" + balance + "]";
		}
}
