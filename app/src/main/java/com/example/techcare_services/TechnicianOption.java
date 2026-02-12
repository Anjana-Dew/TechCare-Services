package com.example.techcare_services;

public class TechnicianOption {
    private int icon;
    private String title;
    private Class<?> targetActivity;

    public TechnicianOption(int icon, String title, Class<?> targetActivity) {
        this.icon = icon;
        this.title = title;
        this.targetActivity = targetActivity;
    }

    public int getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

    public Class<?> getTargetActivity() {
        return targetActivity;
    }
}
