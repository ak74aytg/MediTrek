package com.airtribe.MediTrack.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Appointment implements Cloneable {

    private String id;
    private String patientId;
    private String doctorId;
    private LocalDateTime appointmentTime;
    private AppointmentStatus status;
    private String notes;
    private double consultationFee;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public Appointment() {
        this.status = AppointmentStatus.PENDING;
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }

    public Appointment(String id, String patientId, String doctorId, LocalDateTime appointmentTime) {
        this();
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentTime = appointmentTime;
    }

    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getPatientId() {
        return patientId;
    }
    
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
    
    public String getDoctorId() {
        return doctorId;
    }
    
    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
    
    public LocalDateTime getAppointmentTime() {
        return appointmentTime;
    }
    
    public void setAppointmentTime(LocalDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
        this.updatedDate = LocalDateTime.now();
    }
    
    public AppointmentStatus getStatus() {
        return status;
    }
    
    public void setStatus(AppointmentStatus status) {
        this.status = status;
        this.updatedDate = LocalDateTime.now();
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public double getConsultationFee() {
        return consultationFee;
    }
    
    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
    }
    
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
    
    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(appointmentTime, that.appointmentTime);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, appointmentTime);
    }

    @Override
    public String toString() {
        return "Appointment{" +
               "id='" + id + '\'' +
               ", patientId='" + patientId + '\'' +
               ", doctorId='" + doctorId + '\'' +
               ", appointmentTime=" + appointmentTime +
               ", status=" + status +
               ", consultationFee=" + consultationFee +
               '}';
    }
}
