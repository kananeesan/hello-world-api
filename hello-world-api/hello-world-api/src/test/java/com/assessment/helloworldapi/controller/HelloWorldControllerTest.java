package com.assessment.helloworldapi.controller;


import com.assessment.helloworldapi.exception.GlobalExceptionHandler;
import com.assessment.helloworldapi.service.impl.GreetingServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HelloWorldController.class)
@Import({
        GreetingServiceImpl.class,
        GlobalExceptionHandler.class
})
public class HelloWorldControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void lowercaseNameReturns200() throws Exception {
        mockMvc.perform(get("/hello-world")
                        .param("name", "alice"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Hello Alice"))
                .andExpect(jsonPath("$.error").doesNotExist());
    }

    @Test
    void uppercaseInitialReturns200() throws Exception {
        mockMvc.perform(get("/hello-world")
                        .param("name", "Alice"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello Alice"));
    }

    @Test
    void boundaryLetterMReturns200() throws Exception {
        mockMvc.perform(get("/hello-world")
                        .param("name", "M"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello M"));
    }

    @Test
    void boundaryLetterNReturns400() throws Exception {
        mockMvc.perform(get("/hello-world")
                        .param("name", "N"))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("Invalid Input"))
                .andExpect(jsonPath("$.message").doesNotExist());
    }

    @Test
    void missingNameReturns400() throws Exception {
        mockMvc.perform(get("/hello-world"))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("Invalid Input"))
                .andExpect(jsonPath("$.message").doesNotExist());
    }

    @Test
    void emptyNameReturns400() throws Exception {
        mockMvc.perform(get("/hello-world")
                        .param("name", ""))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Input"));
    }

    @Test
    void blankNameReturns400() throws Exception {
        mockMvc.perform(get("/hello-world")
                        .param("name", "   "))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Input"));
    }

    @Test
    void numericInitialReturns400() throws Exception {
        mockMvc.perform(get("/hello-world")
                        .param("name", "123"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Input"));
    }

    @Test
    void nonEnglishInitialReturns400() throws Exception {
        mockMvc.perform(get("/hello-world")
                        .param("name", "é Alice"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Input"));
    }

    @Test
    void surroundingWhitespaceIsRemoved() throws Exception {
        mockMvc.perform(get("/hello-world")
                        .param("name", "  alice  "))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello Alice"));
    }
}
