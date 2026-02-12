package com.example.techcare_services;

import java.util.List;

public class Customer extends User{
    private int customerId;
    private String phone;
    private String address;


    private List<Device> savedDevices;


    public Customer(int userId,
                    String fullName,
                    String phone,
                    String email,
                    String password,
                    String address,
                    List<Device> savedDevices) {

        super(userId,fullName,email, password, "customer");
            this.phone = phone;
            this.address = address;
            this.savedDevices = savedDevices;
    }

    public int getCustomerId() {

        return customerId;
    }

    public void setCustomerId(int customerId) {

        this.customerId = customerId;
    }
    public String getPhone() {

        return phone;
    }

    public void setPhone(String phone) {

        this.phone = phone;
    }

    public String getAddress() {

        return address;
    }

    public void setAddress(String address) {

        this.address = address;
    }

    public List<Device> getSavedDevices() {

        return savedDevices;
    }

    public void setSavedDevices(List<Device> savedDevices) {

        this.savedDevices = savedDevices;
    }

}
