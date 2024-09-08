package com.example.demo.controller;

import com.example.demo.domain.Poll;
import com.example.demo.domain.VoteOption;
import com.example.demo.manager.PollManager;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/voteOption")
public class voteOptionController {

    @Autowired
    PollManager pollManager;

    @Operation(summary = "Change caption of a voting option")
    @PutMapping("/change")
    public ResponseEntity<String> changeVoteOption(@RequestParam int pollId, @RequestParam int presentationOrder, @RequestParam String newOption) {
        Optional<Poll> pollOptional = findPollById(pollId);
        if (pollOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Poll not found");
        }

        Poll poll = pollOptional.get();
        boolean updated = poll.getOptions().stream()
                .filter(v -> v.getPresentationOrder() == presentationOrder)
                .findFirst()
                .map(v -> {
                    v.setCaption(newOption);
                    return true;
                })
                .orElse(false);

        if (!updated) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vote option not found");
        }

        return ResponseEntity.ok("Vote option changed");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteVoteOption(@RequestParam int pollId, @RequestParam int presentationOrder) {
        Optional<Poll> pollOptional = findPollById(pollId);
        if (pollOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Poll not found");
        }

        Poll poll = pollOptional.get();
        boolean removed = poll.getOptions().removeIf(v -> v.getPresentationOrder() == presentationOrder);
        if (!removed) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vote option not found");
        }

        return ResponseEntity.ok("Vote option deleted");
    }

    @PostMapping("/create")
    public ResponseEntity<String> createVoteOption(@RequestParam int pollId, @RequestBody VoteOption voteOption) {
        Optional<Poll> pollOptional = findPollById(pollId);
        if (pollOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Poll not found");
        }

        Poll poll = pollOptional.get();
        List<VoteOption> newOptions = new ArrayList<>(poll.getOptions());
        newOptions.add(voteOption);
        poll.setOptions(newOptions);

        return ResponseEntity.ok("Vote option added");
    }

    @GetMapping("/get")
    public ResponseEntity<VoteOption> getVoteOption(@RequestParam int pollId, @RequestParam int presentationOrder) {
        return findPollById(pollId)
                .flatMap(poll -> poll.getOptions().stream()
                        .filter(v -> v.getPresentationOrder() == presentationOrder)
                        .findFirst())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private Optional<Poll> findPollById(int pollId) {
        return pollManager.getHashmap().values().stream()
                .flatMap(List::stream)
                .filter(p -> p.getId() == pollId)
                .findFirst();
    }
}