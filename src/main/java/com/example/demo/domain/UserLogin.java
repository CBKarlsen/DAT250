package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserLogin {
    private  String username;
    private  String password;

    public UserLogin(
            @JsonProperty String username,
            @JsonProperty String password)
    {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }

}