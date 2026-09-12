package com.airtribe.MediTrack.service;

import com.airtribe.MediTrack.entity.Patient;
import com.airtribe.MediTrack.exception.InvalidDataException;
import com.airtribe.MediTrack.interface_.Searchable;
import com.airtribe.MediTrack.util.DataStore;
import com.airtribe.MediTrack.util.DateUtil;
import com.airtribe.MediTrack.util.IdGenerator;
import com.airtribe.MediTrack.util.Validator;

import java.util.List;
import java.util.stream.Collectors;

public class PatientService implements Searchable<Patient> {

    private DataStore<Patient> patients;

    public PatientService() {
        this.patients = new DataStore<>();
    }

    public void addPatient(Patient patient) throws InvalidDataException {
        Validator.validatePatient(patient);

        if (patient.getId() == null || patient.getId().isEmpty()) {
            patient.setId(IdGenerator.getInstance().generatePatientId());
        }
        
        patients.add(patient);
        System.out.println("✓ Patient added successfully: " + patient.getName() + " (ID: " + patient.getId() + ")");
    }

    @Override
    public Patient searchById(String patientId) {
        for (Patient patient : patients.getAll()) {
            if (patient.getId().equals(patientId)) {
                return patient;
            }
        }
        return null;
    }

    @Override
    public List<Patient> searchByName(String name) {
        return patients.getAll().stream()
            .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
            .collect(Collectors.toList());
    }

    @Override
    public List<Patient> searchByEmail(String email) {
        return patients.getAll().stream()
            .filter(p -> p.getEmail().equalsIgnoreCase(email))
            .collect(Collectors.toList());
    }

    public List<Patient> searchByPhone(String phone) {
        return patients.getAll().stream()
            .filter(p -> p.getPhone() != null && p.getPhone().equals(phone))
            .collect(Collectors.toList());
    }

    public List<Patient> searchByAgeRange(int minAge, int maxAge) {
        return patients.getAll().stream()
            .filter(p -> p.getDateOfBirth() != null)
            .filter(p -> {
                int age = DateUtil.calculateAge(p.getDateOfBirth());
                return age >= minAge && age <= maxAge;
            })
            .collect(Collectors.toList());
    }

    public List<Patient> searchByBloodGroup(String bloodGroup) {
        return patients.getAll().stream()
            .filter(p -> p.getBloodGroup() != null && p.getBloodGroup().equals(bloodGroup))
            .collect(Collectors.toList());
    }

    public boolean updatePatient(Patient patient) throws InvalidDataException {
        Validator.validatePatient(patient);
        
        Patient existing = searchById(patient.getId());
        if (existing == null) {
            System.out.println("✗ Patient not found with ID: " + patient.getId());
            return false;
        }

        existing.setName(patient.getName());
        existing.setEmail(patient.getEmail());
        existing.setPhone(patient.getPhone());
        existing.setAddress(patient.getAddress());
        existing.setDateOfBirth(patient.getDateOfBirth());
        existing.setMedicalHistory(patient.getMedicalHistory());
        existing.setBloodGroup(patient.getBloodGroup());
        existing.setEmergencyContact(patient.getEmergencyContact());
        existing.setUpdatedDate(java.time.LocalDateTime.now());
        
        System.out.println("✓ Patient updated successfully: " + patient.getName());
        return true;
    }

    public boolean deletePatient(String patientId) {
        Patient patient = searchById(patientId);
        if (patient == null) {
            System.out.println("✗ Patient not found with ID: " + patientId);
            return false;
        }
        
        patients.remove(patient);
        System.out.println("✓ Patient deleted successfully: " + patient.getName());
        return true;
    }

    public List<Patient> getAllPatients() {
        return patients.getAll();
    }

    public int getTotalPatients() {
        return patients.size();
    }

    public boolean patientExists(String patientId) {
        return searchById(patientId) != null;
    }

    public boolean addAllergy(String patientId, String allergy) {
        Patient patient = searchById(patientId);
        if (patient == null) {
            return false;
        }
        patient.addAllergy(allergy);
        return true;
    }

    public List<String> getPatientAllergies(String patientId) {
        Patient patient = searchById(patientId);
        if (patient == null) {
            return java.util.Collections.emptyList();
        }
        return patient.getAllergies();
    }
}
