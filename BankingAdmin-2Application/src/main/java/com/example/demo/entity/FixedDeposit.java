package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fixed_deposits")
@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class FixedDeposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fdId;

    private String accountNumber;
    
    @Column(unique = true)
    private String fdNumber;

    @NotNull
    private Double fdAmount;

    @NotNull
    private Integer tenureMonths;

    @DecimalMax(value = "7", message = "Interest Rate not allowed")
    private Double interestRate;

    private String status;

    
    
	public FixedDeposit() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FixedDeposit(String accountNumber,Double fdAmount, Integer tenureMonths, Double interestRate,
			String status) {
		super();
		this.accountNumber = accountNumber;
		this.fdAmount = fdAmount;
		this.tenureMonths = tenureMonths;
		this.interestRate = interestRate;
		this.status = status;
	}

	public Long getFdId() {
		return fdId;
	}

	public void setFdId(Long fdId) {
		this.fdId = fdId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getFdNumber() {
		return fdNumber;
	}

	public void setFdNumber(String fdNumber) {
		this.fdNumber = fdNumber;
	}

	public Double getFdAmount() {
		return fdAmount;
	}

	public void setFdAmount(Double fdAmount) {
		this.fdAmount = fdAmount;
	}

	public Integer getTenureMonths() {
		return tenureMonths;
	}

	public void setTenureMonths(Integer tenureMonths) {
		this.tenureMonths = tenureMonths;
	}

	public Double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
    
}
