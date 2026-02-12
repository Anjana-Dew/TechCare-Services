package com.example.techcare_services;

import java.time.LocalDateTime;

public class Notification {
    private int notificationId;
    private int customerId;
    private int requestId;
    private String message;
    private boolean isRead;
    private String createdAt;

    public Notification(){

    }

    public Notification(int notificationId, int customerId, int requestId,
                            String message, boolean isRead, String createdAt) {
        this.notificationId = notificationId;
        this.customerId = customerId;
        this.requestId = requestId;
        this.message = message;
        this.isRead = isRead;
        this.createdAt = createdAt;
    }

    public int getNotificationId() {

        return notificationId;
    }

    public void setNotificationId(int notificationId) {

        this.notificationId = notificationId;
    }

    public int getCustomerId() {

        return customerId;
    }

    public void setCustomerId(int customerId) {

        this.customerId = customerId;
    }

    public int getRequestId() {

        return requestId;
    }

    public void setRequestId(int requestId) {

        this.requestId = requestId;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {

        this.message = message;
    }

    public boolean isRead() {

        return isRead;
    }

    public void setRead(boolean read) {

        isRead = read;
    }

    public String getCreatedAt() {

        return createdAt;
    }

    public void setCreatedAt(String createdAt) {

        this.createdAt = createdAt;
    }
}
