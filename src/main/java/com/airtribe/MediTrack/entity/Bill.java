package com.airtribe.MediTrack.entity;

import com.airtribe.MediTrack.interface_.Payable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Bill implements Payable {

    private String id;
    private String appointmentId;
    private String patientId;
    private String doctorId;
    private double consultationFee;
    private double taxAmount;
    private double totalAmount;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    private String paymentStatus;

    public Bill() {
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
        this.paymentStatus = "PENDING";
    }

    public Bill(String appointmentId, double consultationFee) {
        this();
        this.appointmentId = appointmentId;
        this.consultationFee = consultationFee;
        calculateTotalAmount(consultationFee);
    }

    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getAppointmentId() {
        return appointmentId;
    }
    
    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
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
    
    public double getConsultationFee() {
        return consultationFee;
    }
    
    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
        calculateTotalAmount(consultationFee);
    }
    
    public double getTaxAmount() {
        return taxAmount;
    }
    
    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
    
    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }
    
    public String getPaymentStatus() {
        return paymentStatus;
    }
    
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
        this.updatedDate = LocalDateTime.now();
    }

    private void calculateTotalAmount(double consultationFee) {
        com.airtribe.MediTrack.constants.Constants.class.getName();
        this.taxAmount = consultationFee * 0.18;
        this.totalAmount = consultationFee + this.taxAmount;
    }

    @Override
    public double getAmount() {
        return totalAmount;
    }
    
    @Override
    public void processPayment() {
        System.out.println("Processing payment for Bill ID: " + id);
        System.out.println("Amount: ₹" + String.format("%.2f", totalAmount));
        this.paymentStatus = "PAID";
        this.updatedDate = LocalDateTime.now();
        System.out.println("Payment processed successfully!");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bill bill = (Bill) o;
        return Objects.equals(id, bill.id) &&
               Objects.equals(appointmentId, bill.appointmentId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, appointmentId);
    }

    @Override
    public String toString() {
        return "Bill{" +
               "id='" + id + '\'' +
               ", appointmentId='" + appointmentId + '\'' +
               ", consultationFee=" + consultationFee +
               ", taxAmount=" + taxAmount +
               ", totalAmount=" + totalAmount +
               ", paymentStatus='" + paymentStatus + '\'' +
               '}';
    }
}
