package ru.aksenov.onlineshop;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.aksenov.onlineshop.controllers.AuthController;
import ru.aksenov.onlineshop.models.User;
import ru.aksenov.onlineshop.payload.request.LoginRequest;
import ru.aksenov.onlineshop.payload.request.SignupRequest;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {
    private String API_URL = "/api/auth";

    @Mock
    private

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void signup() throws Exception {
        SignupRequest request = new SignupRequest("username", "email@gmail.com", "password123");
        String requestJson = objectMapper.writeValueAsString(request);
        mockMvc.perform(post(API_URL + "/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    void login() throws Exception{
        LoginRequest request = new LoginRequest("username", "password123");
        String requestJson = objectMapper.writeValueAsString(request);
        mockMvc.perform(post(API_URL + "/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());
    }
}
