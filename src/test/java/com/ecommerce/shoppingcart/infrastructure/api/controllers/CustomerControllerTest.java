package com.ecommerce.shoppingcart.infrastructure.api.controllers;

import com.ecommerce.shoppingcart.infrastructure.mother.CustomerPageDomainMother;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ecommerce.shoppingcart.application.GetCustomersByFiltersApp;
import com.ecommerce.shoppingcart.application.RegisterCustomerApp;
import com.perficient.shoppingcart.application.api.model.GetCustomerPageReq;
import com.ecommerce.shoppingcart.domain.valueobjects.CustomerPageDomain;
import com.ecommerce.shoppingcart.domain.valueobjects.CustomerReqFilterDomain;
import com.ecommerce.shoppingcart.infrastructure.api.hateoas.CustomerPageModelAssembler;
import com.ecommerce.shoppingcart.infrastructure.mother.AddCustomerReqMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
    private final String URI = "/api/v1/customer";
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private RegisterCustomerApp registerCustomerService;

    @MockBean
    private GetCustomersByFiltersApp getCustomersByFiltersService;

    @MockBean
    private CustomerPageModelAssembler customerPageModelAssembler;

    @Test
    void createCustomerSuccessfully() throws Exception {
        var addCustomerReq = AddCustomerReqMother.random();

        mvc.perform(MockMvcRequestBuilders
            .post(URI)
            .content(mapper.writeValueAsString(addCustomerReq))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
    }

    @Test
    void createCustomerAddNewCustomerNullable() throws Exception {
        var addCustomerReq = AddCustomerReqMother.nullable();

        mvc.perform(MockMvcRequestBuilders
                        .post(URI)
                        .content(mapper.writeValueAsString(addCustomerReq))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createCustomerInvalidMaxLength() throws Exception {
        var addCustomerReq = AddCustomerReqMother.invalidMaxLength();

        mvc.perform(MockMvcRequestBuilders
                        .post(URI)
                        .content(mapper.writeValueAsString(addCustomerReq))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createCustomerInvalidEmail() throws Exception {
        var addCustomerReq = AddCustomerReqMother.invalidEmail();

        mvc.perform(MockMvcRequestBuilders
                        .post(URI)
                        .content(mapper.writeValueAsString(addCustomerReq))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getCustomers() throws Exception {

        var customerPageDomainMother = CustomerPageDomainMother.random();
        GetCustomerPageReq getCustomerPage = new GetCustomerPageReq();

        when(getCustomersByFiltersService.findByFilter(any(CustomerReqFilterDomain.class)))
                .thenReturn(customerPageDomainMother);

        when(customerPageModelAssembler.toModel(any(CustomerPageDomain.class))).thenReturn(getCustomerPage);

        mvc.perform(MockMvcRequestBuilders
                    .get(URI)
                    .param("offset", "0")
                    .param("limit", "10")
                    .param("firstName", "John")
                    .param("lastName", "Doe")
                    .param("email", "john.doe@example.com")
                    .param("sort", "field1,field2")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getCustomersNoFound() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .get(URI)
                        .param("offset", "0")
                        .param("limit", "10")
                        .param("firstName", "John")
                        .param("lastName", "Doe")
                        .param("email", "john.doe@example.com")
                        .param("sort", "field1,field2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
