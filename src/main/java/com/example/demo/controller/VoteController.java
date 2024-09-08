package com.example.demo.controller;

import com.example.demo.domain.Poll;
import com.example.demo.domain.User;
import com.example.demo.domain.Vote;
import com.example.demo.domain.VoteOption;
import com.example.demo.manager.PollManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/v1/vote")
public class VoteController {

    @Autowired
    private PollManager pollManager;

    @PostMapping("/vote")
    public ResponseEntity<String> vote(@RequestParam String username, @RequestParam int pollId, @RequestParam int voteOption){
        voteOption--;

        User user = findUserByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        Poll poll = findPollById(pollId);
        if (poll == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Poll not found");
        }


        Vote vote = new Vote();
        vote.setVoter(user);
        VoteOption voteOption1 = poll.getOptions().get(voteOption);
        if (voteOption1.getVotes() != null){
            voteOption1.getVotes().add(vote);
        }
        else {
            List<Vote> votes = new ArrayList<>();
            votes.add(vote);
            voteOption1.setVotes(votes);
        }

        return ResponseEntity.ok("Vote registered");

    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteVote(@RequestParam String username, @RequestParam int pollId, @RequestParam int voteOption){
        voteOption--;

        User user = findUserByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        Poll poll = findPollById(pollId);
        if (poll == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Poll not found");
        }

        VoteOption voteOption1 = poll.getOptions().get(voteOption);
        if (voteOption1.getVotes() == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vote not found");
        }

        boolean removed = voteOption1.getVotes().removeIf(v -> Objects.equals(v.getVoter(), user));
        if (!removed) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vote not found");
        }

        return ResponseEntity.ok("Vote deleted");
    }


    @PutMapping("/change")
    public ResponseEntity<String> changeVote(@RequestParam String username, @RequestParam int pollId, @RequestParam int oldVote, @RequestParam int newVote){

        deleteVote(username, pollId, oldVote);
        vote(username, pollId, newVote);

        return ResponseEntity.ok("Vote changed");
    }


        private Poll findPollById(int pollId) {
            return pollManager.getHashmap().values().stream()
                    .flatMap(List::stream)
                    .filter(p -> p.getId() == pollId)
                    .findFirst()
                    .orElse(null);
        }

    private User findUserByUsername(String username) {
        return pollManager.getHashmap().keySet().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
}