package com.crv.usersgroup.core.security.model;

public class AccountCredentialModel {

    private String username;
    private String password;

    public AccountCredentialModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AccountCredentialModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
