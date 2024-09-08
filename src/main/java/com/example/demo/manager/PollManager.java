package com.example.demo.manager; // Make sure this matches your package name

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;

import com.example.demo.domain.User;
import com.example.demo.domain.Poll;
import lombok.getter;
import lombok.Setter;

@Component
public class PollManager {

    private HashMap<User, List<Poll>> hashmap = new HashMap<>();
    private int pollId = 0;

    public PollManager(){

    }

    public HashMap<User, List<Poll>> getHashmap() {
        return hashmap;
    }

    public int getNextId(){
        return pollId++;
    }

    public int getPollId() {
        return pollId;
    }

    public void setPollId(int pollId) {
        this.pollId = pollId;
    }
}