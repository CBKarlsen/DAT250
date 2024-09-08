package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.List;

public class VoteOption {
    private String caption;
    private int presentationOrder;
    @JsonBackReference
    private Poll poll;
    private List<Vote> votes;

    public VoteOption() {

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

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }
}