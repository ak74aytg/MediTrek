package com.airtribe;

import com.airtribe.MediTrack.entity.*;
import com.airtribe.MediTrack.exception.AppointmentNotFoundException;
import com.airtribe.MediTrack.exception.InvalidDataException;
import com.airtribe.MediTrack.service.AppointmentService;
import com.airtribe.MediTrack.service.BillingService;
import com.airtribe.MediTrack.service.DoctorService;
import com.airtribe.MediTrack.service.PatientService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner scanner;
    private static DoctorService doctorService;
    private static PatientService patientService;
    private static AppointmentService appointmentService;
    private static BillingService billingService;

    public static void main(String[] args) {
        initializeServices();
        
        displayWelcome();
        mainMenu();
        
        System.out.println(" Thank you for using MediTrack !!!");
    }

    private static void initializeServices() {
        scanner = new Scanner(System.in);
        doctorService = new DoctorService();
        patientService = new PatientService();
        appointmentService = new AppointmentService(doctorService, patientService);
        billingService = new BillingService(appointmentService, patientService, doctorService);
        loadSampleData();
    }

    private static void loadSampleData() {
        try {

            Doctor doc1 = new Doctor("Dr. Rajesh", "rajesh@hospital.com", Specialization.CARDIOLOGY);
            doc1.setConsultationFee(1000);
            doc1.setYearsOfExperience(15);
            doc1.setPhone("9876543210");
            doctorService.addDoctor(doc1);
            
            Doctor doc2 = new Doctor("Dr. Priya", "priya@hospital.com", Specialization.NEUROLOGY);
            doc2.setConsultationFee(800);
            doc2.setYearsOfExperience(12);
            doc2.setPhone("9876543211");
            doctorService.addDoctor(doc2);

            Patient pat1 = new Patient("Amit", "amit@gmail.com");
            pat1.setDateOfBirth(LocalDate.of(1990, 5, 15));
            pat1.setBloodGroup("O+");
            pat1.setPhone("9123456789");
            pat1.addAllergy("Penicillin");
            patientService.addPatient(pat1);
            
            Patient pat2 = new Patient("Neha ", "neha@gmail.com");
            pat2.setDateOfBirth(LocalDate.of(1995, 8, 20));
            pat2.setBloodGroup("B+");
            pat2.setPhone("9123456790");
            patientService.addPatient(pat2);
            
            System.out.println("Sample data loaded successfully!\n");
        } catch (InvalidDataException e) {
            System.out.println("Error loading sample data: " + e.getMessage() + "\n");
        }
    }

    private static void displayWelcome() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("     Welcome to MediTrack - Healthcare Management System");
        System.out.println("=".repeat(60) + "\n");
    }

    private static void mainMenu() {
        boolean running = true;
        
        while (running) {
            System.out.println("\n" + "-".repeat(50));
            System.out.println("MAIN MENU");
            System.out.println("-".repeat(50));
            System.out.println("1. Doctor Management");
            System.out.println("2. Patient Management");
            System.out.println("3. Appointment Management");
            System.out.println("4. Billing Management");
            System.out.println("5. Reports & Analytics");
            System.out.println("6. Exit");
            System.out.print("\nEnter your choice (1-6): ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1:
                        doctorMenu();
                        break;
                    case 2:
                        patientMenu();
                        break;
                    case 3:
                        appointmentMenu();
                        break;
                    case 4:
                        billingMenu();
                        break;
                    case 5:
                        analyticsMenu();
                        break;
                    case 6:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice! Please enter 1-6.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }

    private static void doctorMenu() {
        boolean inMenu = true;
        
        while (inMenu) {
            System.out.println("\n" + "-".repeat(50));
            System.out.println("DOCTOR MANAGEMENT");
            System.out.println("-".repeat(50));
            System.out.println("1. Add Doctor");
            System.out.println("2. View All Doctors");
            System.out.println("3. Search Doctor by ID");
            System.out.println("4. Search Doctor by Specialization");
            System.out.println("5. Update Doctor");
            System.out.println("6. Delete Doctor");
            System.out.println("7. Back to Main Menu");
            System.out.print("\nEnter your choice (1-7): ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1:
                        addDoctor();
                        break;
                    case 2:
                        viewAllDoctors();
                        break;
                    case 3:
                        searchDoctorById();
                        break;
                    case 4:
                        searchDoctorBySpecialization();
                        break;
                    case 5:
                        updateDoctor();
                        break;
                    case 6:
                        deleteDoctor();
                        break;
                    case 7:
                        inMenu = false;
                        break;
                    default:
                        System.out.println(" Invalid choice!");
                }
            } catch (NumberFormatException e) {
                System.out.println(" Invalid input!");
            }
        }
    }
    
    private static void addDoctor() {
        try {
            System.out.println("\n--- Add New Doctor ---");
            System.out.print("Enter name: ");
            String name = scanner.nextLine();
            
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            
            System.out.print("Enter phone (10 digits): ");
            String phone = scanner.nextLine();
            
            System.out.println("\nSpecializations:");
            int idx = 1;
            for (Specialization spec : Specialization.values()) {
                System.out.println(idx + ". " + spec.getDisplayName());
                idx++;
            }
            System.out.print("Select specialization (1-" + Specialization.values().length + "): ");
            int specChoice = Integer.parseInt(scanner.nextLine());
            Specialization spec = Specialization.values()[specChoice - 1];
            
            System.out.print("Enter consultation fee (₹): ");
            double fee = Double.parseDouble(scanner.nextLine());
            
            System.out.print("Enter years of experience: ");
            int years = Integer.parseInt(scanner.nextLine());
            
            Doctor doctor = new Doctor(name, email, spec);
            doctor.setPhone(phone);
            doctor.setConsultationFee(fee);
            doctor.setYearsOfExperience(years);
            
            doctorService.addDoctor(doctor);
        } catch (InvalidDataException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Invalid input!");
        }
    }
    
    private static void viewAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        if (doctors.isEmpty()) {
            System.out.println("\nNo doctors found!");
        } else {
            System.out.println("\n" + "=".repeat(100));
            System.out.println("DOCTORS LIST");
            System.out.println("=".repeat(100));
            for (Doctor doc : doctors) {
                System.out.println(doc.getDetails());
                System.out.println("-".repeat(100));
            }
        }
    }
    
    private static void searchDoctorById() {
        System.out.print("\nEnter doctor ID: ");
        String doctorId = scanner.nextLine();
        
        Doctor doctor = doctorService.searchById(doctorId);
        if (doctor == null) {
            System.out.println("Doctor not found!");
        } else {
            System.out.println("\n" + doctor.getDetails());
        }
    }
    
    private static void searchDoctorBySpecialization() {
        try {
            System.out.println("\nSpecializations:");
            int idx = 1;
            for (Specialization spec : Specialization.values()) {
                System.out.println(idx + ". " + spec.getDisplayName());
                idx++;
            }
            System.out.print("Select specialization: ");
            int choice = Integer.parseInt(scanner.nextLine());
            Specialization spec = Specialization.values()[choice - 1];
            
            List<Doctor> doctors = doctorService.searchBySpecialization(spec);
            if (doctors.isEmpty()) {
                System.out.println("No doctors found for " + spec.getDisplayName());
            } else {
                System.out.println("\n" + "=".repeat(100));
                System.out.println("DOCTORS - " + spec.getDisplayName());
                System.out.println("=".repeat(100));
                for (Doctor doc : doctors) {
                    System.out.println(doc.getDetails());
                    System.out.println("-".repeat(100));
                }
            }
        } catch (Exception e) {
            System.out.println("Invalid input!");
        }
    }
    
    private static void updateDoctor() {
        try {
            System.out.print("\nEnter doctor ID to update: ");
            String doctorId = scanner.nextLine();
            
            Doctor doctor = doctorService.searchById(doctorId);
            if (doctor == null) {
                System.out.println("Doctor not found!");
                return;
            }
            
            System.out.print("Enter new consultation fee (current: " + doctor.getConsultationFee() + "): ");
            double fee = Double.parseDouble(scanner.nextLine());
            doctor.setConsultationFee(fee);
            
            doctorService.updateDoctor(doctor);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private static void deleteDoctor() {
        System.out.print("\nEnter doctor ID to delete: ");
        String doctorId = scanner.nextLine();
        doctorService.deleteDoctor(doctorId);
    }

    private static void patientMenu() {
        boolean inMenu = true;
        
        while (inMenu) {
            System.out.println("\n" + "-".repeat(50));
            System.out.println("PATIENT MANAGEMENT");
            System.out.println("-".repeat(50));
            System.out.println("1. Add Patient");
            System.out.println("2. View All Patients");
            System.out.println("3. Search Patient by ID");
            System.out.println("4. Search Patient by Name");
            System.out.println("5. Update Patient");
            System.out.println("6. Delete Patient");
            System.out.println("7. Add Allergy to Patient");
            System.out.println("8. Back to Main Menu");
            System.out.print("\nEnter your choice (1-8): ");


            try {
                int choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1:
                        addPatient();
                        break;
                    case 2:
                        viewAllPatients();
                        break;
                    case 3:
                        searchPatientById();
                        break;
                    case 4:
                        searchPatientByName();
                        break;
                    case 5:
                        updatePatient();
                        break;
                    case 6:
                        deletePatient();
                        break;
                    case 7:
                        addAllergyToPatient();
                        break;
                    case 8:
                        inMenu = false;
                        break;
                    default:
                        System.out.println(" Invalid choice!");
                }
            } catch (NumberFormatException e) {
                System.out.println(" Invalid input!");
            }
        }
    }
    
    private static void addPatient() {
        try {
            System.out.println("\n--- Add New Patient ---");
            System.out.print("Enter name: ");
            String name = scanner.nextLine();
            
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            
            System.out.print("Enter phone (10 digits): ");
            String phone = scanner.nextLine();
            
            System.out.print("Enter date of birth (yyyy-MM-dd): ");
            LocalDate dob = LocalDate.parse(scanner.nextLine());
            
            System.out.print("Enter blood group: ");
            String bloodGroup = scanner.nextLine();
            
            Patient patient = new Patient(name, email);
            patient.setPhone(phone);
            patient.setDateOfBirth(dob);
            patient.setBloodGroup(bloodGroup);
            
            patientService.addPatient(patient);
        } catch (InvalidDataException e) {
            System.out.println(" Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println(" Invalid input!");
        }
    }
    
    private static void viewAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        if (patients.isEmpty()) {
            System.out.println("\n✗ No patients found!");
        } else {
            System.out.println("\n" + "=".repeat(100));
            System.out.println("PATIENTS LIST");
            System.out.println("=".repeat(100));
            for (Patient pat : patients) {
                System.out.println(pat.getDetails());
                System.out.println("-".repeat(100));
            }
        }
    }
    
    private static void searchPatientById() {
        System.out.print("\nEnter patient ID: ");
        String patientId = scanner.nextLine();
        
        Patient patient = patientService.searchById(patientId);
        if (patient == null) {
            System.out.println("Patient not found!");
        } else {
            System.out.println("\n" + patient.getDetails());
        }
    }
    
    private static void searchPatientByName() {
        System.out.print("\nEnter patient name: ");
        String name = scanner.nextLine();
        
        List<Patient> patients = patientService.searchByName(name);
        if (patients.isEmpty()) {
            System.out.println("No patients found!");
        } else {
            System.out.println("\n" + "=".repeat(100));
            for (Patient pat : patients) {
                System.out.println(pat.getDetails());
                System.out.println("-".repeat(100));
            }
        }
    }
    
    private static void updatePatient() {
        try {
            System.out.print("\nEnter patient ID to update: ");
            String patientId = scanner.nextLine();
            
            Patient patient = patientService.searchById(patientId);
            if (patient == null) {
                System.out.println(" Patient not found!");
                return;
            }
            
            System.out.print("Enter new phone: ");
            String phone = scanner.nextLine();
            patient.setPhone(phone);
            
            patientService.updatePatient(patient);
        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }
    
    private static void deletePatient() {
        System.out.print("\nEnter patient ID to delete: ");
        String patientId = scanner.nextLine();
        patientService.deletePatient(patientId);
    }
    
    private static void addAllergyToPatient() {
        System.out.print("\nEnter patient ID: ");
        String patientId = scanner.nextLine();
        
        System.out.print("Enter allergy: ");
        String allergy = scanner.nextLine();
        
        if (patientService.addAllergy(patientId, allergy)) {
            System.out.println("✓ Allergy added successfully!");
        } else {
            System.out.println(" Patient not found!");
        }
    }

    private static void appointmentMenu() {
        boolean inMenu = true;
        
        while (inMenu) {
            System.out.println("\n" + "-".repeat(50));
            System.out.println("APPOINTMENT MANAGEMENT");
            System.out.println("-".repeat(50));
            System.out.println("1. Book New Appointment");
            System.out.println("2. View All Appointments");
            System.out.println("3. Search Appointment by ID");
            System.out.println("4. View Appointments by Patient");
            System.out.println("5. View Appointments by Doctor");
            System.out.println("6. Confirm Appointment");
            System.out.println("7. Cancel Appointment");
            System.out.println("8. Complete Appointment");
            System.out.println("9. Back to Main Menu");
            System.out.print("\nEnter your choice (1-9): ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1:
                        bookAppointment();
                        break;
                    case 2:
                        viewAllAppointments();
                        break;
                    case 3:
                        searchAppointmentById();
                        break;
                    case 4:
                        viewAppointmentsByPatient();
                        break;
                    case 5:
                        viewAppointmentsByDoctor();
                        break;
                    case 6:
                        confirmAppointment();
                        break;
                    case 7:
                        cancelAppointment();
                        break;
                    case 8:
                        completeAppointment();
                        break;
                    case 9:
                        inMenu = false;
                        break;
                    default:
                        System.out.println(" Invalid choice!");
                }
            } catch (NumberFormatException e) {
                System.out.println(" Invalid input!");
            }
        }
    }
    
    private static void bookAppointment() {
        try {
            System.out.println("\n--- Book New Appointment ---");
            System.out.print("Enter patient ID: ");
            String patientId = scanner.nextLine();
            
            System.out.print("Enter doctor ID: ");
            String doctorId = scanner.nextLine();
            
            System.out.print("Enter appointment date and time (yyyy-MM-dd HH:mm): ");
            String dateTimeStr = scanner.nextLine();
            LocalDateTime appointmentTime = LocalDateTime.parse(dateTimeStr, 
                java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            
            Appointment appointment = appointmentService.createAppointment(patientId, doctorId, appointmentTime);
            System.out.println("✓ Appointment booked: " + appointment.getId());
        } catch (AppointmentNotFoundException e) {
            System.out.println(" Error: " + e.getMessage());
        } catch (InvalidDataException e) {
            System.out.println(" Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println(" Invalid input!");
        }
    }
    
    private static void viewAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        if (appointments.isEmpty()) {
            System.out.println("\n✗ No appointments found!");
        } else {
            System.out.println("\n" + "=".repeat(100));
            System.out.println("APPOINTMENTS LIST");
            System.out.println("=".repeat(100));
            for (Appointment apt : appointments) {
                System.out.println(apt);
            }
        }
    }
    
    private static void searchAppointmentById() {
        System.out.print("\nEnter appointment ID: ");
        String appointmentId = scanner.nextLine();
        
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        if (appointment == null) {
            System.out.println(" Appointment not found!");
        } else {
            System.out.println("\n" + appointment);
        }
    }
    
    private static void viewAppointmentsByPatient() {
        System.out.print("\nEnter patient ID: ");
        String patientId = scanner.nextLine();
        
        List<Appointment> appointments = appointmentService.getAppointmentsByPatient(patientId);
        if (appointments.isEmpty()) {
            System.out.println(" No appointments found for this patient!");
        } else {
            System.out.println("\n" + "=".repeat(100));
            for (Appointment apt : appointments) {
                System.out.println(apt);
            }
        }
    }
    
    private static void viewAppointmentsByDoctor() {
        System.out.print("\nEnter doctor ID: ");
        String doctorId = scanner.nextLine();
        
        List<Appointment> appointments = appointmentService.getAppointmentsByDoctor(doctorId);
        if (appointments.isEmpty()) {
            System.out.println(" No appointments found for this doctor!");
        } else {
            System.out.println("\n" + "=".repeat(100));
            for (Appointment apt : appointments) {
                System.out.println(apt);
            }
        }
    }
    
    private static void confirmAppointment() {
        try {
            System.out.print("\nEnter appointment ID to confirm: ");
            String appointmentId = scanner.nextLine();
            appointmentService.confirmAppointment(appointmentId);
        } catch (AppointmentNotFoundException e) {
            System.out.println(" " + e.getMessage());
        }
    }
    
    private static void cancelAppointment() {
        try {
            System.out.print("\nEnter appointment ID to cancel: ");
            String appointmentId = scanner.nextLine();
            appointmentService.cancelAppointment(appointmentId);
        } catch (AppointmentNotFoundException e) {
            System.out.println(" " + e.getMessage());
        }
    }
    
    private static void completeAppointment() {
        try {
            System.out.print("\nEnter appointment ID to complete: ");
            String appointmentId = scanner.nextLine();
            appointmentService.completeAppointment(appointmentId);
        } catch (AppointmentNotFoundException e) {
            System.out.println(" " + e.getMessage());
        }
    }

    private static void billingMenu() {
        boolean inMenu = true;
        
        while (inMenu) {
            System.out.println("\n" + "-".repeat(50));
            System.out.println("BILLING MANAGEMENT");
            System.out.println("-".repeat(50));
            System.out.println("1. Generate Bill");
            System.out.println("2. View All Bills");
            System.out.println("3. View Pending Bills");
            System.out.println("4. View Paid Bills");
            System.out.println("5. Search Bill by ID");
            System.out.println("6. Process Payment");
            System.out.println("7. Back to Main Menu");
            System.out.print("\nEnter your choice (1-7): ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1:
                        generateBill();
                        break;
                    case 2:
                        viewAllBills();
                        break;
                    case 3:
                        viewPendingBills();
                        break;
                    case 4:
                        viewPaidBills();
                        break;
                    case 5:
                        searchBillById();
                        break;
                    case 6:
                        processPayment();
                        break;
                    case 7:
                        inMenu = false;
                        break;
                    default:
                        System.out.println(" Invalid choice!");
                }
            } catch (NumberFormatException e) {
                System.out.println(" Invalid input!");
            }
        }
    }
    
    private static void generateBill() {
        try {
            System.out.print("\nEnter appointment ID: ");
            String appointmentId = scanner.nextLine();
            
            Bill bill = billingService.generateBill(appointmentId);
            System.out.println("\n" + bill.toString());
        } catch (AppointmentNotFoundException e) {
            System.out.println(" " + e.getMessage());
        }
    }
    
    private static void viewAllBills() {
        List<Bill> bills = billingService.getAllBills();
        if (bills.isEmpty()) {
            System.out.println("\n✗ No bills found!");
        } else {
            System.out.println("\n" + "=".repeat(100));
            System.out.println("BILLS LIST");
            System.out.println("=".repeat(100));
            for (Bill bill : bills) {
                System.out.println(bill);
            }
        }
    }
    
    private static void viewPendingBills() {
        List<Bill> bills = billingService.getPendingBills();
        if (bills.isEmpty()) {
            System.out.println("\n✗ No pending bills!");
        } else {
            System.out.println("\n" + "=".repeat(100));
            System.out.println("PENDING BILLS");
            System.out.println("=".repeat(100));
            for (Bill bill : bills) {
                System.out.println(bill);
            }
            System.out.println("Total Pending: ₹" + String.format("%.2f", 
                billingService.calculateTotalPendingAmount()));
        }
    }
    
    private static void viewPaidBills() {
        List<Bill> bills = billingService.getPaidBills();
        if (bills.isEmpty()) {
            System.out.println("\n✗ No paid bills yet!");
        } else {
            System.out.println("\n" + "=".repeat(100));
            System.out.println("PAID BILLS");
            System.out.println("=".repeat(100));
            for (Bill bill : bills) {
                System.out.println(bill);
            }
            System.out.println("Total Paid: ₹" + String.format("%.2f", 
                billingService.calculateTotalRevenue()));
        }
    }
    
    private static void searchBillById() {
        System.out.print("\nEnter bill ID: ");
        String billId = scanner.nextLine();
        
        Bill bill = billingService.getBillById(billId);
        if (bill == null) {
            System.out.println(" Bill not found!");
        } else {
            System.out.println("\n" + bill);
        }
    }
    
    private static void processPayment() {
        System.out.print("\nEnter bill ID: ");
        String billId = scanner.nextLine();
        
        if (billingService.processPayment(billId)) {
            System.out.println("✓ Payment processed successfully!");
        }
    }

    private static void analyticsMenu() {
        System.out.println("\n" + "-".repeat(50));
        System.out.println("REPORTS & ANALYTICS");
        System.out.println("-".repeat(50));
        System.out.println("Total Doctors: " + doctorService.getTotalDoctors());
        System.out.println("Total Patients: " + patientService.getTotalPatients());
        System.out.println("Total Appointments: " + appointmentService.getTotalAppointments());
        System.out.println("Total Bills: " + billingService.getTotalBills());
        System.out.println("\nFinancial Summary:");
        System.out.println("Total Revenue (Paid): ₹" + String.format("%.2f", 
            billingService.calculateTotalRevenue()));
        System.out.println("Total Pending: ₹" + String.format("%.2f", 
            billingService.calculateTotalPendingAmount()));
        System.out.println("Total Tax Collected: ₹" + String.format("%.2f", 
            billingService.getTotalTaxCollected()));
        System.out.println("Average Bill Amount: ₹" + String.format("%.2f", 
            billingService.calculateAverageBillAmount()));
        System.out.println("Average Consultation Fee: ₹" + String.format("%.2f", 
            doctorService.getAverageConsultationFee()));
    }
}
