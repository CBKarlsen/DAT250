package com.example.demo.controller;

import com.example.demo.manager.PollManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.domain.Poll;
import com.example.demo.domain.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/poll")
public class PollController {

    @Autowired
    private PollManager pollManager;

    @PostMapping("/create")
    public ResponseEntity<String> createPoll(@RequestParam String username, @RequestBody Poll poll) {
        Optional<User> userOptional = pollManager.findUserByUsername(username);
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        User user = userOptional.get();
        poll.setId(pollManager.getNextId());
        pollManager.addPollForUser(user, poll);
        return new ResponseEntity<>("Poll created", HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public Collection<List<Poll>> getPolls() {
        return pollManager.getAllPolls();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePoll(@RequestParam int pollId) {
        boolean deleted = pollManager.deletePoll(pollId);
        if (!deleted) {
            return new ResponseEntity<>("Poll not found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok("Poll deleted");
    }

    @PutMapping("/change")
    public ResponseEntity<String> changePoll(@RequestParam int pollId, @RequestBody Poll changedPoll) {
        boolean updated = pollManager.updatePoll(pollId, changedPoll);
        if (!updated) {
            return new ResponseEntity<>("Poll not found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok("Poll changed");
    }
}