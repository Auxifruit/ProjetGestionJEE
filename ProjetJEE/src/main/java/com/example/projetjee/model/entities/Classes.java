package com.example.projetjee.model.entities;

import jakarta.persistence.*;

@Entity
public class Classes {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "classesId")
    private int classesId;
    @Basic
    @Column(name = "classesName")
    private String classesName;

    public int getClassesId() {
        return classesId;
    }

    public void setClassesId(int classesId) {
        this.classesId = classesId;
    }

    public String getClassesName() {
        return classesName;
    }

    public void setClassesName(String classesName) {
        this.classesName = classesName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Classes classes = (Classes) o;

        if (classesId != classes.classesId) return false;
        if (classesName != null ? !classesName.equals(classes.classesName) : classes.classesName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = classesId;
        result = 31 * result + (classesName != null ? classesName.hashCode() : 0);
        return result;
    }
}
