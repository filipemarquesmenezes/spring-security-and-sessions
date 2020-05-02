package com.security.demosecurity.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping
    public Map<String, Principal> principal(Principal principal) {
        return Collections.singletonMap("user", principal);
    }
}
