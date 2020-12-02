package com.digitalacademy.customer.controller;

import com.digitalacademy.customer.model.Customer;
import com.digitalacademy.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
//import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;
//import java.util.Map;

@RestController
@RequestMapping(path = "/customer")
public class CustomerController {
    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @GetMapping("/list")
    public List<Customer> customerList() {
//        Map<String, String> customer = new HashMap<>();
//        ArrayList<Map> customers = new ArrayList<>();
//        customer.put("1", "Gique");
//        customer.put("2", "Net");
//        customer.put("3", "First");
//        customers.add(customer);
//        return customers;

//        List<Customer> css = new ArrayList<>();
//        Customer cs = new Customer();
//        cs.setId(1L);
//        cs.setFirstName("Ryan");
//        cs.setLastName("Giqgs");
//        cs.setEmail("giqgs@test.com");
//        cs.setPhoneNo("6665559999");
//        cs.setAge(18);
//        css.add(cs);
//
//        cs = new Customer();
//        cs.setId(2L);
//        cs.setFirstName("Ka");
//        cs.setLastName("Mak");
//        cs.setEmail("ka@mak.com");
//        cs.setPhoneNo("6665549999");
//        cs.setAge(22);
//        css.add(cs);
//
//        return css;
        return customerService.getCustomerList();
    }

    //GetById
    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id){
        return customerService.getCustomerById(id);
    }

    // localhost:8081/api/customer
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createCustomer(@Valid @RequestBody Customer body) {
        Customer customer = customerService.createCustomer(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @Valid @RequestBody Customer body) {
        body.setId(id);
        Customer customer = customerService.updateCustomer(id, body);
        return customer != null ? ResponseEntity.ok(customer) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        return customerService.deleteById(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }



}
