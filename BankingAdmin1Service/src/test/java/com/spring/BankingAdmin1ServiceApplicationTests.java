package com.spring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.spring.dto.CustomerDTO;
import com.spring.entity.Customer;
import com.spring.exceptions.CustomerAlreadyExistsException;
import com.spring.exceptions.CustomerNotFoundException;
import com.spring.repository.Admin1Repository;
import com.spring.service.Admin1ServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BankingAdmin1ServiceApplicationTests {

	    @Mock
	    private Admin1Repository repository;

	    @InjectMocks
	    private Admin1ServiceImpl service;

	    private Customer customer;

	    private CustomerDTO customerDTO;

	    //Test to first set up the data for the testing
	    @BeforeEach
	    void setUp() {

	        customer = new Customer();

	        customer.setCustomerId(1L);
	        customer.setCustomerName("Ishan");
	        customer.setEmail("ishan@gmail.com");
	        customer.setPhone("9999999999");
	        customer.setAddress("Noida");
	        customer.setCity("Noida");
	        customer.setStatus("ACTIVE");

	        customerDTO = new CustomerDTO();
	        customerDTO.setCustomerId(1L);
	        customerDTO.setCustomerName("Ishan");
	        customerDTO.setEmail("ishan@gmail.com");
	        customerDTO.setPhone("9999999999");
	        customerDTO.setAddress("Noida");
	        customerDTO.setCity("Noida");
	        customerDTO.setStatus("ACTIVE");
	    }
	    
	    //Test to add the Customer
	    @Test
	    void testAddCustomer() {

	        when(repository.findByEmail(
	                customerDTO.getEmail()))
	                .thenReturn(Optional.empty());

	        when(repository.save(any(Customer.class)))
	                .thenReturn(customer);

	        CustomerDTO result =
	                service.addCustomer(customerDTO);

	        assertNotNull(result);

	        assertEquals(
	                "Ishan",
	                result.getCustomerName());

	        verify(repository,times(1))
	                .save(any(Customer.class));
	    }
	    
	    //Test Exception when creating duplicate Customer
	    @Test
	    void testAddCustomerAlreadyExists() {

	        when(repository.findByEmail(
	                customerDTO.getEmail()))
	                .thenReturn(Optional.of(customer));

	        assertThrows(
	            CustomerAlreadyExistsException.class,

	            () -> service.addCustomer(customerDTO)
	        );
	    }
	    
	    //Test to get Customer by ID
	    @Test
	    void testGetCustomerById() {

	        when(repository.findById(1L))
	                .thenReturn(Optional.of(customer));

	        CustomerDTO result =
	                service.getCustomerById(1L);

	        assertEquals(
	                "Ishan",
	                result.getCustomerName());
	    }
	    
	    //Test Exception when customer is not found by ID
	    @Test
	    void testGetCustomerByIdNotFound() {

	        when(repository.findById(1L))
	                .thenReturn(Optional.empty());

	        assertThrows(

	            CustomerNotFoundException.class,

	            () -> service.getCustomerById(1L)
	        );
	    }
	    
	    //Test for updating Customer
	    @Test
	    void testUpdateCustomer() {

	        when(repository.findById(1L))
	                .thenReturn(Optional.of(customer));

	        when(repository.save(any(Customer.class)))
	                .thenReturn(customer);

	        CustomerDTO result =
	                service.updateCustomer(
	                        1L,
	                        customerDTO);

	        assertNotNull(result);

	        verify(repository)
	                .save(any(Customer.class));
	    }
	    
	    //Test for deleting Customer
	    @Test
	    void testDeleteCustomer() {

	        when(repository.findById(1L))
	                .thenReturn(Optional.of(customer));

	        String result =
	                service.deleteCustomer(1L);

	        assertEquals(
	                "Customer deleted successfully",
	                result);

	        verify(repository,times(1))
	                .delete(customer);
	    }
	    
	    //Test to get Customer by Email
	    @Test
	    void testGetCustomerByEmail() {

	        when(repository.findByEmail(
	                "ishan@gmail.com"))
	                .thenReturn(Optional.of(customer));

	        CustomerDTO result =
	                service.getCustomerByEmail(
	                        "ishan@gmail.com");

	        assertEquals(
	                "Ishan",
	                result.getCustomerName());
	    }
	    
	    //Test Exception to get Customer by Email
	    @Test
	    void testGetCustomerByEmailException() {

	        when(repository.findByEmail(
	                "ishan@gmail.com"))
	                .thenReturn(Optional.empty());

	        assertThrows(
	                CustomerNotFoundException.class,
	                () -> service.getCustomerByEmail("ishan@gmail.com"));
	    }
	    
	    //Test to get Customer By Phone
	    @Test
	    void testGetCustomerByPhone(){
	    	
	    	when(repository.findByPhone("9999999999"))
	    	.thenReturn(Optional.of(customer));
	    	
	    	CustomerDTO result = service.getCustomerByPhone("9999999999");
	    	
	    	assertEquals("Ishan", result.getCustomerName());
	    }
	    
	    //Test Exception to get Customer By Phone
	    @Test
	    void testGetCustomerByPhoneException() {
	    	when(repository.findByPhone("9999999999"))
	    	.thenReturn(Optional.empty());
	    	
	    	assertThrows(
	    			CustomerNotFoundException.class,
	    			() -> service.getCustomerByPhone("9999999999"));
	    }
	    
	    //Test to get All Customers
	    @Test
	    void testGetAllCustomers() {

	        when(repository.findAll())
	                .thenReturn(
	                    List.of(customer)
	                );

	        List<CustomerDTO> result =
	                service.getAllCustomers();

	        assertEquals(
	                1,
	                result.size());
	    }
	    
	  //Test to get Customer By Status
	    @Test
	    void testGetCustomerByStatus(){
	    	
	    	when(repository.findByStatus("ACTIVE"))
	    	.thenReturn(List.of(customer));
	    	
	    	List<CustomerDTO> result = service.getCustomerByStatus("ACTIVE");
	    	
	    	assertEquals(1, result.size());
	    }
	    
	    //Test Exception to get Customer By Phone
	    @Test
	    void testGetCustomerByStatusException() {    	
	    	when(repository.findByStatus("ACTIVE"))
	    	.thenReturn(List.of());
	    	
	    	assertThrows(
	    			CustomerNotFoundException.class,
	    			() -> service.getCustomerByStatus("ACTIVE"));
	    }
	    
	}

