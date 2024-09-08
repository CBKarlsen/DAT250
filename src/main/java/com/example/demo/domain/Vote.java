package com.example.demo.domain;

import java.time.Instant;

class Vote {
    private Instant publishedAt;
    private User Voter;
    private VoteOption vote;

    // Getters, setters, and constructor
    public Vote() {

    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public User getVoter() {
        return Voter;
    }

    public void setVoter(User voter) {
        Voter = voter;
    }

    public VoteOption getVote() {
        return vote;
    }

    public void setVote(VoteOption vote) {
        this.vote = vote;
    }
}