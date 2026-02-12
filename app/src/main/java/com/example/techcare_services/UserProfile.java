package com.example.techcare_services;

public class UserProfile {
    private String fullName, email, phone, address;

    public UserProfile(String fullName, String email, String phone, String address) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
}
