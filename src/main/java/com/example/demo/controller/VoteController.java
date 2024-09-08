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
    public ResponseEntity<String> vote(@RequestParam String username, @RequestParam int pollId,
                                       @RequestParam int voteOption) {

        Optional<User> userOptional = pollManager.findUserByUsername(username);
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        Optional<Poll> pollOptional = findPollById(pollId);
        if (pollOptional.isEmpty()) {
            return new ResponseEntity<>("Poll not found", HttpStatus.NOT_FOUND);
        }

        Poll poll = pollOptional.get();
        if (voteOption < 1 || voteOption > poll.getOptions().size()) {
            return new ResponseEntity<>("Invalid vote option", HttpStatus.BAD_REQUEST);
        }

        VoteOption selectedOption = poll.getOptions().get(voteOption - 1);
        Vote vote = new Vote();
        vote.setVoter(userOptional.get());

        List<Vote> votes = selectedOption.getVotes();
        if (votes == null) {
            votes = new ArrayList<>();
            selectedOption.setVotes(votes);
        }
        votes.add(vote);

        return ResponseEntity.ok("Vote was added");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteVote(@RequestParam String username, @RequestParam int pollId,
                                             @RequestParam int voteOption) {

        Optional<Poll> pollOptional = findPollById(pollId);
        if (pollOptional.isEmpty()) {
            return new ResponseEntity<>("Poll not found", HttpStatus.NOT_FOUND);
        }

        Poll poll = pollOptional.get();
        if (voteOption < 1 || voteOption > poll.getOptions().size()) {
            return new ResponseEntity<>("Invalid vote option", HttpStatus.BAD_REQUEST);
        }

        VoteOption selectedOption = poll.getOptions().get(voteOption - 1);
        List<Vote> optionVotes = selectedOption.getVotes();
        if (optionVotes == null) {
            return new ResponseEntity<>("No votes found for this option", HttpStatus.NOT_FOUND);
        }

        boolean removed = optionVotes.removeIf(v -> Objects.equals(v.getVoter().getUsername(), username));
        if (!removed) {
            return new ResponseEntity<>("Vote not found for this user and option", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok("Vote deleted");
    }

    @PutMapping("/change")
    public ResponseEntity<String> changeVote(@RequestParam String username, @RequestParam int pollId,
                                             @RequestParam int oldVote, @RequestParam int newVoteOption) {
        ResponseEntity<String> deleteResponse = deleteVote(username, pollId, oldVote);
        if (!deleteResponse.getStatusCode().is2xxSuccessful()) {
            return deleteResponse;
        }

        return vote(username, pollId, newVoteOption);
    }

    private Optional<Poll> findPollById(int pollId) {
        return pollManager.getHashmap().values().stream()
                .flatMap(List::stream)
                .filter(p -> p.getId() == pollId)
                .findFirst();
    }

    private User findUserByUsername(String username) {
        return pollManager.getHashmap().keySet().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
}