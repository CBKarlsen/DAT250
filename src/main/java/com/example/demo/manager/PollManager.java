package com.example.demo.manager;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import com.example.demo.domain.User;
import com.example.demo.domain.Poll;

@Component
public class PollManager {

    private final Map<User, List<Poll>> userPollsMap = new HashMap<>();
    private int pollId = 0;

    public int getNextId() {
        return pollId++;
    }

    public boolean deletePoll(int pollId) {
        return userPollsMap.values().stream()
                .anyMatch(polls -> polls.removeIf(poll -> poll.getId() == pollId));
    }

    public Collection<List<Poll>> getAllPolls() {
        return userPollsMap.values();
    }

    public Optional<User> findUserByUsername(String username) {
        return userPollsMap.keySet().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    public void addPollForUser(User user, Poll poll) {
        userPollsMap.compute(user, (u, polls) -> {
            if (polls == null) {
                return new ArrayList<>(List.of(poll));
            } else {
                List<Poll> newPolls = new ArrayList<>(polls);
                newPolls.add(poll);
                return newPolls;
            }
        });
    }

    public boolean updatePoll(int pollId, Poll updatedPoll) {
        return userPollsMap.values().stream()
                .flatMap(Collection::stream)
                .filter(poll -> poll.getId() == pollId)
                .findFirst()
                .map(poll -> {
                    poll.setCreator(updatedPoll.getCreator());
                    poll.setOptions(updatedPoll.getOptions());
                    poll.setQuestion(updatedPoll.getQuestion());
                    poll.setPublishedAt(updatedPoll.getPublishedAt());
                    poll.setValidUntil(updatedPoll.getValidUntil());
                    return true;
                })
                .orElse(false);
    }


    public Map<User, List<Poll>> getHashmap() {
        return userPollsMap;
    }
}