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
    public ResponseEntity<String> createPoll(@RequestParam String username, @RequestBody Poll poll){
        poll.setId(pollManager.getNextId());

        User existingUser = findUserByUsername(username);
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        List<Poll> polls = pollManager.getHashmap().get(existingUser);
        if (polls == null) {
            polls = new ArrayList<>();
        }
        polls.add(poll);
        pollManager.getHashmap().put(existingUser, polls);

        return ResponseEntity.ok("Poll was created");
    }

    private User findUserByUsername(String username) {
        return pollManager.getHashmap().keySet().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst().orElse(null);
    }

    @GetMapping("/getAll")
    public Collection<List<Poll>> getPolls(){
        return pollManager.getHashmap().values();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePoll(@RequestParam int pollId){
        Collection<List<Poll>> polls = pollManager.getHashmap().values();

        for (List<Poll> pollList : polls) {
            pollList.removeIf(poll -> poll.getId() == pollId);
        }
        return ResponseEntity.ok("Poll deleted");
    }

    @PutMapping("/change")
    public ResponseEntity<String> changePoll(@RequestParam int pollId, @RequestBody Poll changedPoll) {
        boolean pollFound = false;

        for (List<Poll> pollList : pollManager.getHashmap().values()) {
            for (Poll p : pollList) {
                if (p.getId() == pollId) {
                    updatePoll(p, changedPoll);
                    pollFound = true;
                    break;
                }
            }
            if (pollFound) {
                break;
            }
        }

        if (!pollFound) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Poll not found");
        }

        return ResponseEntity.ok("Poll changed");
    }

    private void updatePoll(Poll existingPoll, Poll changedPoll) {
        existingPoll.setCreator(changedPoll.getCreator());
        existingPoll.setOptions(changedPoll.getOptions());
        existingPoll.setQuestion(changedPoll.getQuestion());
        existingPoll.setPublishedAt(changedPoll.getPublishedAt());
        existingPoll.setValidUntil(changedPoll.getValidUntil());
    }
}