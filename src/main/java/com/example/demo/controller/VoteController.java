package com.example.demo.controller;
import com.example.demo.domain.Vote;
import com.example.demo.manager.PollManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Set;

@RestController
@CrossOrigin
public class VoteController {

    private final PollManager manager;

    public VoteController(@Autowired PollManager manager) {
        this.manager = manager;
    };

    @GetMapping("/votes")
    public ResponseEntity<Set<Vote>> getVotes() {
        return ResponseEntity.ok().body(manager.getVotes());
    }

    @PostMapping("/votes")
    public ResponseEntity<Vote> castVote(@RequestBody Vote vote) {
        if (manager.castVote(vote)) {
            return ResponseEntity.created(URI.create("/"+vote.getVoter()+"/"+vote.getPollID())).body(vote);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/votes/{id}/{username}")
    public ResponseEntity<Vote> changeVote(@PathVariable String id, @PathVariable String username, @RequestBody Vote newVote) {
        if (manager.castVote(newVote)) {
            return ResponseEntity.ok().body(newVote);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}