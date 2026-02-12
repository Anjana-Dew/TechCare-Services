package com.example.techcare_services;

public class Technician extends User {
    private int technicianId;
    private String specialization;
    private String availabilityStatus;

    public Technician(int userId,
                      int technicianId,
                      String fullName,
                      String email,
                      String password,
                      String specialization,
                      String availabilityStatus) {

        super(userId, fullName, email, password, "technician");
        this.technicianId = technicianId;
        this.specialization = specialization;
        this.availabilityStatus = availabilityStatus;
    }

    public int getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(int technicianId) {
        this.technicianId = technicianId;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }
}
