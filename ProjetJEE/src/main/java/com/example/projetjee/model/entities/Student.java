package com.example.projetjee.model.entities;

import jakarta.persistence.*;

/**
 * Entity representing a student in the system.
 */
@Entity
public class Student {
    /**
     * The unique identifier for a student.
     */
    @Id
    @Column(name = "studentId")
    private int studentId;
    /**
     * The identifier of the class to which the student belongs.
     */
    @Basic
    @Column(name = "classId")
    private Integer classId;

    /**
     * The identifier of the major that the student is enrolled in.
     */
    @Basic
    @Column(name = "majorId")
    private Integer majorId;

    /*
    // The following code represents a relation to a Users entity which is currently commented out.
    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId", insertable = false, updatable = false)
    private Users user;  // Association to the Users entity
    */

    /**
     * Gets the unique identifier of the student.
     *
     * @return the student ID.
     */
    
    public int getStudentId() {
        return studentId;
    }

    /**
     * Sets the unique identifier of the student.
     *
     * @param studentId the student ID to set.
     */
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    /**
     * Gets the identifier of the class to which the student belongs.
     *
     * @return the class ID.
     */
    public Integer getClassId() {
        return classId;
    }

    /**
     * Sets the identifier of the class to which the student belongs.
     *
     * @param classId the class ID to set.
     */
    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    /**
     * Gets the identifier of the major that the student is enrolled in.
     *
     * @return the major ID.
     */
    public Integer getMajorId() {
        return majorId;
    }

    /**
     * Sets the identifier of the major that the student is enrolled in.
     *
     * @param majorId the major ID to set.
     */
    public void setMajorId(Integer majorId) {
        this.majorId = majorId;
    }

    /*
    // The following code represents getter and setter for the 'user' field which is currently commented out.
    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
    */

    /**
     * Checks if this student is equal to another object.
     *
     * @param o the object to compare to.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
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

    /**
     * Computes the hash code for this student.
     *
     * @return the hash code of the student.
     */
    @Override
    public int hashCode() {
        int result = studentId;
        result = 31 * result + (classId != null ? classId.hashCode() : 0);
        result = 31 * result + (majorId != null ? majorId.hashCode() : 0);
        return result;
    }
}
