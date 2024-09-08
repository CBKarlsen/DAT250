package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.domain.Poll;
import com.example.demo.domain.VoteOption;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PollApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testServer() throws Exception {
        ResponseEntity<String> enitity = restTemplate.getForEntity("/", String.class);
        assertEquals(HttpStatus.OK, enitity.getStatusCode());
    }

    @ParameterizedTest
    @ValueSource(strings = "eple")
    public void listUser(String username) throws Exception {
        ResponseEntity<Set> response = restTemplate.getForEntity("/users", Set.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains(username));
    }


    @Test
    public void userPollFlow() throws Exception {
        String user1 = "eple";
        String user2 = "ananas";

        // Create user1
        ResponseEntity<User> userEntity = restTemplate.postForEntity("/users/"+user1, new User(user1, "pass1", user1+"@gmail.com"), User.class);
        assertEquals(HttpStatus.CREATED, userEntity.getStatusCode());
        assertEquals(new User(user1, "pass1", user1+"@gmail.com"), userEntity.getBody());

        userEntity = restTemplate.getForEntity("/users/"+user1, User.class);
        assertEquals(HttpStatus.OK, userEntity.getStatusCode());
        assertEquals(new User(user1, "pass1", user1+"@gmail.com"), userEntity.getBody());

        // List all users, contains user1
        ResponseEntity<Set> response = restTemplate.getForEntity("/users", Set.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains(user1));

        // Create another user
        userEntity = restTemplate.postForEntity("/users/"+user2, new User(user2, "pass2", user2+"@gmail.com"), User.class);
        assertEquals(HttpStatus.CREATED, userEntity.getStatusCode());
        assertEquals(new User(user2, "pass2", user2+"@gmail.com"), userEntity.getBody());

        // List all users, contains both users
        response = restTemplate.getForEntity("/users", Set.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains(user1));
        assertTrue(response.getBody().contains(user2));

        // User1 creates a poll
        Poll poll = new Poll(
                user1,
                "Cat or Dog",
                Instant.now().plusSeconds(3600),
                true,
                new HashSet<>(Set.of(
                        new VoteOption("cat", 0),
                        new VoteOption("Dog", 1)
                ))
        );
        ResponseEntity<Poll> pollEntity = restTemplate.postForEntity("/polls", poll, Poll.class);
        //assertEquals(HttpStatus.CREATED, pollEntity.getStatusCode());
        assertEquals("eple", pollEntity.getBody().getPollCreator());


    }

}