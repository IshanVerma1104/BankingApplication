package com.example.demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.dto.TransactionDTO;

@FeignClient(
        name = "transaction-service",
        url = "http://localhost:7081")
public interface TransactionFeignClient {

    @PostMapping(value = "/account/transactions/transfer",
    		consumes = "application/json")
    String saveTransferTransaction(
            @RequestBody TransactionDTO dto);
}
