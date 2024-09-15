package com.example.demo.controller;

import com.example.demo.domain.VoteOption;
import com.example.demo.manager.PollManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;

@RestController
@CrossOrigin
public class VoteOptionController {

    private final PollManager manager;

    public VoteOptionController(@Autowired PollManager manager) {
        this.manager = manager;
    };

    @GetMapping("/voteops")
    public ResponseEntity<Set<VoteOption>> getVotes() {
        return ResponseEntity.ok().body(manager.getVoteOptions());
    }

    @GetMapping("/voteops/{id}")
    public ResponseEntity<Set<VoteOption>> changeVote(@PathVariable UUID id) {
        return ResponseEntity.ok().body(manager.getVoteOptions(id));
    }
}