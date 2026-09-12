package com.airtribe.MediTrack.service;

import com.airtribe.MediTrack.entity.Appointment;
import com.airtribe.MediTrack.entity.AppointmentStatus;
import com.airtribe.MediTrack.exception.AppointmentNotFoundException;
import com.airtribe.MediTrack.exception.InvalidDataException;
import com.airtribe.MediTrack.util.DataStore;
import com.airtribe.MediTrack.util.DateUtil;
import com.airtribe.MediTrack.util.IdGenerator;
import com.airtribe.MediTrack.util.Validator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class AppointmentService {

    private DataStore<Appointment> appointments;
    private DoctorService doctorService;
    private PatientService patientService;

    public AppointmentService(DoctorService doctorService, PatientService patientService) {
        this.appointments = new DataStore<>();
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    public Appointment createAppointment(String patientId, String doctorId, LocalDateTime appointmentTime)
            throws AppointmentNotFoundException, InvalidDataException {

        if (patientId == null || patientId.isEmpty() || doctorId == null || doctorId.isEmpty()) {
            throw new InvalidDataException("IDs", "Patient ID and Doctor ID cannot be empty");
        }

        if (!patientService.patientExists(patientId)) {
            throw new AppointmentNotFoundException("Patient with ID: " + patientId + " not found");
        }

        if (!doctorService.doctorExists(doctorId)) {
            throw new AppointmentNotFoundException("Doctor with ID: " + doctorId + " not found");
        }

        if (!DateUtil.isValidAppointmentTime(appointmentTime)) {
            throw new InvalidDataException("appointmentTime", "Appointment time must be in the future");
        }

        Appointment appointment = new Appointment();
        appointment.setId(IdGenerator.getInstance().generateAppointmentId());
        appointment.setPatientId(patientId);
        appointment.setDoctorId(doctorId);
        appointment.setAppointmentTime(appointmentTime);
        appointment.setStatus(AppointmentStatus.PENDING);
        appointment.setConsultationFee(doctorService.searchById(doctorId).getConsultationFee());
        
        appointments.add(appointment);
        System.out.println("✓ Appointment created successfully: " + appointment.getId());
        
        return appointment;
    }

    public Appointment getAppointmentById(String appointmentId) {
        for (Appointment apt : appointments.getAll()) {
            if (apt.getId().equals(appointmentId)) {
                return apt;
            }
        }
        return null;
    }

    public List<Appointment> getAppointmentsByPatient(String patientId) {
        return appointments.getAll().stream()
            .filter(a -> a.getPatientId().equals(patientId))
            .collect(Collectors.toList());
    }

    public List<Appointment> getAppointmentsByDoctor(String doctorId) {
        return appointments.getAll().stream()
            .filter(a -> a.getDoctorId().equals(doctorId))
            .collect(Collectors.toList());
    }

    public List<Appointment> getAppointmentsByStatus(AppointmentStatus status) {
        return appointments.getAll().stream()
            .filter(a -> a.getStatus() == status)
            .collect(Collectors.toList());
    }

    public List<Appointment> getPendingAppointments() {
        return getAppointmentsByStatus(AppointmentStatus.PENDING);
    }

    public List<Appointment> getConfirmedAppointments() {
        return getAppointmentsByStatus(AppointmentStatus.CONFIRMED);
    }

    public boolean confirmAppointment(String appointmentId) throws AppointmentNotFoundException {
        Appointment apt = getAppointmentById(appointmentId);
        if (apt == null) {
            throw new AppointmentNotFoundException(appointmentId);
        }
        
        if (apt.getStatus() == AppointmentStatus.PENDING) {
            apt.setStatus(AppointmentStatus.CONFIRMED);
            System.out.println("✓ Appointment confirmed: " + appointmentId);
            return true;
        } else {
            System.out.println("✗ Cannot confirm appointment - current status: " + apt.getStatus());
            return false;
        }
    }

    public boolean updateAppointmentNotes(String appointmentId, String notes) 
            throws AppointmentNotFoundException {
        Appointment apt = getAppointmentById(appointmentId);
        if (apt == null) {
            throw new AppointmentNotFoundException(appointmentId);
        }
        
        apt.setNotes(notes);
        return true;
    }

    public boolean cancelAppointment(String appointmentId) throws AppointmentNotFoundException {
        Appointment apt = getAppointmentById(appointmentId);
        if (apt == null) {
            throw new AppointmentNotFoundException(appointmentId);
        }
        
        if (apt.getStatus() == AppointmentStatus.CANCELLED) {
            System.out.println("✗ Appointment is already cancelled");
            return false;
        }
        
        if (apt.getStatus() == AppointmentStatus.COMPLETED) {
            System.out.println("✗ Cannot cancel completed appointment");
            return false;
        }
        
        apt.setStatus(AppointmentStatus.CANCELLED);
        System.out.println("✓ Appointment cancelled: " + appointmentId);
        return true;
    }

    public boolean completeAppointment(String appointmentId) throws AppointmentNotFoundException {
        Appointment apt = getAppointmentById(appointmentId);
        if (apt == null) {
            throw new AppointmentNotFoundException(appointmentId);
        }
        
        apt.setStatus(AppointmentStatus.COMPLETED);
        System.out.println("✓ Appointment completed: " + appointmentId);
        return true;
    }

    public List<Appointment> getAllAppointments() {
        return appointments.getAll();
    }

    public int getTotalAppointments() {
        return appointments.size();
    }

    public List<Appointment> getAppointmentsByDate(java.time.LocalDate date) {
        return appointments.getAll().stream()
            .filter(a -> a.getAppointmentTime().toLocalDate().equals(date))
            .collect(Collectors.toList());
    }

    public long getAppointmentCountForDoctor(String doctorId) {
        return appointments.getAll().stream()
            .filter(a -> a.getDoctorId().equals(doctorId))
            .count();
    }

    public long getAppointmentCountForPatient(String patientId) {
        return appointments.getAll().stream()
            .filter(a -> a.getPatientId().equals(patientId))
            .count();
    }
}
