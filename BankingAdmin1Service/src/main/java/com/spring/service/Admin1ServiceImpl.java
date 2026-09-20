package com.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.dto.CustomerDTO;
import com.spring.dto.LoginDTO;
import com.spring.dto.UserRegistrationDTO;
import com.spring.entity.Customer;
import com.spring.exceptions.CustomerAlreadyExistsException;
import com.spring.exceptions.CustomerNotFoundException;
import com.spring.exceptions.InputNotCorrectException;
import com.spring.repository.Admin1Repository;

@Service
public class Admin1ServiceImpl implements Admin1Service {

	@Autowired
	private Admin1Repository repository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private Customer dtoToEntity(CustomerDTO dto) {

	    Customer customer = new Customer();

	    customer.setCustomerId(dto.getCustomerId());
	    customer.setCustomerName(dto.getCustomerName());
	    customer.setEmail(dto.getEmail());
	    customer.setPhone(dto.getPhone());
	    customer.setAddress(dto.getAddress());
	    customer.setCity(dto.getCity());
	    customer.setStatus(dto.getStatus());
	    customer.setRole(dto.getRole());
	    return customer;
	}
	
	private CustomerDTO entityToDto(Customer customer) {

	    CustomerDTO dto = new CustomerDTO();

	    dto.setCustomerId(customer.getCustomerId());
	    dto.setCustomerName(customer.getCustomerName());
	    dto.setEmail(customer.getEmail());
	    dto.setPhone(customer.getPhone());
	    dto.setAddress(customer.getAddress());
	    dto.setCity(customer.getCity());
	    dto.setStatus(customer.getStatus());
	    dto.setRole(customer.getRole());
	    return dto;
	}
	
	
	@Override
	public CustomerDTO addCustomer(CustomerDTO customerDTO) {

	    if(repository.findByEmail(customerDTO.getEmail()).isPresent()) {
	        throw new CustomerAlreadyExistsException(
	                "Customer already exists with email");
	    }

	    Customer customer = dtoToEntity(customerDTO);

	    Customer savedCustomer = repository.save(customer);

	    return entityToDto(savedCustomer);
	}
	
	@Override
	public CustomerDTO getCustomerById(Long customerId) {

	    Customer customer = repository.findById(customerId)
	            .orElseThrow(() ->
	                    new CustomerNotFoundException("Customer not found"));

	    return entityToDto(customer);
	}
	
	@Override
	public List<CustomerDTO> getAllCustomers() {

	    return repository.findAll()
	            .stream()
	            .map(this::entityToDto)
	            .toList();
	}
	
	@Override
	public CustomerDTO updateCustomer(Long customerId,
	                                  CustomerDTO customer) {

		//Validation
	    Customer existingCustomer = repository.findById(customerId)
	            .orElseThrow(() ->
	                    new CustomerNotFoundException("Customer not found"));

	    existingCustomer.setCustomerName(customer.getCustomerName());

	    existingCustomer.setEmail(customer.getEmail());

	    existingCustomer.setPhone(customer.getPhone());

	    existingCustomer.setAddress(customer.getAddress());

	    existingCustomer.setCity(customer.getCity());

	    existingCustomer.setStatus(customer.getStatus());

	    Customer updatedCustomer = repository.save(existingCustomer);

	    CustomerDTO dto = new CustomerDTO();

	    dto.setCustomerId(updatedCustomer.getCustomerId());
	    dto.setCustomerName(updatedCustomer.getCustomerName());
	    dto.setEmail(updatedCustomer.getEmail());
	    dto.setPhone(updatedCustomer.getPhone());
	    dto.setAddress(updatedCustomer.getAddress());
	    dto.setCity(updatedCustomer.getCity());
	    dto.setStatus(updatedCustomer.getStatus());
	    dto.setRole(updatedCustomer.getRole());
	    return dto;
	}
	
	@Override
	public String deleteCustomer(Long customerId) {

	Customer customer = repository.findById(customerId).orElseThrow(() -> 
		new CustomerNotFoundException("Customer not found"));
		repository.delete(customer);
		return "Customer deleted successfully";
	}

	@Override
	public CustomerDTO getCustomerByEmail(String email) {

		Customer customer = repository.findByEmail(email)
		        .orElseThrow(() ->
		        new CustomerNotFoundException("Customer not found"));

		return entityToDto(customer);
	}

	@Override
	public CustomerDTO getCustomerByPhone(String phone) {
		
		Customer customer = repository.findByPhone(phone)
		        .orElseThrow(() ->
		        new CustomerNotFoundException("Customer not found"));

		return entityToDto(customer);
	}

	@Override
	public List<CustomerDTO> getCustomerByName(String name) {
		
		List<Customer> customer = repository.findByCustomerName(name);
		        
		if(customer.isEmpty()) {
			throw new CustomerNotFoundException("Customer Not Found");
		}
		return repository.findByCustomerName(name)
		        .stream()
		        .map(this::entityToDto)
		        .toList();
	}


	@Override
	public List<CustomerDTO> getCustomerByCity(String city) {
		
		return repository.findByCity(city)
		        .stream()
		        .map(this::entityToDto)
		        .toList();
	}

	@Override
	public List<CustomerDTO> getCustomerByStatus(String status) {
		
		List<Customer> customer = repository.findByStatus(status);
        
		if(customer.isEmpty()) {
			throw new CustomerNotFoundException("Customer Not Found");
		}
		return repository.findByStatus(status)
		        .stream()
		        .map(this::entityToDto)
		        .toList();
	}
	
	@Override
	public CustomerDTO registerCustomer(
	        UserRegistrationDTO dto) {

	    if(repository
	            .findByEmail(dto.getEmail())
	            .isPresent()) {

	        throw new CustomerAlreadyExistsException(
	                "Email already registered");
	    }

	    //Validation
	    if(repository
	            .findByPhone(dto.getPhone())
	            .isPresent()) {

	        throw new CustomerAlreadyExistsException(
	                "Phone already registered");
	    }

	    Customer customer =
	            new Customer();

	    customer.setCustomerName(
	            dto.getCustomerName());

	    customer.setEmail(
	            dto.getEmail());

	    customer.setPhone(
	            dto.getPhone());

	    customer.setAddress(
	            dto.getAddress());

	    customer.setCity(
	            dto.getCity());

	    customer.setPassword(
	            passwordEncoder.encode(
	                dto.getPassword()
	            )
	    );

	    customer.setRole("USER");

	    customer.setStatus("PENDING");

	    Customer saved =
	            repository.save(customer);

	    return entityToDto(saved);
	}
	@Override
	public CustomerDTO login(
	        LoginDTO dto) {

	    Customer customer =
	            repository
	                .findByEmail(
	                    dto.getEmailOrPhone())
	                .orElseGet(() ->
	                    repository.findByPhone(
	                        dto.getEmailOrPhone())
	                    .orElseThrow(
	                        () ->
	                        new CustomerNotFoundException(
	                            "User not found"
	                        )
	                    )
	                );

	    if(!passwordEncoder.matches(
	            dto.getPassword(),
	            customer.getPassword())) {

	        throw new InputNotCorrectException(
	                "Invalid Password");
	    }

	    if(!customer.getStatus().equalsIgnoreCase("ACTIVE")) {

	        throw new InputNotCorrectException(
	                "Account is pending approval by Admin");
	    }
	    return entityToDto(customer);
	}
	@Override
	public CustomerDTO approveCustomer(
	        Long customerId) {

	    Customer customer =
	            repository.findById(
	                    customerId)
	            .orElseThrow(
	                () -> new CustomerNotFoundException(
	                        "Customer not found"
	                )
	            );

	    customer.setStatus("ACTIVE");

	    Customer updated =
	            repository.save(customer);

	    return entityToDto(updated);
	}

	
}
