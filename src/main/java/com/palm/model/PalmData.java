package com.palm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

import java.util.Arrays;

@Entity
@Table(name = "palm_data")
public class PalmData {

    @Id
    private String palmId; // Unique ID for the palm record

    private String schoolId; // Associated school ID

    @Lob
    private byte[] palmTemplate; // Palm binary template

    // Default constructor required by JPA
    public PalmData() {
    }

    // Constructor for easy instantiation
    public PalmData(String palmId, String schoolId, byte[] palmTemplate) {
        this.palmId = palmId;
        this.schoolId = schoolId;
        this.palmTemplate = palmTemplate;
    }

    // Getters and Setters
    public String getPalmId() {
        return palmId;
    }

    public void setPalmId(String palmId) {
        this.palmId = palmId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public byte[] getPalmTemplate() {
        return palmTemplate;
    }

    public void setPalmTemplate(byte[] palmTemplate) {
        this.palmTemplate = palmTemplate;
    }

    @Override
    public String toString() {
        return "PalmData{" +
                "palmId='" + palmId + '\'' +
                ", schoolId='" + schoolId + '\'' +
                ", palmTemplate=" + Arrays.toString(palmTemplate) +
                '}';
    }
}
