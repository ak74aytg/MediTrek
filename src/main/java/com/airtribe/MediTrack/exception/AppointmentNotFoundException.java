package com.airtribe.MediTrack.exception;

public class AppointmentNotFoundException extends Exception {

    public AppointmentNotFoundException(String appointmentId) {
        super("Appointment with ID: " + appointmentId + " not found");
    }

    public AppointmentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
