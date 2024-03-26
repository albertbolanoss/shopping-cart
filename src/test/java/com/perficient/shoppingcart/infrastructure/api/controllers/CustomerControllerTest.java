package com.perficient.shoppingcart.infrastructure.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perficient.shoppingcart.application.api.model.AddCustomerReq;
import com.perficient.shoppingcart.infrastructure.mother.AddCustomerReqMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
//    private final String URI = "/api/v1/customer";
//    @Autowired
//    private MockMvc mvc;
//
//    @Autowired
//    private ObjectMapper mapper;
//
//
//
//    @Test
//    void createCustomerSuccessfully() throws Exception {
//        var addCustomerReq = AddCustomerReqMother.random();
//
//        mvc.perform( MockMvcRequestBuilders
//            .post(URI)
//            .content(mapper.writeValueAsString(addCustomerReq))
//            .contentType(MediaType.APPLICATION_JSON)
//            .accept(MediaType.APPLICATION_JSON))
//        .andExpect(status().isCreated());
//    }
//
//    @Test
//    void createCustomerAddNewCustomerNullable() throws Exception {
//        var addCustomerReq = AddCustomerReqMother.nullable();
//
//        mvc.perform( MockMvcRequestBuilders
//                        .post(URI)
//                        .content(mapper.writeValueAsString(addCustomerReq))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void createCustomerInvalidMaxLength() throws Exception {
//        var addCustomerReq = AddCustomerReqMother.invalidMaxLength();
//
//        mvc.perform( MockMvcRequestBuilders
//                        .post(URI)
//                        .content(mapper.writeValueAsString(addCustomerReq))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void createCustomerInvalidEmail() throws Exception {
//        var addCustomerReq = AddCustomerReqMother.invalidEmail();
//
//        mvc.perform( MockMvcRequestBuilders
//                        .post(URI)
//                        .content(mapper.writeValueAsString(addCustomerReq))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest());
//    }
}
