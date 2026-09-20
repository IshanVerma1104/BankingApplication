package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.FixedDeposit;
import com.example.demo.service.FixedDepositService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/fd")
public class FixedDepositController {

    @Autowired
    private FixedDepositService service;

    @PostMapping("/create")
    public ResponseEntity<FixedDeposit>
    createFd(@Valid @RequestBody FixedDeposit fd) {

        return ResponseEntity.ok(
                service.createFd(fd));
    }

    @PutMapping("/activate/{fdNumber}")
    public ResponseEntity<FixedDeposit>
    activateFd(@PathVariable String fdNumber) {

        return ResponseEntity.ok(
                service.activateFd(fdNumber));
    }
    
    @GetMapping("/{fdNumber}")
    public ResponseEntity<FixedDeposit>
    getFdByNumber(
            @PathVariable String fdNumber) {

        return ResponseEntity.ok(
                service.getFdByNumber(fdNumber));
    }
    
    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<List<FixedDeposit>>
    getByAccount(
            @PathVariable String accountNumber) {

        return ResponseEntity.ok(
                service.getAllFdByAccount(accountNumber));
    }
    
    @PutMapping("/deactivate/{fdNumber}")
    public ResponseEntity<FixedDeposit>
    deactivateFd(@PathVariable String fdNumber) {

        return ResponseEntity.ok(
                service.deactivateFd(fdNumber));
    }
}
