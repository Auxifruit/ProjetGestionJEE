package com.example.projetjee.model.entities;

import jakarta.persistence.*;

/**
 * Entity representing a class in the system.
 */
@Entity
@Table(name = "administrator")
public class Administrator {

    /**
     * The unique identifier for an administrator.
     */
    @Id
    @Column(name = "administratorId")
    private int administratorId;

    /**
     * Gets the administrator's unique identifier.
     *
     * @return the administrator ID.
     */
    public int getAdministratorId() {
        return administratorId;
    }

    /**
     * Sets the administrator's unique identifier.
     *
     * @param administratorId the administrator ID to set.
     */
    public void setAdministratorId(int administratorId) {
        this.administratorId = administratorId;
    }

    /**
     * Checks if this administrator is equal to another object.
     *
     * @param o the object to compare to.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Administrator that = (Administrator) o;

        if (administratorId != that.administratorId) return false;

        return true;
    }

    /**
     * Computes the hash code for this administrator.
     *
     * @return the hash code of the administrator.
     */
    @Override
    public int hashCode() {
        return administratorId;
    }
}