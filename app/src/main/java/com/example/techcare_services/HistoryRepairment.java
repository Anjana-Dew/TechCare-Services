package com.example.techcare_services;

public class HistoryRepairment {

    private int requestId;
    private String customerName;
    private String serviceInfo;
    private String completedDate;

    public HistoryRepairment(
            int requestId,
            String customerName,
            String serviceInfo,
            String completedDate) {
        this.requestId = requestId;
        this.customerName = customerName;
        this.serviceInfo = serviceInfo;
        this.completedDate = completedDate;
    }

    public int getRequestId() {
        return requestId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getServiceInfo() {
        return serviceInfo;
    }

    public String getCompletedDate() {
        return completedDate;
    }
}
