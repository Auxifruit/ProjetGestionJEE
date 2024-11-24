package com.example.projetjee.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name="major")
public class Major {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "majorId")
    private int majorId;

    @Column(name = "majorName", unique = true)
    private String majorName;

    public int getMajorId() {
        return majorId;
    }

    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Major major = (Major) o;

        if (majorId != major.majorId) return false;
        if (majorName != null ? !majorName.equals(major.majorName) : major.majorName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = majorId;
        result = 31 * result + (majorName != null ? majorName.hashCode() : 0);
        return result;
    }
}