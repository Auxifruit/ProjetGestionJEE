package com.example.projetjee.model.entities;

import jakarta.persistence.*;
import java.util.Objects;

/**
 * Entity representing a class in the system.
 */
@Entity
@Table(name = "classes")
public class Classes {

    /**
     * The unique identifier for a class.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "classId", nullable = true)
    private int classId;

    /**
     * The name of the class.
     */
    @Column(name = "className", nullable = true, unique = true)
    private String className;

    // Getters et Setters
    /**
     * Gets the unique identifier of the class.
     *
     * @return the class ID.
     */
    public int getClassId() {
        return classId;
    }

    /**
     * Sets the unique identifier of the class.
     *
     * @param classId the class ID to set.
     */
    public void setClassId(int classId) {
        this.classId = classId;
    }

    /**
     * Gets the name of the class.
     *
     * @return the class name.
     */
    public String getClassName() {
        return className;
    }

    /**
     * Sets the name of the class.
     *
     * @param className the class name to set.
     */
    public void setClassName(String className) {
        this.className = className;
    }

    // Méthodes equals et hashCode
    /**
     * Checks if this class is equal to another object.
     *
     * @param o the object to compare to.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Classes classes = (Classes) o;
        return classId == classes.classId && Objects.equals(className, classes.className);
    }

    /**
     * Computes the hash code for this class.
     *
     * @return the hash code of the class.
     */
    @Override
    public int hashCode() {
        return Objects.hash(classId, className);
    }

    /**
     * Returns a string representation of the class.
     *
     * @return a string describing the class.
     */
    @Override
    public String toString() {
        return "Classes{" +
                "classId=" + classId +
                ", className='" + className + '\'' +
                '}';
    }
}
