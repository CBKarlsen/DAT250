package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.*;

public class Poll  {
    @JsonIgnore private UUID id;
    private String username; // username to user who created poll
    private String question;
    private Instant publishedAt;
    private Instant validUntil;
    private boolean isPublic;
    private Set<VoteOption> voteOptions;

    public Poll(
            @JsonProperty("username") String username,
            @JsonProperty("question") String question,
            @JsonProperty("validUntil") Instant validUntil,
            @JsonProperty("isPublic") boolean isPublic,
            @JsonProperty("voteOptions") Set<VoteOption> voteOptions
    ) {
        this.id = UUID.randomUUID(); // random unique ID
        this.username = username;
        this.question = question;
        this.publishedAt = Instant.now();
        this.validUntil = validUntil;
        this.isPublic = isPublic;
        this.voteOptions = voteOptions != null ? voteOptions : new HashSet<>();
    }


    public UUID getPollID() {
        return id;
    }

    public String getPollCreator() {
        return username;
    }



    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }



    public boolean isPublic() {
        return isPublic;
    }



    public Set<VoteOption> getVoteOptions() {
        return voteOptions;
    }

    public VoteOption getVoteOption(int index) {
        for (VoteOption voteOption : voteOptions) {
            if (voteOption.getPresentationOrder() == index) {
                return voteOption;
            }
        }
        return null;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Poll poll = (Poll) o;
        return isPublic == poll.isPublic && Objects.equals(id, poll.id) && Objects.equals(username, poll.username) && Objects.equals(question, poll.question) && Objects.equals(publishedAt, poll.publishedAt) && Objects.equals(validUntil, poll.validUntil) && Objects.equals(voteOptions, poll.voteOptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, question, publishedAt, validUntil, isPublic, voteOptions);
    }
}