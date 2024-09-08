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
    public String createUser(@RequestParam String username){
        pollManager.getHashmap().put(new User(username),null);

        return "User was created";
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


    @PutMapping("/change")// not working
    public ResponseEntity<String> changeUser(@RequestParam String oldUsername, @RequestParam String newUsername) {
        List<Poll> polls = pollManager.getHashmap().remove(new User(oldUsername));

        if (polls == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        pollManager.getHashmap().put(new User(newUsername), polls);
        return ResponseEntity.ok("User changed");
    }
}