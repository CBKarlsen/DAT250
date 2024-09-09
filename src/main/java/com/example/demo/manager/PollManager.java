package com.example.demo.manager;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.demo.domain.User;
import com.example.demo.domain.Poll;
import com.example.demo.domain.Vote;

@Component
public class PollManager {
    private final Map<String, User> users = new HashMap<>();
    private final Map<UUID, Poll> polls = new HashMap<>();
    private final Map<UUID, User> userPolls = new HashMap<>();
    private final Map<String, Vote> pollVotes = new HashMap<>();

    public PollManager() {
    }

    // Get users as a set of usernames
    public Set<String> getUsers() {
        return users.keySet();
    }

    // Get all polls
    public Set<Poll> getPolls() {
        return Set.copyOf(polls.values());
    }

    // Get all votes
    public Set<Vote> getVotes() {
        return Set.copyOf(pollVotes.values());
    }

    // Get a user by their username
    public User getUserByUsername(String username) {
        return users.get(username);
    }

    // Get a poll by its ID
    public Poll getPollByID(UUID id) {
        return polls.get(id);
    }

    // Check if a user exists by username
    public boolean userExists(User user) {
        return user != null && users.containsKey(user.getUsername());
    }

    // Check if a poll exists by poll ID
    public boolean pollExists(Poll poll) {
        return poll != null && polls.containsKey(poll.getPollID());
    }

    // Create a new user if it doesn't exist
    public boolean createUser(User user) {
        if (userExists(user)) {
            return false; // User already exists
        }
        users.put(user.getUsername(), user);
        return true;
    }

    // Create a poll if the user exists and poll is valid
    public boolean createPoll(Poll poll, String username) {
        if (username == null || username.isEmpty() || poll == null) {
            return false; // Invalid input
        }

        User creator = getUserByUsername(username);
        if (creator == null) {
            return false; // User doesn't exist
        }

        UUID pollID = poll.getPollID();
        polls.put(pollID, poll);
        userPolls.put(pollID, creator);
        return true;
    }

    // Delete a poll by poll ID and clean up associated votes
    public boolean deletePoll(UUID pollID) {
        Poll poll = getPollByID(pollID);
        if (poll == null) {
            return false; // Poll doesn't exist
        }

        polls.remove(pollID);
        userPolls.remove(pollID);

        // Remove all votes related to the poll
        pollVotes.values().removeIf(vote -> vote.getPollID().equals(pollID));

        return true;
    }

    // Cast a vote for a poll
    public boolean castVote(Vote vote) {
        Poll poll = getPollByID(vote.getPollID());
        if (poll == null) {
            return false; // Poll doesn't exist
        }

        String voter = vote.getVoter();
        if (poll.isPublic()) {
            if (voter == null || voter.isEmpty()) {
                voter = UUID.randomUUID().toString(); // Assign anonymous voter ID
                vote.setVoter(voter);
            }
            pollVotes.put(voter, vote);
        } else {
            User userVoter = getUserByUsername(voter);
            if (userVoter == null) {
                return false; // User doesn't exist for private poll
            }
            pollVotes.put(userVoter.getUsername(), vote);
        }
        return true;
    }
}