package com.airtribe.MediTrack.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVUtil {

    private static final String DELIMITER = ",";
    private static final String QUOTE = "\"";

    private CSVUtil() {
        throw new AssertionError("Cannot instantiate CSVUtil class");
    }

    public static List<String> parseCSVLine(String line) {
        if (line == null || line.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.asList(line.split(DELIMITER));
    }

    public static List<String> parseCSVLineAdvanced(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;
        
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                fields.add(sb.toString().trim());
                sb = new StringBuilder();
            } else {
                sb.append(c);
            }
        }
        fields.add(sb.toString().trim());
        return fields;
    }

    public static String createCSVLine(String... values) {
        if (values == null || values.length == 0) {
            return "";
        }
        return String.join(DELIMITER, values);
    }

    public static String createCSVLine(List<String> values) {
        if (values == null || values.isEmpty()) {
            return "";
        }
        return String.join(DELIMITER, values);
    }

    public static String createCSVLineQuoted(String... values) {
        if (values == null || values.length == 0) {
            return "";
        }
        
        List<String> quotedValues = new ArrayList<>();
        for (String value : values) {
            if (value != null && (value.contains(DELIMITER) || value.contains(QUOTE))) {

                String escaped = value.replace(QUOTE, QUOTE + QUOTE);
                quotedValues.add(QUOTE + escaped + QUOTE);
            } else {
                quotedValues.add(value != null ? value : "");
            }
        }
        
        return String.join(DELIMITER, quotedValues);
    }

    public static String escapeCSVField(String value) {
        if (value == null) {
            return "";
        }
        
        if (value.contains(DELIMITER) || value.contains(QUOTE) || value.contains("\n")) {
            return QUOTE + value.replace(QUOTE, QUOTE + QUOTE) + QUOTE;
        }
        
        return value;
    }

    public static String createHeaderLine(String... headers) {
        return createCSVLine(headers);
    }

    public static boolean isValidCSVLine(String line) {
        if (line == null || line.isEmpty()) {
            return false;
        }

        int quoteCount = 0;
        for (char c : line.toCharArray()) {
            if (c == '"') {
                quoteCount++;
            }
        }
        
        return quoteCount % 2 == 0;
    }
}
