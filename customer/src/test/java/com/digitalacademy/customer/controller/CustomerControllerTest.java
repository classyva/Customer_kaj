package com.digitalacademy.customer.controller;

import com.digitalacademy.customer.customer.CustomerSupportTest;
import com.digitalacademy.customer.model.Customer;
import com.digitalacademy.customer.repositories.CustomerRepository;
import com.digitalacademy.customer.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustomerControllerTest {
    @Mock
    private CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    private MockMvc mvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        customerController = new CustomerController(customerService);
        mvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @DisplayName("Test get all customer should return list of customer")
    @Test
    void testGetCustomerList() throws Exception {
        List<Customer> customer_list = CustomerSupportTest.getListCustomer();
        when(customerService.getCustomerList()).thenReturn(customer_list);

        MvcResult mvcResult = mvc.perform(get("/customer/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONArray jsonArray = new JSONArray(mvcResult.getResponse().getContentAsString());
//        System.out.println("-------------------------------------"+jsonArray);
//        System.out.println("*************************************"+jsonArray.getJSONObject(1));

        assertEquals(1, jsonArray.getJSONObject(0).get("id"));
        assertEquals("Ryan", jsonArray.getJSONObject(0).getString("first_name"));
        assertEquals("Giggs", jsonArray.getJSONObject(0).getString("last_name"));
        assertEquals("gique@gique.com", jsonArray.getJSONObject(0).getString("email"));
        assertEquals("66818884484", jsonArray.getJSONObject(0).getString("phoneNo"));
        assertEquals(32, jsonArray.getJSONObject(0).get("age"));

        assertEquals(2, jsonArray.getJSONObject(1).get("id"));
        assertEquals("David", jsonArray.getJSONObject(1).getString("first_name"));
        assertEquals("Beckham", jsonArray.getJSONObject(1).getString("last_name"));
        assertEquals("david@david.com", jsonArray.getJSONObject(1).getString("email"));
        assertEquals("66818884999", jsonArray.getJSONObject(1).getString("phoneNo"));
        assertEquals(45, jsonArray.getJSONObject(1).get("age"));

        verify(customerService, times(1)).getCustomerList();

    }

    @DisplayName("Test get customer by id 1 should return customer information")
    @Test
    void testGetCustomerInfoById() throws Exception {
        Long reqParam = 1L;

        Customer customer = new Customer();
        customer.setId(reqParam);
        customer.setFirst_name("Ryan1");
        customer.setLast_name("Giggs1");
        customer.setPhoneNo("66818884485");
        customer.setEmail("gique1@gique.com");
        customer.setAge(32);

        when(customerService.getCustomerById(reqParam)).thenReturn(customer);

        MvcResult mvcResult = mvc.perform(get("/customer/" + reqParam))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONObject resp = new JSONObject(mvcResult.getResponse().getContentAsString());
//        System.out.println("-------------------------------------"+resp);
//        System.out.println("*************************************"+resp.getString("id"));

        assertEquals(1, resp.get("id"));
        assertEquals("Ryan1", resp.getString("first_name"));
        assertEquals("Giggs1", resp.getString("last_name"));
        assertEquals("gique1@gique.com", resp.getString("email"));
        assertEquals("66818884485", resp.getString("phoneNo"));
        assertEquals(32, resp.get("age"));
    }

    @DisplayName("Test create customer should return success")
    @Test
    void testCreateCustomer() throws Exception{
        Customer customerReq = CustomerSupportTest.getCreateCustomer();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(customerReq);

        when(customerService.createCustomer(customerReq)).thenReturn(CustomerSupportTest.getCreatedCustomer());

        MvcResult mvcResult = mvc.perform(post("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andReturn();

        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());

        assertEquals("6",jsonObject.get("id").toString());
        assertEquals("New",jsonObject.get("first_name"));
        assertEquals("NewNew",jsonObject.get("last_name"));
        assertEquals("66818884477",jsonObject.get("phoneNo"));
        assertEquals("new@new.com",jsonObject.get("email"));
        assertEquals(10,jsonObject.get("age"));
    }

    @DisplayName("Test create customer with firstname empty") 
    @Test
    void testCreateCustomerWithNameEmpty() throws Exception{
        Customer customerReq = CustomerSupportTest.getCreateCustomer();
        customerReq.setFirst_name("");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(customerReq);

        when(customerService.createCustomer(customerReq))
                .thenReturn(CustomerSupportTest.getCreatedCustomer());

        MvcResult mvcResult = mvc.perform(post("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest())
                .andReturn();
        assertEquals("Validation failed for argument [0] in public org.springframework.http.ResponseEntity<?> " +
                "com.digitalacademy.customer.controller.CustomerController.createCustomer(com.digitalacademy.customer.model.Customer): " +
                "[Field error in object 'customer' on field 'first_name': rejected value []; " +
                "codes [Size.customer.first_name,Size.first_name,Size.java.lang.String,Size]; " +
                "arguments [org.springframework.context.support.DefaultMessageSourceResolvable: " +
                "codes [customer.first_name,first_name]; arguments []; default message [first_name],100,1]; " +
                "default message [Please type your first name size between 1 - 100]] ",mvcResult.getResolvedException().getMessage());
    }

    @DisplayName("Test update customer should return success")
    @Test
    void testUpdateCustomer() throws Exception {
        Customer customerReq = CustomerSupportTest.getBeforeUpdateCustomer();
        Long reqId = 3L;

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(customerReq);

        when(customerService.updateCustomer(reqId, customerReq)).thenReturn(CustomerSupportTest.getBeforeUpdateCustomer());
        MvcResult mvcResult = mvc.perform(put("/customer/" + reqId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        assertEquals("3",jsonObject.get("id").toString());
        assertEquals("Old",jsonObject.get("first_name"));
        assertEquals("OldOld",jsonObject.get("last_name"));
        assertEquals("66818884477",jsonObject.get("phoneNo"));
        assertEquals("old@old.com", jsonObject.get("email"));
        assertEquals(50, jsonObject.get("age"));

    }

    @DisplayName("Test update customer should return id not found")
    @Test
    void testUpdateCustomerIdNotFound() throws Exception{
        Customer customerReq = CustomerSupportTest.getBeforeUpdateCustomer();
        Long reqId = 3L;

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(customerReq);

        when(customerService.updateCustomer(reqId,customerReq))
                .thenReturn(null);

        mvc.perform(put("/customer/"+reqId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        ).andExpect(status().isNotFound()).andReturn();

        verify(customerService, times(1)).updateCustomer(reqId, customerReq);
    }

    @DisplayName("Test delete customer should success")
    @Test
    void testDeleteCustomer() throws Exception {
        Long reqId = 10L;
        when(customerService.deleteById(reqId)).thenReturn(true);

        mvc.perform(delete("/customer/"+reqId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        verify(customerService, times(1)).deleteById(reqId);
    }

    @DisplayName("Test delete customer should not found")
    @Test
    void testDeleteCustomerShouldReturnNotFound() throws Exception {
        Long reqId = 10L;
        when(customerService.deleteById(reqId)).thenReturn(false);

        mvc.perform(delete("/customer/"+reqId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        verify(customerService, times(1)).deleteById(reqId);
    }

}
