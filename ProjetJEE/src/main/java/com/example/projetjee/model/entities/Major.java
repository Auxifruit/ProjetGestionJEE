package com.example.projetjee.model.entities;

import jakarta.persistence.*;

/**
 * Entity representing a Major in the system.
 */
@Entity
@Table(name="major")
public class Major {

    /**
     * The unique identifier for a major.
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "majorId")
    private int majorId;

    /**
     * The name of the major.
     * The name must be unique in the system.
     */
    @Column(name = "majorName", unique = true)
    private String majorName;

    /**
     * Gets the unique identifier of the major.
     *
     * @return the major ID.
     */
    public int getMajorId() {
        return majorId;
    }

    /**
     * Sets the unique identifier of the major.
     *
     * @param majorId the major ID to set.
     */
    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }

    /**
     * Gets the name of the major.
     *
     * @return the major name.
     */
    public String getMajorName() {
        return majorName;
    }

    /**
     * Sets the name of the major.
     *
     * @param majorName the major name to set.
     */
    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    /**
     * Checks if this major is equal to another object.
     *
     * @param o the object to compare to.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Major major = (Major) o;

        if (majorId != major.majorId) return false;
        if (majorName != null ? !majorName.equals(major.majorName) : major.majorName != null) return false;

        return true;
    }

    /**
     * Computes the hash code for this major.
     *
     * @return the hash code of the major.
     */
    @Override
    public int hashCode() {
        int result = majorId;
        result = 31 * result + (majorName != null ? majorName.hashCode() : 0);
        return result;
    }
}