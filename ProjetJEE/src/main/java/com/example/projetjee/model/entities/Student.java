package com.example.projetjee.model.entities;

import jakarta.persistence.*;

@Entity
public class Student {
    @Id
    @Column(name = "studentId")
    private int studentId;
    @Basic
    @Column(name = "classId")
    private Integer classId;
    @Basic
    @Column(name = "majorId")
    private Integer majorId;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId", insertable = false, updatable = false)
    private Users user;  // Association à la classe Users

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getMajorId() {
        return majorId;
    }

    public void setMajorId(Integer majorId) {
        this.majorId = majorId;
    }

    // Getters et setters pour l'attribut 'user'
    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (studentId != student.studentId) return false;
        if (classId != null ? !classId.equals(student.classId) : student.classId != null) return false;
        if (majorId != null ? !majorId.equals(student.majorId) : student.majorId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = studentId;
        result = 31 * result + (classId != null ? classId.hashCode() : 0);
        result = 31 * result + (majorId != null ? majorId.hashCode() : 0);
        return result;
    }
}
