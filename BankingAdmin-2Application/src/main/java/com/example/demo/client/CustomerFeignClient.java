package com.example.demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dto.CustomerDTO;

@FeignClient(
		  name="customer-service",
		  url="http://localhost:7080")
		public interface CustomerFeignClient {

		    @GetMapping("/customers/customerId/{id}")
		    CustomerDTO getCustomerById(
		            @PathVariable Long id);
		}
