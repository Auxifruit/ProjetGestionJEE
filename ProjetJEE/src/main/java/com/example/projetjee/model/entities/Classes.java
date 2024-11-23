package com.example.projetjee.model.entities;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "classes")
public class Classes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "classesId")
    private int classId;

    @Column(name = "classesName", nullable = false)
    private String className;

    // Getters et Setters
    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    // Méthodes equals et hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Classes classes = (Classes) o;
        return classId == classes.classId && Objects.equals(className, classes.className);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classId, className);
    }

    @Override
    public String toString() {
        return "Classes{" +
                "classId=" + classId +
                ", className='" + className + '\'' +
                '}';
    }
}
