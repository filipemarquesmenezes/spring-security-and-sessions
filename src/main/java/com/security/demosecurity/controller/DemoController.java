package com.security.demosecurity.controller;

import com.security.demosecurity.sessions.HttpSessionManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class DemoController {

    @Autowired
    private HttpSessionManagement sessionManagement;

    @RequestMapping("/")
    public Map<String, Principal> principal(Principal principal) {
        return Collections.singletonMap("user", principal);
    }

    @RequestMapping("/sessions")
    public Map<String, List<String>> sessions(Principal principal) {
        return Collections.singletonMap("sessions",
                sessionManagement.getActiveSessions()
                        .stream()
                        .map(HttpSession::getId)
                        .collect(Collectors.toList())
        );
    }
}
