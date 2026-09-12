package com.airtribe.MediTrack.util;

public class IdGenerator {

    private static final IdGenerator INSTANCE = new IdGenerator();

    private static int patientCounter = 1000;
    private static int doctorCounter = 2000;
    private static int appointmentCounter = 3000;
    private static int billCounter = 4000;

    private IdGenerator() {

    }

    public static IdGenerator getInstance() {
        return INSTANCE;
    }

    public synchronized String generatePatientId() {
        return "PAT_" + (++patientCounter);
    }

    public synchronized String generateDoctorId() {
        return "DOC_" + (++doctorCounter);
    }

    public synchronized String generateAppointmentId() {
        return "APT_" + (++appointmentCounter);
    }

    public synchronized String generateBillId() {
        return "BILL_" + (++billCounter);
    }

    public synchronized int getPatientCounter() {
        return patientCounter;
    }

    public synchronized int getDoctorCounter() {
        return doctorCounter;
    }

    public synchronized int getAppointmentCounter() {
        return appointmentCounter;
    }

    public synchronized int getBillCounter() {
        return billCounter;
    }

    public synchronized void resetCounters() {
        patientCounter = 1000;
        doctorCounter = 2000;
        appointmentCounter = 3000;
        billCounter = 4000;
    }
}
