package com.abc.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.abc.user.dto.FixedDepositDTO;

@FeignClient(
        name = "fd-service",
        url = "http://localhost:7082")
public interface FixedDepositFeignClient {

    @PostMapping("/fd/create")
    FixedDepositDTO createFd(
            @RequestBody FixedDepositDTO fd);

    @PutMapping("/fd/activate/{fdNumber}")
    FixedDepositDTO activateFd(
            @PathVariable String fdNumber);

    @PutMapping("/fd/deactivate/{fdNumber}")
    FixedDepositDTO deactivateFd(
            @PathVariable String fdNumber);

    @GetMapping("/fd/{fdNumber}")
    FixedDepositDTO getFd(
            @PathVariable String fdNumber);

}