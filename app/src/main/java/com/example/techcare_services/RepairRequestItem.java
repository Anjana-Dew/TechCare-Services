package com.example.techcare_services;

public class RepairRequestItem {

    private int requestId;
    private int customerId;
    private String customerName;
    private String serviceInfo;
    private String serviceMethod;
    private String requestedDate;

    public RepairRequestItem(int requestId,
                             int customerId,
                             String customerName,
                             String serviceInfo,
                             String serviceMethod,
                             String requestedDate) {
        this.requestId = requestId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.serviceInfo = serviceInfo;
        this.serviceMethod = serviceMethod;
        this.requestedDate = requestedDate;
    }

    public int getRequestId() {
        return requestId;
    }
    public int getCustomerId() {
        return customerId;
    }
    public String getCustomerName() {
        return customerName;
    }
    public String getServiceInfo() {
        return serviceInfo;
    }
    public String getServiceMethod() {
        return serviceMethod;
    }
    public String getRequestedDate() {
        return requestedDate;
    }
}

