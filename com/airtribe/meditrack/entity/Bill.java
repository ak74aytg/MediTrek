package entity;

import interfaces.Payable;

import java.time.LocalDateTime;

public class Bill implements Payable {
    private String id;
    private Appointment appointment;
    private Double amount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
}
