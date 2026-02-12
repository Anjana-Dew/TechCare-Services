package com.example.techcare_services;

public class ServiceCard {
    private String name;
    private String description;
    private int imageResId;
    private boolean isSeeAll;
    public ServiceCard(String name, String description, int imageResId) {
        this.name = name;
        this.description = description;
        this.imageResId = imageResId;
        this.isSeeAll=false;
    }
    public ServiceCard(boolean isSeeAll) {

        this.isSeeAll = isSeeAll;
    }

    public boolean isSeeAll() {

        return isSeeAll;
    }

    public String getName() {

        return name;
    }
    public String getDescription() {

        return description;
    }
    public int getImageResId() {

        return imageResId;
    }
}
