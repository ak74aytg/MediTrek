package com.airtribe.MediTrack.util;

import com.airtribe.MediTrack.constants.Constants;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {

    private DateUtil() {
        throw new AssertionError("Cannot instantiate DateUtil class");
    }

    public static LocalDateTime parseDateTime(String dateStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
            return LocalDateTime.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Unable to parse date: " + dateStr, dateStr, 0, e);
        }
    }

    public static LocalDate parseDate(String dateStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_ONLY_FORMAT);
            return LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Unable to parse date: " + dateStr, dateStr, 0, e);
        }
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
        return dateTime.format(formatter);
    }

    public static String formatDate(LocalDate date) {
        if (date == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_ONLY_FORMAT);
        return date.format(formatter);
    }

    public static boolean isValidAppointmentTime(LocalDateTime appointmentTime) {
        return appointmentTime != null && appointmentTime.isAfter(LocalDateTime.now());
    }

    public static boolean isInPast(LocalDateTime date) {
        return date != null && date.isBefore(LocalDateTime.now());
    }

    public static boolean isToday(LocalDate date) {
        return date != null && date.equals(LocalDate.now());
    }

    public static int calculateAge(LocalDate dateOfBirth) {
        if (dateOfBirth == null) {
            return 0;
        }
        return LocalDate.now().getYear() - dateOfBirth.getYear();
    }

    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }
}
