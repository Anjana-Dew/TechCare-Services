package com.example.techcare_services;

public class AcceptedRepairItem {

    private int requestId;
    private String customerName;
    private String serviceName;
    private String createdAt;
    private String status;

    public AcceptedRepairItem(
            int requestId,
            String customerName,
            String serviceName,
            String createdAt,
            String status
    ) {
        this.requestId = requestId;
        this.customerName = customerName;
        this.serviceName = serviceName;
        this.createdAt = createdAt;
        this.status = status;
    }

    public int getRequestId() {
        return requestId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
