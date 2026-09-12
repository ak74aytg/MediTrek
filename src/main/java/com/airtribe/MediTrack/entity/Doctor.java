package com.airtribe.MediTrack.entity;

import com.airtribe.MediTrack.interface_.Payable;
import java.util.Objects;

public class Doctor extends Person implements Payable {

    private Specialization specialization;
    private double consultationFee;
    private int yearsOfExperience;
    private boolean isAvailable;

    public Doctor() {
        super();
        this.isAvailable = true;
    }

    public Doctor(String name, String email, Specialization specialization) {
        super(name, email);
        this.specialization = specialization;
        this.isAvailable = true;
    }

    public Doctor(String id, String name, String email, String phone,
                  Specialization specialization, double consultationFee, 
                  int yearsOfExperience) {
        super(id, name, email, phone, "");
        this.specialization = specialization;
        this.consultationFee = consultationFee;
        this.yearsOfExperience = yearsOfExperience;
        this.isAvailable = true;
    }

    public Specialization getSpecialization() {
        return specialization;
    }
    
    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }
    
    public double getConsultationFee() {
        return consultationFee;
    }
    
    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
    }
    
    public int getYearsOfExperience() {
        return yearsOfExperience;
    }
    
    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
    
    public boolean isAvailable() {
        return isAvailable;
    }
    
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public double getAmount() {
        return consultationFee;
    }
    
    @Override
    public void processPayment() {
        System.out.println("Processing payment of ₹" + consultationFee + " for Dr. " + getName());
    }

    @Override
    public String getDetails() {
        return "Dr. " + getName() + 
               " | Specialization: " + (specialization != null ? specialization.getDisplayName() : "N/A") +
               " | Experience: " + yearsOfExperience + " years" +
               " | Fee: ₹" + consultationFee +
               " | Available: " + (isAvailable ? "Yes" : "No");
    }

    @Override
    public String toString() {
        return "Doctor{" +
               "id='" + getId() + '\'' +
               ", name='" + getName() + '\'' +
               ", email='" + getEmail() + '\'' +
               ", specialization=" + (specialization != null ? specialization.getDisplayName() : "N/A") +
               ", consultationFee=" + consultationFee +
               ", yearsOfExperience=" + yearsOfExperience +
               ", isAvailable=" + isAvailable +
               '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Doctor doctor = (Doctor) o;
        return Double.compare(doctor.consultationFee, consultationFee) == 0 &&
               specialization == doctor.specialization;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), specialization, consultationFee);
    }
}
