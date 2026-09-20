package com.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.dto.CustomerDTO;
import com.spring.dto.LoginDTO;
import com.spring.dto.UserRegistrationDTO;
import com.spring.entity.Customer;

@Service
public interface Admin1Service {

	public CustomerDTO addCustomer(CustomerDTO customer);
	
	public CustomerDTO getCustomerById(Long customerId);
	
	public List<CustomerDTO> getAllCustomers();
	
	public CustomerDTO updateCustomer(Long customerId, CustomerDTO customer);
	
	public String deleteCustomer(Long customerId);
	
	public CustomerDTO getCustomerByEmail(String email);
	
	public CustomerDTO getCustomerByPhone(String phone);
	
	public List<CustomerDTO> getCustomerByName(String name);
	
	public List<CustomerDTO> getCustomerByCity(String city);
	
	public List<CustomerDTO> getCustomerByStatus(String status);
	
	public CustomerDTO registerCustomer(UserRegistrationDTO dto);
	
	public CustomerDTO login(LoginDTO dto);
	
	public CustomerDTO approveCustomer(Long customerId);
}
