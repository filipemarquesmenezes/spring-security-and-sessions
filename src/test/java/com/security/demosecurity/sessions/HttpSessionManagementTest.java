package com.security.demosecurity.sessions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HttpSessionManagementTest {

    private HttpSessionManagement sessionManagement;
    private HttpSessionListener listener;

    @BeforeEach
    void setUp() throws Exception {
        sessionManagement = new HttpSessionManagement();
        listener = sessionManagement.httpSessionListener();
        Field field = HttpSessionManagement.class.getDeclaredField("sessions");
        field.setAccessible(true);
        ((Map<?, ?>) field.get(null)).clear();
    }

    @Test
    void testSessionCreatedAndGetActiveSessions() {
        MockHttpSession mockSession = new MockHttpSession(null, "session-123");
        listener.sessionCreated(new HttpSessionEvent(mockSession));

        List<HttpSession> activeSessions = sessionManagement.getActiveSessions();
        assertEquals(1, activeSessions.size());
        assertEquals("session-123", activeSessions.get(0).getId());
    }

    @Test
    void testSessionDestroyed() {
        MockHttpSession mockSession = new MockHttpSession(null, "session-456");
        listener.sessionCreated(new HttpSessionEvent(mockSession));
        assertEquals(1, sessionManagement.getActiveSessions().size());

        listener.sessionDestroyed(new HttpSessionEvent(mockSession));
        assertEquals(0, sessionManagement.getActiveSessions().size());
    }

    @Test
    void testInvalidateExistingSession() {
        MockHttpSession mockSession = new MockHttpSession(null, "session-789");
        listener.sessionCreated(new HttpSessionEvent(mockSession));

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("user", "pass")
        );

        boolean result = sessionManagement.invalidate("session-789");

        assertTrue(result);
        assertTrue(mockSession.isInvalid());
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testInvalidateNonExistingSession() {
        boolean result = sessionManagement.invalidate("unknown-session");
        assertFalse(result);
    }
}
