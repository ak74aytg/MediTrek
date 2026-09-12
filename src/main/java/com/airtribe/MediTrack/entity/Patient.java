package com.airtribe.MediTrack.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Patient extends Person implements Cloneable {

    private LocalDate dateOfBirth;
    private String medicalHistory;
    private List<String> allergies;
    private String emergencyContact;
    private String bloodGroup;

    public Patient() {
        super();
        this.allergies = new ArrayList<>();
    }

    public Patient(String name, String email) {
        super(name, email);
        this.allergies = new ArrayList<>();
    }

    public Patient(String id, String name, String email, String phone, LocalDate dateOfBirth) {
        super(id, name, email, phone, "");
        this.dateOfBirth = dateOfBirth;
        this.allergies = new ArrayList<>();
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public String getMedicalHistory() {
        return medicalHistory;
    }
    
    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }
    
    public List<String> getAllergies() {
        return allergies;
    }
    
    public void setAllergies(List<String> allergies) {
        this.allergies = allergies != null ? new ArrayList<>(allergies) : new ArrayList<>();
    }
    
    public void addAllergy(String allergy) {
        if (allergy != null && !allergy.isEmpty()) {
            this.allergies.add(allergy);
        }
    }
    
    public void removeAllergy(String allergy) {
        this.allergies.remove(allergy);
    }
    
    public String getEmergencyContact() {
        return emergencyContact;
    }
    
    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }
    
    public String getBloodGroup() {
        return bloodGroup;
    }
    
    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Patient cloned = (Patient) super.clone();

        if (this.allergies != null) {
            cloned.allergies = new ArrayList<>(this.allergies);
        }
        
        return cloned;
    }

    public Patient(Patient original) {
        super(original.getId(), original.getName(), original.getEmail(), 
              original.getPhone(), original.getAddress());
        this.dateOfBirth = original.dateOfBirth;
        this.medicalHistory = original.medicalHistory;
        this.emergencyContact = original.emergencyContact;
        this.bloodGroup = original.bloodGroup;

        this.allergies = new ArrayList<>(original.allergies);
    }

    @Override
    public String getDetails() {
        return getName() + " | DOB: " + dateOfBirth + 
               " | Blood Group: " + bloodGroup +
               " | Allergies: " + (allergies.isEmpty() ? "None" : String.join(", ", allergies));
    }
    
    @Override
    public String toString() {
        return "Patient{" +
               "id='" + getId() + '\'' +
               ", name='" + getName() + '\'' +
               ", email='" + getEmail() + '\'' +
               ", dateOfBirth=" + dateOfBirth +
               ", bloodGroup='" + bloodGroup + '\'' +
               ", allergies=" + allergies +
               ", emergencyContact='" + emergencyContact + '\'' +
               '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Patient patient = (Patient) o;
        return Objects.equals(dateOfBirth, patient.dateOfBirth);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), dateOfBirth);
    }
}
