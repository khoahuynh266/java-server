package com.example.javaserver.payload.response;

import java.util.List;

import java.util.List;

public class JwtResponse {
    private String accessToken;
    private String type = "Bearer";
    private String refreshToken;
    private int id;
    private String username;
    private String fullname;
    private String roles;

    public JwtResponse(String accessToken, String refreshToken, int id, String username, String fullname, String roles) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.roles = roles;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String setFullname() {
        return fullname;
    }

    public void getFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoles() {
        return roles;
    }
    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}