package com.example.polls.payload;

import java.io.UnsupportedEncodingException;

public class UserSummary {
    private Long id;
    private String username;
    private String name;
    private byte[] picture;

    public UserSummary(Long id, String username, String name, String picture) throws UnsupportedEncodingException {
        this.id = id;
        this.username = username;
        this.name = name;
        byte[] bytes = picture.getBytes("UTF-8");
        this.picture = bytes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() throws UnsupportedEncodingException {
        return new String(picture, "UTF-8");
    }

    public void setPicture(String picture) throws UnsupportedEncodingException {
        byte[] bytes = picture.getBytes("UTF-8");
        this.picture = bytes;
    }
}
