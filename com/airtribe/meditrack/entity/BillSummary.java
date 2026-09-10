package entity;

import java.time.LocalDateTime;

public final class BillSummary {

    private final String billId;
    private final String patientName;
    private final double totalAmount;

    public BillSummary(String billId, String patientName, double totalAmount) {
        this.billId = billId;
        this.patientName = patientName;
        this.totalAmount = totalAmount;
    }

    public String getBillId() {
        return billId;
    }

    public String getPatientName() {
        return patientName;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}