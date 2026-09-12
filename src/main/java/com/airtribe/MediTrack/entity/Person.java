package com.airtribe.MediTrack.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Person {

    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public Person() {
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }

    public Person(String name, String email) {
        this();
        this.name = name;
        this.email = email;
    }

    public Person(String id, String name, String email, String phone, String address) {
        this(name, email);
        this.id = id;
        this.phone = phone;
        this.address = address;
    }

    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
    
    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }
    
    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public abstract String getDetails();

    protected void logActivity(String activity) {
        System.out.println("[" + getClass().getSimpleName() + " - " + this.name + "] " + activity);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) &&
               Objects.equals(email, person.email);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               ", email='" + email + '\'' +
               ", phone='" + phone + '\'' +
               ", address='" + address + '\'' +
               '}';
    }
}
