package com.airtribe.MediTrack.util;

import com.airtribe.MediTrack.entity.Doctor;
import com.airtribe.MediTrack.entity.Patient;
import com.airtribe.MediTrack.entity.Appointment;
import com.airtribe.MediTrack.exception.InvalidDataException;
import com.airtribe.MediTrack.constants.Constants;

public class Validator {

    private Validator() {
        throw new AssertionError("Cannot instantiate Validator class");
    }

    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            return false;
        }
        return phone.matches("\\d{10}");
    }

    public static boolean isValidName(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }
        return name.length() >= 2 && name.length() <= 100;
    }

    public static boolean isValidConsultationFee(double fee) {
        return fee >= Constants.CONSULTATION_FEE_MIN && 
               fee <= Constants.CONSULTATION_FEE_MAX;
    }

    public static void validateDoctor(Doctor doctor) throws InvalidDataException {
        if (doctor == null) {
            throw new InvalidDataException("doctor", "Doctor object cannot be null");
        }
        
        if (!isValidName(doctor.getName())) {
            throw new InvalidDataException("name", "Name must be between 2-100 characters");
        }
        
        if (!isValidEmail(doctor.getEmail())) {
            throw new InvalidDataException("email", "Invalid email format");
        }
        
        if (doctor.getPhone() != null && !doctor.getPhone().isEmpty() && 
            !isValidPhone(doctor.getPhone())) {
            throw new InvalidDataException("phone", "Phone must be 10 digits");
        }
        
        if (!isValidConsultationFee(doctor.getConsultationFee())) {
            throw new InvalidDataException("consultationFee", 
                "Fee must be between ₹" + Constants.CONSULTATION_FEE_MIN + 
                " and ₹" + Constants.CONSULTATION_FEE_MAX);
        }
        
        if (doctor.getYearsOfExperience() < 0 || doctor.getYearsOfExperience() > 70) {
            throw new InvalidDataException("yearsOfExperience", 
                "Years of experience must be between 0 and 70");
        }
    }

    public static void validatePatient(Patient patient) throws InvalidDataException {
        if (patient == null) {
            throw new InvalidDataException("patient", "Patient object cannot be null");
        }
        
        if (!isValidName(patient.getName())) {
            throw new InvalidDataException("name", "Name must be between 2-100 characters");
        }
        
        if (!isValidEmail(patient.getEmail())) {
            throw new InvalidDataException("email", "Invalid email format");
        }
        
        if (patient.getPhone() != null && !patient.getPhone().isEmpty() && 
            !isValidPhone(patient.getPhone())) {
            throw new InvalidDataException("phone", "Phone must be 10 digits");
        }
        
        if (patient.getDateOfBirth() != null && 
            patient.getDateOfBirth().isAfter(java.time.LocalDate.now())) {
            throw new InvalidDataException("dateOfBirth", 
                "Date of birth cannot be in the future");
        }
    }

    public static void validateAppointment(Appointment appointment) throws InvalidDataException {
        if (appointment == null) {
            throw new InvalidDataException("appointment", "Appointment object cannot be null");
        }
        
        if (appointment.getPatientId() == null || appointment.getPatientId().isEmpty()) {
            throw new InvalidDataException("patientId", "Patient ID cannot be empty");
        }
        
        if (appointment.getDoctorId() == null || appointment.getDoctorId().isEmpty()) {
            throw new InvalidDataException("doctorId", "Doctor ID cannot be empty");
        }
        
        if (appointment.getAppointmentTime() == null) {
            throw new InvalidDataException("appointmentTime", "Appointment time cannot be null");
        }
        
        if (appointment.getAppointmentTime().isBefore(java.time.LocalDateTime.now())) {
            throw new InvalidDataException("appointmentTime", 
                "Appointment time cannot be in the past");
        }
    }
}
