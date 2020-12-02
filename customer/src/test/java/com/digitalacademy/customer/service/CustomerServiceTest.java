package com.digitalacademy.customer.service;

import com.digitalacademy.customer.customer.CustomerSupportTest;
import com.digitalacademy.customer.model.Customer;
import com.digitalacademy.customer.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerService(customerRepository);
    }

    @DisplayName("Test get all customer should return list of customer")
    @Test
    void testGetAllCustomer() {
        when(customerRepository.findAll()).thenReturn(CustomerSupportTest.getListCustomer());
        List<Customer> resp = customerService.getCustomerList();

        assertEquals(1, resp.get(0).getId().intValue());
        assertEquals("Ryan", resp.get(0).getFirst_name());
        assertEquals("Giggs", resp.get(0).getLast_name());
        assertEquals("66818884484", resp.get(0).getPhoneNo());
        assertEquals("gique@gique.com", resp.get(0).getEmail());
        assertEquals(32, resp.get(0).getAge().intValue());

        assertEquals(2, resp.get(1).getId().intValue());
        assertEquals("David", resp.get(1).getFirst_name());
        assertEquals("Beckham", resp.get(1).getLast_name());
        assertEquals("66818884999", resp.get(1).getPhoneNo());
        assertEquals("david@david.com", resp.get(1).getEmail());
        assertEquals(45, resp.get(1).getAge().intValue());

    }

    @DisplayName("Test get all customer should return list of customer")
    @Test
    void testGetCustomerById() {
        Long reqParam = 10L;

        when(customerRepository.findAllById(reqParam)).thenReturn(CustomerSupportTest.getListCustomer().get(0));

        Customer resp = customerService.getCustomerById(10L);

        assertEquals(1, resp.getId().intValue());
        assertEquals("Ryan", resp.getFirst_name());
        assertEquals("Giggs", resp.getLast_name());
        assertEquals("66818884484", resp.getPhoneNo());
        assertEquals("gique@gique.com", resp.getEmail());
        assertEquals(32, resp.getAge().intValue());

    }

    @DisplayName("Test get all customer should return list of customer")
    @Test
    void testCreateCustomer() {
        when(customerRepository.save(CustomerSupportTest.getCreateCustomer()))
                .thenReturn(CustomerSupportTest.getCreatedCustomer());

        Customer resp = customerService.createCustomer(CustomerSupportTest.getCreateCustomer());

        assertEquals(6, resp.getId().intValue());
        assertEquals("New", resp.getFirst_name());
        assertEquals("NewNew", resp.getLast_name());
        assertEquals("66818884477", resp.getPhoneNo());
        assertEquals("new@new.com", resp.getEmail());
        assertEquals(10, resp.getAge().intValue());
    }

    @DisplayName("Test get all customer should return list of customer")
    @Test
    void testUpdateCustomer() {
        Long reqId = 3L;
        when(customerRepository.findAllById(reqId)).thenReturn(CustomerSupportTest.getBeforeUpdateCustomer());
        when(customerRepository.save(CustomerSupportTest.getAfterUpdateCustomer()))
                .thenReturn(CustomerSupportTest.getAfterUpdateCustomer());

        Customer resp = customerService.updateCustomer(reqId, CustomerSupportTest.getAfterUpdateCustomer());

        assertEquals(3, resp.getId().intValue());
        assertEquals("Old", resp.getFirst_name());
        assertEquals("OldOld", resp.getLast_name());
        assertEquals("66818884477", resp.getPhoneNo());
        assertEquals("old@old.com", resp.getEmail());
        assertEquals(70, resp.getAge().intValue());
    }

    @DisplayName("Test get all customer should return list of customer")
    @Test
    void testUpdateCustomerFail() {
        Long reqId = 3L;
        when(customerRepository.findAllById(reqId)).thenReturn(null);

        Customer resp = customerService.updateCustomer(reqId, CustomerSupportTest.getAfterUpdateCustomer());

        assertEquals(null, resp);
    }

    @DisplayName("Test get all customer should return list of customer")
    @Test
    void testDeleteCustomer() {
        Long reqId = 1L;
        doNothing().when(customerRepository).deleteById(reqId);
        boolean resp = customerService.deleteById(reqId);

        assertEquals(true, resp);
        assertTrue(resp);
    }

    @DisplayName("Test get all customer should return list of customer")
    @Test
    void testDeleteCustomerFail() {
        Long reqId = 1L;
        doThrow(EmptyResultDataAccessException.class)
                .when(customerRepository).deleteById(reqId);

        boolean resp = customerService.deleteById(reqId);
        assertEquals(false, resp);
        assertFalse(resp);
    }


}
