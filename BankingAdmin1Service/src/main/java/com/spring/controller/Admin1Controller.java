package com.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.dto.CustomerDTO;
import com.spring.dto.LoginDTO;
import com.spring.dto.UserRegistrationDTO;
import com.spring.service.Admin1Service;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customers")
public class Admin1Controller {

	@Autowired 
	private Admin1Service service;
	
	@PostMapping("/add")
	public ResponseEntity<CustomerDTO> addCustomer(@Valid @RequestBody CustomerDTO customer) {
	
		return new ResponseEntity<>(service.addCustomer(customer), HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Long id) {

        return ResponseEntity.ok(
                service.getCustomerById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {

        return ResponseEntity.ok(
                service.getAllCustomers());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customer) {

        return ResponseEntity.ok(
                service.updateCustomer(id, customer));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {

        return ResponseEntity.ok(
                service.deleteCustomer(id));
    }

    @GetMapping("/customerId/{customerId}")
    public CustomerDTO getCustomerById(
            @PathVariable Long customerId) {

        return service
                .getCustomerById(customerId);
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<CustomerDTO> getCustomerByEmail(@PathVariable String email) {

        return ResponseEntity.ok(
                service.getCustomerByEmail(email));
    }

    @GetMapping("/phone/{phone}")
    public ResponseEntity<CustomerDTO> getCustomerByPhone(@PathVariable String phone) {

        return ResponseEntity.ok(
                service.getCustomerByPhone(phone));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity <List<CustomerDTO>> getCustomerByName(@PathVariable String name) {

        return ResponseEntity.ok(
                service.getCustomerByName(name));
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<CustomerDTO>> getCustomerByCity(@PathVariable String city) {

        return ResponseEntity.ok(
                service.getCustomerByCity(city));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<CustomerDTO>> getCustomerByStatus(@PathVariable String status) {

        return ResponseEntity.ok(
                service.getCustomerByStatus(status));
    }
    
    @PostMapping("/register")
    public ResponseEntity<CustomerDTO>
    registerCustomer(
            @Valid
            @RequestBody
            UserRegistrationDTO dto){

        return new ResponseEntity<>(
                service.registerCustomer(dto),
                HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<CustomerDTO>
    login(
            @RequestBody LoginDTO dto){

        return ResponseEntity.ok(
                service.login(dto));
    }
    
    @PutMapping("/approve/{customerId}")
    public ResponseEntity<CustomerDTO>
    approveCustomer(
            @PathVariable Long customerId){

        return ResponseEntity.ok(
                service.approveCustomer(customerId)
        );
    }
}
