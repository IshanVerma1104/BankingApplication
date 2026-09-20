package com.example.demo.dto;

public class FixedDepositDTO {

    private Long fdId;

    private Long accountId;

    private String fdNumber;

    private Double fdAmount;

    private Integer tenureMonths;

    private Double interestRate;

    private String status;

    public FixedDepositDTO() {
    }

    public FixedDepositDTO(Long fdId, Long accountId, String fdNumber,
            Double fdAmount, Integer tenureMonths,
            Double interestRate, String status) {

        this.fdId = fdId;
        this.accountId = accountId;
        this.fdNumber = fdNumber;
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

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
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
