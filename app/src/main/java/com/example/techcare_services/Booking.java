package com.example.techcare_services;

import java.time.LocalDateTime;

public class Booking {
    private long bookingId;
    private long requestId;
    private String method;
    private String location;
    private String dateTime;
    private String createdAt;
    private String serviceName;
    public Booking(){

    }
    public Booking(long bookingId, long requestId, String method,
                   String location, String dateTime, String createdAt, String serviceName) {
        this.bookingId = bookingId;
        this.requestId = requestId;
        this.method = method;
        this.location = location;
        this.dateTime = dateTime;
        this.createdAt = createdAt;
        this.serviceName = serviceName;
    }

    public long getBookingId() {
        return bookingId;
    }

    public void setBookingId(long bookingId) {
        this.bookingId = bookingId;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public String getMethod() {

        return method;
    }

    public void setMethod(String method) {

        this.method = method;
    }

    public String getLocation() {

        return location;
    }

    public void setLocation(String location) {

        this.location = location;
    }

    public String getDateTime() {

        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getCreatedAt() {

        return createdAt;
    }

    public void setCreatedAt(String createdAt) {

        this.createdAt = createdAt;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
