package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VoteOption {
    @JsonIgnore
    private Set<Vote> votes;
    private String caption;
    private int presentationOrder;

    public VoteOption(
            @JsonProperty("caption") String caption,
            @JsonProperty("orderOfPresentation") int presentationOrder
    ) {
        this.votes = new HashSet<>();
        this.caption = caption;
        this.presentationOrder = presentationOrder;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getPresentationOrder() {
        return presentationOrder;
    }

    public void setPresentationOrder(int presentationOrder) {
        this.presentationOrder = presentationOrder;
    }
}