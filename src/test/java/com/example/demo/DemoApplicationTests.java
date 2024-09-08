package com.example.demo;

import com.example.demo.domain.User;
import com.example.demo.domain.Poll;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DemoApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;  // For serializing objects to JSON

	// Test if the server responds correctly to the root endpoint "/"
	@Test
	public void testServer() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Server is running"))); // Adjust based on actual content
	}

	// Parameterized test for listing users
	@ParameterizedTest
	@ValueSource(strings = "user1")
	public void listUser(String username) throws Exception {
		mockMvc.perform(get("/users"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[*]", hasItem(username))); // Expects the username to be in the list
	}

	// Testing the full user poll flow
	@Test
	public void userPollFlow() throws Exception {
		String user1 = "user1";
		String user2 = "user2";

		// Create user1
		User user1Obj = new User(user1, "pass1", user1 + "@gmail.com");
		mockMvc.perform(post("/users/" + user1)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(user1Obj)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.username", is(user1)))
				.andExpect(jsonPath("$.email", is(user1 + "@gmail.com")));

		// Get user1
		mockMvc.perform(get("/users/" + user1))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.username", is(user1)))
				.andExpect(jsonPath("$.email", is(user1 + "@gmail.com")));

		// List all users, should contain user1
		mockMvc.perform(get("/users"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[*].username", hasItem(user1)));

		// Create user2
		User user2Obj = new User(user2, "pass2", user2 + "@gmail.com");
		mockMvc.perform(post("/users/" + user2)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(user2Obj)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.username", is(user2)))
				.andExpect(jsonPath("$.email", is(user2 + "@gmail.com")));

		// List all users, should contain both user1 and user2
		mockMvc.perform(get("/users"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[*].username", hasItems(user1, user2)));
	}
}