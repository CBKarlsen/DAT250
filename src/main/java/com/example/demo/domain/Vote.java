package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Vote {
    private UUID pollID;
    private String username; // user who cast a vote
    private int voteOption;
    @JsonIgnore
    private Instant publishedAt;

    public Vote(
            @JsonProperty("pollID") UUID pollID,
            @JsonProperty("username") String username,
            @JsonProperty("voteOption") int voteOption
    ) {
        this.pollID = pollID;
        this.username = username;
        this.voteOption = voteOption;
        this.publishedAt = Instant.now();
    }

    public Vote() {};

    public UUID getPollID() {
        return pollID;
    }

    //** username of user who voted **//
    public String getVoter() {
        return username;
    }

    public void setVoter(String voter) {
        this.username = voter;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public int getVoteOption() {
        return voteOption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vote vote = (Vote) o;
        return voteOption == vote.voteOption && Objects.equals(pollID, vote.pollID) && Objects.equals(username, vote.username) && Objects.equals(publishedAt, vote.publishedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pollID, username, voteOption, publishedAt);
    }
}