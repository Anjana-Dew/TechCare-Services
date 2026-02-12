package com.example.techcare_services;

public class User {
    private int userId;
    private String email;
    private String fullName;
    private String passwordHash;
    private String role;

    // Constructor
    public User(int userId,
                String email,
                String fullName,
                String passwordHash,
                String role) {
        this.userId = userId;
        this.email = email;
        this.fullName = fullName;
        this.passwordHash = passwordHash;
        this.role = role;
    }
    public int getUserId() {

        return userId;
    }
    public String getFullName() {

        return fullName;
    }
    public String getEmail() {

        return email;
    }

    public String getPassword() {

        return passwordHash;
    }

    public String getRole() {

        return role;
    }
    public void setFullName(String fullName) {

        this.fullName = fullName;
    }
    public void setEmail(String email) {

        this.email = email;
    }

    public void setPassword(String password) {
        this
                .passwordHash = password;
    }

    public void setRole(String role) {

        this.role = role;
    }
}

