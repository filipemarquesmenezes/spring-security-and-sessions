package com.security.demosecurity.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest extends AbstractControllerTest {

    @Test
    void whenUnauthenticated_thenRedirectsToLogin() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "regularuser", roles = "USER")
    void whenAuthenticatedAsUser_thenReturnsUserPrincipal() throws Exception {
        mockMvc.perform(get("/user").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.username", is("regularuser")));
    }

    @Test
    @WithMockUser(username = "adminuser", roles = "ADMIN")
    void whenAuthenticatedAsAdminOnly_thenForbidden() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().isForbidden());
    }
}
