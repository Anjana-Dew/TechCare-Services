package com.example.techcare_services;

public class Promotion {
    private int imageRes;
    private String description;

    public Promotion(int imageRes, String description) {
        this.imageRes = imageRes;
        this.description = description;
    }

    public int getImageRes() {
        return imageRes;
    }

    public String getDescription() {
        return description;
    }
}
