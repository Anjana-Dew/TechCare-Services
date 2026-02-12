package com.example.techcare_services;

public class Review {
    private String reviewerName;
    private String reviewText;
    float rating;

    public Review(String reviewerName, String reviewText,float rating) {
        this.reviewerName = reviewerName;
        this.reviewText = reviewText;
        this.rating= rating;

    }

    public String getReviewerName() {

        return reviewerName;
    }

    public String getReviewText() {

        return reviewText;
    }

    public float getRating() {

        return rating;
    }

}
