package com.example.techcare_services;

import java.util.List;

public class ServiceCategory {
    private String categoryName;
    private int iconResId;
    private List<Service> services;

    public ServiceCategory(String categoryName, int iconResId, List<Service> services) {
        this.categoryName = categoryName;
        this.iconResId = iconResId;
        this.services = services;
    }

    public String getCategoryName() {
        return categoryName;
    }
    public int getIconResId() {
        return iconResId;
    }
    public List<Service> getServices() {
        return services;
    }
}
