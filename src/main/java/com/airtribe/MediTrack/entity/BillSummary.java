package com.airtribe.MediTrack.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public final class BillSummary {

    private final String billId;
    private final String patientName;
    private final double consultationFee;
    private final double taxAmount;
    private final double totalAmount;
    private final LocalDateTime createdDate;
    private final String paymentStatus;

    public BillSummary(String billId, String patientName, double consultationFee,
                       double taxAmount, double totalAmount, LocalDateTime createdDate,
                       String paymentStatus) {

        Objects.requireNonNull(billId, "Bill ID cannot be null");
        Objects.requireNonNull(patientName, "Patient name cannot be null");
        Objects.requireNonNull(createdDate, "Created date cannot be null");
        
        if (totalAmount < 0) {
            throw new IllegalArgumentException("Total amount cannot be negative");
        }

        this.billId = billId;
        this.patientName = patientName;
        this.consultationFee = consultationFee;
        this.taxAmount = taxAmount;
        this.totalAmount = totalAmount;
        this.createdDate = createdDate;
        this.paymentStatus = paymentStatus != null ? paymentStatus : "PENDING";
    }

    public BillSummary(String billId, String patientName, double totalAmount, LocalDateTime createdDate) {
        this(billId, patientName, 0, 0, totalAmount, createdDate, "PENDING");
    }

    public String getBillId() {
        return billId;
    }
    
    public String getPatientName() {
        return patientName;
    }
    
    public double getConsultationFee() {
        return consultationFee;
    }
    
    public double getTaxAmount() {
        return taxAmount;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
    
    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getFormattedSummary() {
        return "Bill ID: " + billId + 
               "\nPatient: " + patientName +
               "\nConsultation Fee: ₹" + String.format("%.2f", consultationFee) +
               "\nTax (18%): ₹" + String.format("%.2f", taxAmount) +
               "\nTotal Amount: ₹" + String.format("%.2f", totalAmount) +
               "\nStatus: " + paymentStatus +
               "\nDate: " + createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BillSummary that = (BillSummary) o;
        return Objects.equals(billId, that.billId) &&
               Objects.equals(patientName, that.patientName) &&
               Double.compare(that.totalAmount, totalAmount) == 0;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(billId, patientName, totalAmount);
    }

    @Override
    public String toString() {
        return "BillSummary{" +
               "billId='" + billId + '\'' +
               ", patientName='" + patientName + '\'' +
               ", totalAmount=" + totalAmount +
               ", paymentStatus='" + paymentStatus + '\'' +
               ", createdDate=" + createdDate +
               '}';
    }
}
