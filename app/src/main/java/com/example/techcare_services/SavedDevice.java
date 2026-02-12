package com.example.techcare_services;

public class SavedDevice {
    private int deviceId;
    private String deviceName;
    private String brand;
    private String deviceType;
    private boolean isActive;

    public SavedDevice(int deviceId, String deviceName, String brand, String deviceType, boolean isActive) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.brand = brand;
        this.deviceType = deviceType;
        this.isActive = isActive;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getBrand() {
        return brand;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public boolean isActive() {
        return isActive;
    }

}
