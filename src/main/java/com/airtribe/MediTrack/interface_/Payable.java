package com.airtribe.MediTrack.interface_;

public interface Payable {

    double getAmount();

    void processPayment();

    default String getPaymentStatus() {
        return "Amount to be paid: ₹" + String.format("%.2f", getAmount());
    }

    default boolean isValidAmount() {
        return getAmount() > 0;
    }
}
