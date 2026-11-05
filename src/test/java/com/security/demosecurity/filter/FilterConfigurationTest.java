package com.security.demosecurity.filter;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class FilterConfigurationTest {

    @Test
    void testDoFilterInternal() {
        FilterConfiguration filter = new FilterConfiguration();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        assertDoesNotThrow(() -> filter.doFilterInternal(request, response, filterChain));
    }
}
