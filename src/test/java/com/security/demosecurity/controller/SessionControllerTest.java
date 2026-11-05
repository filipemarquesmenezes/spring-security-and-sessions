package com.security.demosecurity.controller;

import com.security.demosecurity.sessions.HttpSessionManagement;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SessionController.class)
class SessionControllerTest extends AbstractControllerTest {

    @MockBean
    private HttpSessionManagement sessionManagement;

    @Test
    void whenUnauthenticated_thenRedirectsToLogin() throws Exception {
        mockMvc.perform(get("/admin/sessions"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(get("/admin/sessions/invalidate").param("session-id", "123"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(roles = "USER")
    void whenAuthenticatedAsUser_thenForbidden() throws Exception {
        mockMvc.perform(get("/admin/sessions"))
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/admin/sessions/invalidate").param("session-id", "123"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void whenAuthenticatedAsAdmin_andNoActiveSessions_thenReturnsEmptyList() throws Exception {
        when(sessionManagement.getActiveSessions()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/admin/sessions").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sessions", hasSize(0)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void whenAuthenticatedAsAdmin_andActiveSessionsExist_thenReturnsSessionIds() throws Exception {
        MockHttpSession session1 = new MockHttpSession(null, "session-abc");
        MockHttpSession session2 = new MockHttpSession(null, "session-xyz");
        when(sessionManagement.getActiveSessions()).thenReturn(Arrays.asList(session1, session2));

        mockMvc.perform(get("/admin/sessions").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sessions", hasSize(2)))
                .andExpect(jsonPath("$.sessions", hasItem("session-abc")))
                .andExpect(jsonPath("$.sessions", hasItem("session-xyz")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void whenInvalidateExistingSession_thenReturnsInvalidatedStatus() throws Exception {
        when(sessionManagement.invalidate("session-abc")).thenReturn(true);

        mockMvc.perform(get("/admin/sessions/invalidate")
                        .param("session-id", "session-abc")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("invalidated")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void whenInvalidateNonExistingSession_thenReturnsInvalidSessionIdStatus() throws Exception {
        when(sessionManagement.invalidate("session-unknown")).thenReturn(false);

        mockMvc.perform(get("/admin/sessions/invalidate")
                        .param("session-id", "session-unknown")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("invalid session id")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void whenInvalidateMissingSessionIdParam_thenReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/admin/sessions/invalidate"))
                .andExpect(status().isBadRequest());
    }
}
