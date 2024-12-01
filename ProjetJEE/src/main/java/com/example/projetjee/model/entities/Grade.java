package com.example.projetjee.model.entities;

import jakarta.persistence.*;

/**
 * Entity representing a Grade in the system.
 */
@Entity
public class Grade {
    /**
     * The unique identifier for a grade.
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "gradeId")
    private int gradeId;

    /**
     * The name or description of the grade.
     */
    @Basic
    @Column(name = "gradeName")
    private String gradeName;
    /**
     * The value of the grade.
     */
    @Basic
    @Column(name = "gradeValue")
    private Double gradeValue;

    /**
     * The coefficient associated with the grade.
     */
    @Basic
    @Column(name = "gradeCoefficient")
    private Integer gradeCoefficient;

    /**
     * The identifier of the student associated with the grade.
     */
    @Basic
    @Column(name = "studentId")
    private Integer studentId;

    /**
     * The identifier of the course associated with the grade.
     */
    @Basic
    @Column(name = "courseId")
    private Integer courseId;

    /**
     * The identifier of the teacher who assigned the grade.
     */
    @Basic
    @Column(name = "teacherId")
    private Integer teacherId;

    /**
     * Gets the unique identifier of the grade.
     *
     * @return the grade ID.
     */
    public int getGradeId() {
        return gradeId;
    }

    /**
     * Sets the unique identifier of the grade.
     *
     * @param gradeId the grade ID to set.
     */
    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    /**
     * Gets the name or description of the grade.
     *
     * @return the grade name.
     */
    public String getGradeName() {
        return gradeName;
    }

    /**
     * Sets the name or description of the grade.
     *
     * @param gradeName the grade name to set.
     */
    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    /**
     * Gets the value of the grade.
     *
     * @return the grade value.
     */
    public Double getGradeValue() {
        return gradeValue;
    }

    /**
     * Sets the value of the grade.
     *
     * @param gradeValue the grade value to set.
     */
    public void setGradeValue(Double gradeValue) {
        this.gradeValue = gradeValue;
    }

    /**
     * Gets the coefficient associated with the grade.
     *
     * @return the grade coefficient.
     */
    public Integer getGradeCoefficient() {
        return gradeCoefficient;
    }

    /**
     * Sets the coefficient associated with the grade.
     *
     * @param gradeCoefficient the grade coefficient to set.
     */
    public void setGradeCoefficient(Integer gradeCoefficient) {
        this.gradeCoefficient = gradeCoefficient;
    }

    /**
     * Gets the student ID associated with the grade.
     *
     * @return the student ID.
     */
    public Integer getStudentId() {
        return studentId;
    }

    /**
     * Sets the student ID associated with the grade.
     *
     * @param studentId the student ID to set.
     */
    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    /**
     * Gets the course ID associated with the grade.
     *
     * @return the course ID.
     */
    public Integer getCourseId() {
        return courseId;
    }

    /**
     * Sets the course ID associated with the grade.
     *
     * @param courseId the course ID to set.
     */
    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    /**
     * Gets the teacher ID who assigned the grade.
     *
     * @return the teacher ID.
     */
    public Integer getTeacherId() {
        return teacherId;
    }

    /**
     * Sets the teacher ID who assigned the grade.
     *
     * @param teacherId the teacher ID to set.
     */
    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    /**
     * Checks if this grade is equal to another object.
     *
     * @param o the object to compare to.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Grade grade = (Grade) o;

        if (gradeId != grade.gradeId) return false;
        if (gradeName != null ? !gradeName.equals(grade.gradeName) : grade.gradeName != null) return false;
        if (gradeValue != null ? !gradeValue.equals(grade.gradeValue) : grade.gradeValue != null) return false;
        if (gradeCoefficient != null ? !gradeCoefficient.equals(grade.gradeCoefficient) : grade.gradeCoefficient != null)
            return false;
        if (studentId != null ? !studentId.equals(grade.studentId) : grade.studentId != null) return false;
        if (courseId != null ? !courseId.equals(grade.courseId) : grade.courseId != null) return false;
        if (teacherId != null ? !teacherId.equals(grade.teacherId) : grade.teacherId != null) return false;

        return true;
    }

    /**
     * Computes the hash code for this grade.
     *
     * @return the hash code of the grade.
     */
    @Override
    public int hashCode() {
        int result = gradeId;
        result = 31 * result + (gradeName != null ? gradeName.hashCode() : 0);
        result = 31 * result + (gradeValue != null ? gradeValue.hashCode() : 0);
        result = 31 * result + (gradeCoefficient != null ? gradeCoefficient.hashCode() : 0);
        result = 31 * result + (studentId != null ? studentId.hashCode() : 0);
        result = 31 * result + (courseId != null ? courseId.hashCode() : 0);
        result = 31 * result + (teacherId != null ? teacherId.hashCode() : 0);
        return result;
    }
}
