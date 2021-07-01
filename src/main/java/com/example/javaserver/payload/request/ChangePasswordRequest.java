package com.example.javaserver.payload.request;

import javax.validation.constraints.NotBlank;

public class ChangePasswordRequest {
    @NotBlank
    private String newPassword;

    @NotBlank
    private String oldPassword;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private  String token;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }


    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }



    }

