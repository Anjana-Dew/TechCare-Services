package com.example.techcare_services;

public class Service {

    private int serviceId;
    private String serviceName;
    private String description;
    private double price;
    private String deviceId;
    private int estimatedDuration;
    private int imageResId;

    public Service() {

    }
    public Service(int serviceId,
                   String serviceName,
                   String description,
                   double price,
                   String deviceId,
                   int estimatedDuration,
                   int imageResId) {

        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.description = description;
        this.price = price;
        this.deviceId = deviceId;
        this.estimatedDuration = estimatedDuration;
        this.imageResId = imageResId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getDeviceId() {

        return deviceId;
    }

    public int getEstimatedDuration() {

        return estimatedDuration;
    }

    public int getImageResId() {

        return imageResId;
    }

    public void setServiceId(int serviceId) {

        this.serviceId = serviceId;
    }

    public void setServiceName(String serviceName) {

        this.serviceName = serviceName;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDeviceId(String deviceId) {

        this.deviceId = deviceId;
    }

    public void setEstimatedDuration(int estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }

    public void setImageResId(int imageResId) {

        this.imageResId = imageResId;
    }
}


