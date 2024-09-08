package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Poll  {
    @JsonIgnore private UUID id;
    private String username; // username to user who created poll
    private String question;
    @JsonIgnore
    private Instant publishedAt;
    private Instant validUntil;
    private boolean isPublic;
    private Set<VoteOption> voteOptions;

    public Poll(
            @JsonProperty("username") String username,
            @JsonProperty("validUntil") Instant validUntil,
            @JsonProperty("isPublic") boolean isPublic,
            @JsonProperty("question") String question,
            @JsonProperty("voteOptions") Set<VoteOption> voteOptions
    ) {
        this.id = UUID.randomUUID(); // generates random ID
        this.username = username;
        this.question = question;
        this.publishedAt = Instant.now();
        this.validUntil = validUntil;
        this.isPublic = isPublic;
        this.voteOptions = voteOptions;
    }

    public Poll() {};

    public UUID getPollID() {
        return id;
    }

    // should not be able to change user creator
    public String getPollCreator() {
        return username;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Instant getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Instant validUntil) {
        this.validUntil = validUntil;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public Set<VoteOption> getVoteOptions() {
        return voteOptions;
    }

    public void addVoteOption(VoteOption vo) {
        voteOptions.add(vo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Poll poll = (Poll) o;
        return isPublic == poll.isPublic && Objects.equals(username, poll.username) && Objects.equals(question, poll.question) && Objects.equals(publishedAt, poll.publishedAt) && Objects.equals(validUntil, poll.validUntil) && Objects.equals(voteOptions, poll.voteOptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, question, publishedAt, validUntil, isPublic, voteOptions);
    }
}