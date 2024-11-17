package com.example.projetjee.model.entities;

import jakarta.persistence.*;

@Entity
public class Teacher {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "teacherId")
    private int teacherId;

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Teacher teacher = (Teacher) o;

        if (teacherId != teacher.teacherId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return teacherId;
    }
}
