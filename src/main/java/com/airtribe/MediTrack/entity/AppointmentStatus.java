package com.airtribe.MediTrack.entity;

public enum AppointmentStatus {
    PENDING("Pending", "Appointment is pending confirmation"),
    CONFIRMED("Confirmed", "Appointment is confirmed by doctor"),
    IN_PROGRESS("In Progress", "Appointment is currently being conducted"),
    COMPLETED("Completed", "Appointment has been completed"),
    CANCELLED("Cancelled", "Appointment has been cancelled");
    
    private final String status;
    private final String description;

    AppointmentStatus(String status, String description) {
        this.status = status;
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString() {
        return status;
    }
}
