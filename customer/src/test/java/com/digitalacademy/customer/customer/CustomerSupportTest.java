package com.digitalacademy.customer.customer;

import com.digitalacademy.customer.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerSupportTest {
    public static List<Customer> getListCustomer() {
        List<Customer> customerList = new ArrayList();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirst_name("Ryan");
        customer.setLast_name("Giggs");
        customer.setPhoneNo("66818884484");
        customer.setEmail("gique@gique.com");
        customer.setAge(32);
        customerList.add(customer);
        customer = new Customer();
        customer.setId(2L);
        customer.setFirst_name("David");
        customer.setLast_name("Beckham");
        customer.setPhoneNo("66818884999");
        customer.setEmail("david@david.com");
        customer.setAge(45);
        customerList.add(customer);
        return customerList;
    }
    public static Customer getCreateCustomer() {
        Customer customer = new Customer();
        customer.setFirst_name("New");
        customer.setLast_name("NewNew");
        customer.setPhoneNo("66818884477");
        customer.setEmail("new@new.com");
        customer.setAge(10);
        return customer;
    }
    public static Customer getCreatedCustomer() {
        Customer customer = new Customer();
        customer.setId(6L);
        customer.setFirst_name("New");
        customer.setLast_name("NewNew");
        customer.setPhoneNo("66818884477");
        customer.setEmail("new@new.com");
        customer.setAge(10);
        return customer;
    }
    public static Customer getBeforeUpdateCustomer() {
        Customer customer = new Customer();
        customer.setId(3L);
        customer.setFirst_name("Old");
        customer.setLast_name("OldOld");
        customer.setPhoneNo("66818884477");
        customer.setEmail("old@old.com");
        customer.setAge(50);
        return customer;
    }
    public static Customer getAfterUpdateCustomer() {
        Customer customer = new Customer();
        customer.setId(3L);
        customer.setFirst_name("Old");
        customer.setLast_name("OldOld");
        customer.setPhoneNo("66818884477");
        customer.setEmail("old@old.com");
        customer.setAge(70);
        return customer;
    }
}
