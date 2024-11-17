package com.example.projetjee.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "class", schema = "cydtb_jee", catalog = "")
public class Clazz {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "classId")
    private int classId;
    @Basic
    @Column(name = "className")
    private String className;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Clazz clazz = (Clazz) o;

        if (classId != clazz.classId) return false;
        if (className != null ? !className.equals(clazz.className) : clazz.className != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = classId;
        result = 31 * result + (className != null ? className.hashCode() : 0);
        return result;
    }
}
