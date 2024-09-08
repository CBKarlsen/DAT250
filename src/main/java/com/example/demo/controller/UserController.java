package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import com.example.demo.domain.Poll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.User;
import com.example.demo.manager.PollManager;

@RestController
@RequestMapping("/v1/user")

public class UserController {

    @Autowired
    private PollManager pollManager;

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestParam String username) {
        User newUser = new User(username);
        pollManager.addPollForUser(newUser, null); // Assuming this handles new users with no polls
        return new ResponseEntity<>("User was created", HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public List<User> getAll() {
        return pollManager.getHashmap().keySet().stream().toList();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam String username) {
        boolean deleted = pollManager.getHashmap().keySet().removeIf(user -> user.getUsername().equals(username));
        if (!deleted) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok("User was deleted");
    }

    @PutMapping("/change")
    public ResponseEntity<String> changeUser(@RequestParam String username, @RequestParam String newUsername) {
        Optional<User> existingUser = pollManager.findUserByUsername(username);
        if (existingUser.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        User user = existingUser.get();
        List<Poll> polls = pollManager.getHashmap().get(user);
        pollManager.getHashmap().remove(user);
        pollManager.getHashmap().put(new User(newUsername), polls);
        return ResponseEntity.ok("User was changed");
    }
}