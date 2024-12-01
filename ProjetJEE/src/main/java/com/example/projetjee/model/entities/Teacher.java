package com.example.projetjee.model.entities;

import jakarta.persistence.*;

/**
 * Entity representing a teacher in the system.
 */
@Entity
@Table(name="teacher")
public class Teacher {
    /**
     * The unique identifier for a teacher.
     */
    @Id
    @Column(name = "teacherId")
    private int teacherId;

    /**
     * Gets the unique identifier of the teacher.
     *
     * @return the teacher ID.
     */
    public int getTeacherId() {
        return teacherId;
    }

    /**
     * Sets the unique identifier of the teacher.
     *
     * @param teacherId the teacher ID to set.
     */
    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    /**
     * Checks if this teacher is equal to another object.
     *
     * @param o the object to compare to.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Teacher teacher = (Teacher) o;

        if (teacherId != teacher.teacherId) return false;

        return true;
    }

    /**
     * Computes the hash code for this teacher.
     *
     * @return the hash code of the teacher.
     */
    @Override
    public int hashCode() {
        return teacherId;
    }
}