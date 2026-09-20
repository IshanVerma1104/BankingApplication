package com.spring;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.mockito.junit.jupiter.MockitoExtension;
import com.spring.controller.Admin1Controller;
import com.spring.dto.CustomerDTO;
import com.spring.dto.LoginDTO;
import com.spring.dto.UserRegistrationDTO;
import com.spring.service.Admin1Service;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
@ExtendWith(MockitoExtension.class)
class Admin1ControllerTest {

    @InjectMocks
    private Admin1Controller controller;

    @Mock
    private Admin1Service service;

    private CustomerDTO customer;

    @BeforeEach
    void setUp() {

        customer = new CustomerDTO();

        customer.setCustomerId(1L);
        customer.setCustomerName("Ishan");
        customer.setEmail("ishan@gmail.com");
        customer.setPhone("9999999999");
        customer.setCity("Noida");
        customer.setStatus("ACTIVE");
    }

    @Test
    void testAddCustomer() {

        when(service.addCustomer(any(CustomerDTO.class)))
                .thenReturn(customer);

        ResponseEntity<CustomerDTO> response =
                controller.addCustomer(customer);

        assertEquals(
                HttpStatus.CREATED,
                response.getStatusCode());

        assertEquals(
                customer,
                response.getBody());
    }

    @Test
    void testGetCustomer() {

        when(service.getCustomerById(1L))
                .thenReturn(customer);

        ResponseEntity<CustomerDTO> response =
                controller.getCustomer(1L);

        assertEquals(
                HttpStatus.OK,
                response.getStatusCode());

        assertEquals(
                customer,
                response.getBody());
    }

    @Test
    void testGetAllCustomers() {

        List<CustomerDTO> customers =
                List.of(customer);

        when(service.getAllCustomers())
                .thenReturn(customers);

        ResponseEntity<List<CustomerDTO>> response =
                controller.getAllCustomers();

        assertEquals(
                customers,
                response.getBody());
    }
    @Test
    void testUpdateCustomer() {

        when(service.updateCustomer(
                eq(1L),
                any(CustomerDTO.class)))
                .thenReturn(customer);

        ResponseEntity<CustomerDTO> response =
                controller.updateCustomer(
                        1L,
                        customer);

        assertEquals(
                customer,
                response.getBody());
    }
    @Test
    void testDeleteCustomer() {

        when(service.deleteCustomer(1L))
                .thenReturn(
                        "Customer deleted successfully");

        ResponseEntity<String> response =
                controller.deleteCustomer(1L);

        assertEquals(
                "Customer deleted successfully",
                response.getBody());
    }
    @Test
    void testGetCustomerById() {

        when(service.getCustomerById(1L))
                .thenReturn(customer);

        CustomerDTO result =
                controller.getCustomerById(1L);

        assertEquals(
                customer,
                result);
    }
    @Test
    void testGetCustomerByEmail() {

        when(service.getCustomerByEmail(
                "ishan@gmail.com"))
                .thenReturn(customer);

        ResponseEntity<CustomerDTO> response =
                controller.getCustomerByEmail(
                        "ishan@gmail.com");

        assertEquals(
                customer,
                response.getBody());
    }
    @Test
    void testGetCustomerByPhone() {

        when(service.getCustomerByPhone(
                "9999999999"))
                .thenReturn(customer);

        ResponseEntity<CustomerDTO> response =
                controller.getCustomerByPhone(
                        "9999999999");

        assertEquals(
                customer,
                response.getBody());
    }
    @Test
    void testGetCustomerByName() {

        List<CustomerDTO> customers =
                List.of(customer);

        when(service.getCustomerByName("Ishan"))
                .thenReturn(customers);

        ResponseEntity<List<CustomerDTO>> response =
                controller.getCustomerByName(
                        "Ishan");

        assertEquals(
                customers,
                response.getBody());
    }
    @Test
    void testGetCustomerByCity() {

        List<CustomerDTO> customers =
                List.of(customer);

        when(service.getCustomerByCity("Noida"))
                .thenReturn(customers);

        ResponseEntity<List<CustomerDTO>> response =
                controller.getCustomerByCity(
                        "Noida");

        assertEquals(
                customers,
                response.getBody());
    }
    @Test
    void testGetCustomerByStatus() {

        List<CustomerDTO> customers =
                List.of(customer);

        when(service.getCustomerByStatus(
                "ACTIVE"))
                .thenReturn(customers);

        ResponseEntity<List<CustomerDTO>> response =
                controller.getCustomerByStatus(
                        "ACTIVE");

        assertEquals(
                customers,
                response.getBody());
    }
    @Test
    void testRegisterCustomer() {

        UserRegistrationDTO dto =
                new UserRegistrationDTO();

        when(service.registerCustomer(dto))
                .thenReturn(customer);

        ResponseEntity<CustomerDTO> response =
                controller.registerCustomer(dto);

        assertEquals(
                HttpStatus.CREATED,
                response.getStatusCode());

        assertEquals(
                customer,
                response.getBody());
    }
    @Test
    void testLogin() {

        LoginDTO dto =
                new LoginDTO();

        when(service.login(dto))
                .thenReturn(customer);

        ResponseEntity<CustomerDTO> response =
                controller.login(dto);

        assertEquals(
                customer,
                response.getBody());
    }
    @Test
    void testApproveCustomer() {

        when(service.approveCustomer(1L))
                .thenReturn(customer);

        ResponseEntity<CustomerDTO> response =
                controller.approveCustomer(1L);

        assertEquals(
                customer,
                response.getBody());
    }
    
}
