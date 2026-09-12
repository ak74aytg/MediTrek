package com.airtribe.MediTrack.entity;

public enum Specialization {
    CARDIOLOGY("Cardiology", "Heart and circulatory system diseases"),
    NEUROLOGY("Neurology", "Nervous system and brain disorders"),
    ORTHOPEDICS("Orthopedics", "Bones, joints and skeletal system"),
    PEDIATRICS("Pediatrics", "Children's medical care"),
    DERMATOLOGY("Dermatology", "Skin conditions and treatment"),
    GENERAL_MEDICINE("General Medicine", "General health and wellness"),
    DENTISTRY("Dentistry", "Dental care and treatment"),
    PSYCHOLOGY("Psychology", "Mental health and behavioral disorders");
    
    private final String displayName;
    private final String description;

    Specialization(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public static String[] getAllSpecializations() {
        Specialization[] specs = values();
        String[] names = new String[specs.length];
        for (int i = 0; i < specs.length; i++) {
            names[i] = specs[i].name();
        }
        return names;
    }
}
