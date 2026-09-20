package com.spring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@NoArgsConstructor
//@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customerId;
	
	@NotBlank
	private String customerName;
	
	@Column(unique = true)
	@Email
	private String email;
	
	@Column(unique = true)
	@NotBlank
	@Pattern(
			regexp = "^[0-9]{10}$",
			message = "Phone number must be exactly 10 digits"
			)
	private String phone;
	
	@NotBlank
	private String address;
	
	@NotBlank
	private String password;
	
	@NotBlank
	private String city;
	
	@NotBlank
	private String status;
	
	private String role = "USER";
	
	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Customer(String customerName, String email, String phone, 
			String address, String city, 
			String status, String password, String role) {
		super();
		this.customerName = customerName;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.city = city;
		this.status = status;
		this.password = password;
		this.role = role;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
	
}
