package com.ecommerce.user.infrastructure.api.controllers;

import com.ecommerce.user.application.GetUsersByFiltersApp;
import com.ecommerce.user.application.RegisterUserApp;
import com.ecommerce.user.domain.valueobjects.UserPageDomain;
import com.ecommerce.user.domain.valueobjects.UserReqFilterDomain;
import com.ecommerce.user.infrastructure.api.hateoas.UserPageModelAssembler;
import com.ecommerce.user.infrastructure.mother.AddUserReqMother;
import com.ecommerce.user.infrastructure.mother.UserPageDomainMother;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perficient.shoppingcart.application.api.model.GetUsersPageReq;
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

@WebMvcTest(UserController.class)
class UserControllerTest {
    private final String URI = "/api/v1/user";
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private RegisterUserApp registerUserApp;

    @MockBean
    private GetUsersByFiltersApp getUsersByFiltersApp;

    @MockBean
    private UserPageModelAssembler userPageModelAssembler;

    @Test
    void createUserSuccessfully() throws Exception {
        var addCustomerReq = AddUserReqMother.random();

        mvc.perform(MockMvcRequestBuilders
            .post(URI)
            .content(mapper.writeValueAsString(addCustomerReq))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
    }

    @Test
    void createUserAddNewUserNullable() throws Exception {
        var addCustomerReq = AddUserReqMother.nullable();

        mvc.perform(MockMvcRequestBuilders
                        .post(URI)
                        .content(mapper.writeValueAsString(addCustomerReq))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUserInvalidMaxLength() throws Exception {
        var addCustomerReq = AddUserReqMother.invalidMaxLength();

        mvc.perform(MockMvcRequestBuilders
                        .post(URI)
                        .content(mapper.writeValueAsString(addCustomerReq))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUserInvalidEmail() throws Exception {
        var addCustomerReq = AddUserReqMother.invalidEmail();

        mvc.perform(MockMvcRequestBuilders
                        .post(URI)
                        .content(mapper.writeValueAsString(addCustomerReq))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getUsers() throws Exception {

        var customerPageDomainMother = UserPageDomainMother.random();
        GetUsersPageReq getCustomerPage = new GetUsersPageReq();

        when(getUsersByFiltersApp.findByFilter(any(UserReqFilterDomain.class)))
                .thenReturn(customerPageDomainMother);

        when(userPageModelAssembler.toModel(any(UserPageDomain.class))).thenReturn(getCustomerPage);

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
    void getUsersNoFound() throws Exception {

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
