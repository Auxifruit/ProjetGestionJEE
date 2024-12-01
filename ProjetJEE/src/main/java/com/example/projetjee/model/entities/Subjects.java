package com.example.projetjee.model.entities;

import jakarta.persistence.*;

/**
 * Entity representing a subject in the system.
 */
@Entity
@Table(name="subjects")
public class Subjects {
    /**
     * The unique identifier for a subject.
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "subjectId")
    private int subjectId;

    /**
     * The name of the subject.
     * This field is unique, meaning no two subjects can have the same name.
     */
    @Column(name = "subjectName", unique = true)
    private String subjectName;

    /**
     * Gets the unique identifier of the subject.
     *
     * @return the subject ID.
     */
    public int getSubjectId() {
        return subjectId;
    }

    /**
     * Sets the unique identifier of the subject.
     *
     * @param subjectId the subject ID to set.
     */
    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    /**
     * Gets the name of the subject.
     *
     * @return the subject name.
     */
    public String getSubjectName() {
        return subjectName;
    }

    /**
     * Sets the name of the subject.
     *
     * @param subjectName the subject name to set.
     */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    /**
     * Checks if this subject is equal to another object.
     *
     * @param o the object to compare to.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subjects subjects = (Subjects) o;

        if (subjectId != subjects.subjectId) return false;
        if (subjectName != null ? !subjectName.equals(subjects.subjectName) : subjects.subjectName != null)
            return false;

        return true;
    }

    /**
     * Computes the hash code for this subject.
     *
     * @return the hash code of the subject.
     */
    @Override
    public int hashCode() {
        int result = subjectId;
        result = 31 * result + (subjectName != null ? subjectName.hashCode() : 0);
        return result;
    }
}