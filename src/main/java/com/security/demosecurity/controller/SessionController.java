package com.security.demosecurity.controller;

import com.security.demosecurity.sessions.HttpSessionManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/sessions")
public class SessionController {

    @Autowired
    private HttpSessionManagement sessionManagement;

    @RequestMapping
    public Map<String, List<String>> sessions() {
            return Collections.singletonMap("sessions",
                sessionManagement.getActiveSessions()
                        .stream()
                        .map(HttpSession::getId)
                        .collect(Collectors.toList())
        );
    }

    // TODO Currently not working properly
    @RequestMapping("/invalidate")
    public Map<String, String> invalidate(@RequestParam("session-id") String sessionId) {
        if(sessionManagement.invalidate(sessionId)) {
            return Collections.singletonMap("status", "invalidated");
        }
        return Collections.singletonMap("status", "invalid session id");
    }
}
