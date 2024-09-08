package com.example.demo.controller;
import com.example.demo.domain.Vote;
import com.example.demo.manager.PollManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class VoteController {

    private final PollManager manager;

    public VoteController(@Autowired PollManager manager) {
        this.manager = manager;
    };

    @GetMapping("/votes")
    public ResponseEntity<Set<Vote>> getVotes() {
        return ResponseEntity.ok().body(manager.getVotes());
    }
}