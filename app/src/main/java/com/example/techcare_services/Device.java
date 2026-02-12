package com.example.techcare_services;

public class Device {
    private int deviceId;
    private String deviceName;
    private String brand;
    private String deviceType;
    private int imageResId;
    int serviceIconResRd;

    public int getDeviceId() {
        return deviceId;
    }

    public int getServiceIconResId() {
        return serviceIconResRd;
    }

    public void setServiceIconResId(int serviceIconResRd) {
        this.serviceIconResRd = serviceIconResRd;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
}
