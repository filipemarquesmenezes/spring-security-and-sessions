package com.security.demosecurity;

import com.security.demosecurity.sessions.HttpSessionManagement;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.util.AssertionErrors.assertNotNull;

@SpringBootTest
class DemoSecurityApplicationTests {

	private final HttpSessionManagement sessionManagement;

	@Autowired
	DemoSecurityApplicationTests(HttpSessionManagement sessionManagement) {
		this.sessionManagement = sessionManagement;
	}

	@Test
	void contextLoads() {
		assertNotNull("sessionManagement is null", sessionManagement);
	}

}
