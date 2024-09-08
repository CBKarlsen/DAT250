package com.example.demo.controller;

import com.example.demo.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserControllerTest {

    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        restTemplate = new RestTemplateBuilder().build();
    }

    private final String baseUrl = "http://localhost:8080/v1/user";

    @Test
    public void testCreateUser() {
        String url = baseUrl + "/create?username=user1";

        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User was created", response.getBody());
    }

    @Test
    public void testGetAllUsers() {
        String url = baseUrl + "/getAll";

        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testDeleteUser() {
        String createUserUrl = baseUrl + "/create?username=userToDelete";
        restTemplate.postForEntity(createUserUrl, null, String.class);

        String deleteUrl = baseUrl + "/delete?username=userToDelete";

        ResponseEntity<String> response = restTemplate.exchange(deleteUrl, HttpMethod.DELETE, null, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User deleted", response.getBody());
    }

    @Test
    public void testChangeUser() {
        String createUserUrl = baseUrl + "/create?username=oldUser";
        restTemplate.postForEntity(createUserUrl, null, String.class);

        String changeUrl = baseUrl + "/change?oldUsername=oldUser&newUsername=newUser";

        ResponseEntity<String> response = restTemplate.exchange(changeUrl, HttpMethod.PUT, null, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User changed", response.getBody());
    }
}