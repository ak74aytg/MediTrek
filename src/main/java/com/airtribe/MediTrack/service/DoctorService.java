package com.airtribe.MediTrack.service;

import com.airtribe.MediTrack.entity.Doctor;
import com.airtribe.MediTrack.entity.Specialization;
import com.airtribe.MediTrack.exception.InvalidDataException;
import com.airtribe.MediTrack.interface_.Searchable;
import com.airtribe.MediTrack.util.DataStore;
import com.airtribe.MediTrack.util.IdGenerator;
import com.airtribe.MediTrack.util.Validator;

import java.util.List;
import java.util.stream.Collectors;

public class DoctorService implements Searchable<Doctor> {

    private DataStore<Doctor> doctors;

    public DoctorService() {
        this.doctors = new DataStore<>();
    }

    public void addDoctor(Doctor doctor) throws InvalidDataException {
        Validator.validateDoctor(doctor);

        if (doctor.getId() == null || doctor.getId().isEmpty()) {
            doctor.setId(IdGenerator.getInstance().generateDoctorId());
        }
        
        doctors.add(doctor);
        System.out.println("✓ Doctor added successfully: " + doctor.getName() + " (ID: " + doctor.getId() + ")");
    }

    @Override
    public Doctor searchById(String doctorId) {
        for (Doctor doctor : doctors.getAll()) {
            if (doctor.getId().equals(doctorId)) {
                return doctor;
            }
        }
        return null;
    }

    @Override
    public List<Doctor> searchByName(String name) {
        return doctors.getAll().stream()
            .filter(d -> d.getName().toLowerCase().contains(name.toLowerCase()))
            .collect(Collectors.toList());
    }

    @Override
    public List<Doctor> searchByEmail(String email) {
        return doctors.getAll().stream()
            .filter(d -> d.getEmail().equalsIgnoreCase(email))
            .collect(Collectors.toList());
    }

    public List<Doctor> searchBySpecialization(Specialization specialization) {
        return doctors.getAll().stream()
            .filter(d -> d.getSpecialization() == specialization)
            .collect(Collectors.toList());
    }

    public List<Doctor> searchByExperience(int minYears) {
        return doctors.getAll().stream()
            .filter(d -> d.getYearsOfExperience() >= minYears)
            .collect(Collectors.toList());
    }

    public List<Doctor> searchAvailableDoctors() {
        return doctors.getAll().stream()
            .filter(Doctor::isAvailable)
            .collect(Collectors.toList());
    }

    public boolean updateDoctor(Doctor doctor) throws InvalidDataException {
        Validator.validateDoctor(doctor);
        
        Doctor existing = searchById(doctor.getId());
        if (existing == null) {
            System.out.println("✗ Doctor not found with ID: " + doctor.getId());
            return false;
        }

        existing.setName(doctor.getName());
        existing.setEmail(doctor.getEmail());
        existing.setPhone(doctor.getPhone());
        existing.setAddress(doctor.getAddress());
        existing.setSpecialization(doctor.getSpecialization());
        existing.setConsultationFee(doctor.getConsultationFee());
        existing.setYearsOfExperience(doctor.getYearsOfExperience());
        existing.setUpdatedDate(java.time.LocalDateTime.now());
        
        System.out.println("✓ Doctor updated successfully: " + doctor.getName());
        return true;
    }

    public boolean deleteDoctor(String doctorId) {
        Doctor doctor = searchById(doctorId);
        if (doctor == null) {
            System.out.println("✗ Doctor not found with ID: " + doctorId);
            return false;
        }
        
        doctors.remove(doctor);
        System.out.println("✓ Doctor deleted successfully: " + doctor.getName());
        return true;
    }

    public List<Doctor> getAllDoctors() {
        return doctors.getAll();
    }

    public int getTotalDoctors() {
        return doctors.size();
    }

    public boolean doctorExists(String doctorId) {
        return searchById(doctorId) != null;
    }

    public double getAverageConsultationFee() {
        return doctors.getAll().stream()
            .mapToDouble(Doctor::getConsultationFee)
            .average()
            .orElse(0.0);
    }

    public List<Doctor> getDoctorsSortedByFee() {
        return doctors.getAll().stream()
            .sorted((d1, d2) -> Double.compare(d2.getConsultationFee(), d1.getConsultationFee()))
            .collect(Collectors.toList());
    }

    public List<Doctor> getDoctorsSortedByExperience() {
        return doctors.getAll().stream()
            .sorted((d1, d2) -> Integer.compare(d2.getYearsOfExperience(), d1.getYearsOfExperience()))
            .collect(Collectors.toList());
    }
}
