package com.airtribe.MediTrack.service;

import com.airtribe.MediTrack.entity.Bill;
import com.airtribe.MediTrack.entity.BillSummary;
import com.airtribe.MediTrack.exception.AppointmentNotFoundException;
import com.airtribe.MediTrack.util.DataStore;
import com.airtribe.MediTrack.util.IdGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class BillingService {

    private DataStore<Bill> bills;
    private AppointmentService appointmentService;
    private PatientService patientService;
    private DoctorService doctorService;

    public BillingService(AppointmentService appointmentService, 
                          PatientService patientService, 
                          DoctorService doctorService) {
        this.bills = new DataStore<>();
        this.appointmentService = appointmentService;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    public Bill generateBill(String appointmentId) throws AppointmentNotFoundException {

        com.airtribe.MediTrack.entity.Appointment appointment = 
            appointmentService.getAppointmentById(appointmentId);
        
        if (appointment == null) {
            throw new AppointmentNotFoundException(appointmentId);
        }

        Bill bill = new Bill();
        bill.setId(IdGenerator.getInstance().generateBillId());
        bill.setAppointmentId(appointmentId);
        bill.setPatientId(appointment.getPatientId());
        bill.setDoctorId(appointment.getDoctorId());
        bill.setConsultationFee(appointment.getConsultationFee());
        bill.setCreatedDate(LocalDateTime.now());

        bills.add(bill);
        
        System.out.println("✓ Bill generated successfully: " + bill.getId());
        System.out.println("  Amount: ₹" + String.format("%.2f", bill.getTotalAmount()));
        
        return bill;
    }

    public Bill getBillById(String billId) {
        for (Bill bill : bills.getAll()) {
            if (bill.getId().equals(billId)) {
                return bill;
            }
        }
        return null;
    }

    public List<Bill> getBillsByPatient(String patientId) {
        return bills.getAll().stream()
            .filter(b -> b.getPatientId().equals(patientId))
            .collect(Collectors.toList());
    }

    public List<Bill> getBillsByAppointment(String appointmentId) {
        return bills.getAll().stream()
            .filter(b -> b.getAppointmentId().equals(appointmentId))
            .collect(Collectors.toList());
    }

    public List<Bill> getAllBills() {
        return bills.getAll();
    }

    public List<Bill> getBillsByStatus(String status) {
        return bills.getAll().stream()
            .filter(b -> b.getPaymentStatus().equals(status))
            .collect(Collectors.toList());
    }

    public List<Bill> getPendingBills() {
        return getBillsByStatus("PENDING");
    }

    public List<Bill> getPaidBills() {
        return getBillsByStatus("PAID");
    }

    public boolean processPayment(String billId) {
        Bill bill = getBillById(billId);
        if (bill == null) {
            System.out.println("✗ Bill not found with ID: " + billId);
            return false;
        }
        
        bill.processPayment();
        return true;
    }

    public int getTotalBills() {
        return bills.size();
    }

    public double calculateTotalRevenue() {
        return getPaidBills().stream()
            .mapToDouble(Bill::getTotalAmount)
            .sum();
    }

    public double calculateAverageBillAmount() {
        return bills.getAll().stream()
            .mapToDouble(Bill::getTotalAmount)
            .average()
            .orElse(0.0);
    }

    public double calculateTotalPendingAmount() {
        return getPendingBills().stream()
            .mapToDouble(Bill::getTotalAmount)
            .sum();
    }

    public double getTotalTaxCollected() {
        return bills.getAll().stream()
            .mapToDouble(Bill::getTaxAmount)
            .sum();
    }

    public List<Bill> getBillsSortedByAmount() {
        return bills.getAll().stream()
            .sorted((b1, b2) -> Double.compare(b2.getTotalAmount(), b1.getTotalAmount()))
            .collect(Collectors.toList());
    }

    public java.util.Map<String, Double> getRevenuePerDoctor() {
        return getPaidBills().stream()
            .collect(Collectors.groupingBy(
                Bill::getDoctorId,
                Collectors.summingDouble(Bill::getTotalAmount)
            ));
    }

    public BillSummary createBillSummary(Bill bill) {
        if (bill == null) {
            return null;
        }
        
        com.airtribe.MediTrack.entity.Patient patient = 
            patientService.searchById(bill.getPatientId());
        String patientName = patient != null ? patient.getName() : "Unknown";
        
        return new BillSummary(
            bill.getId(),
            patientName,
            bill.getConsultationFee(),
            bill.getTaxAmount(),
            bill.getTotalAmount(),
            bill.getCreatedDate(),
            bill.getPaymentStatus()
        );
    }

    public BillSummary getBillSummary(String billId) {
        Bill bill = getBillById(billId);
        return bill != null ? createBillSummary(bill) : null;
    }
}
