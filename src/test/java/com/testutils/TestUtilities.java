package com.testutils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class TestUtilities {
    public static String asJsonString(final Object obj) {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
