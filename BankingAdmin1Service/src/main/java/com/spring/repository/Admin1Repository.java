package com.spring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.entity.Customer;

@Repository
public interface Admin1Repository extends JpaRepository<Customer, Long>{

	public Optional<Customer> findByEmail(String email);
	
	public Optional<Customer> findByPhone(String phone);
	
	public List<Customer> findByCustomerName(String customerName);
	
	public List<Customer> findByCity(String city);
	
	public List<Customer> findByStatus(String status);
}
