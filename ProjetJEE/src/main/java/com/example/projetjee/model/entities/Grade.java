package com.example.projetjee.model.entities;

import jakarta.persistence.*;

@Entity
public class Grade {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "gradeId")
    private int gradeId;
    @Basic
    @Column(name = "gradeName")
    private String gradeName;
    @Basic
    @Column(name = "gradeValue")
    private Double gradeValue;
    @Basic
    @Column(name = "gradeCoefficient")
    private Integer gradeCoefficient;
    @Basic
    @Column(name = "studentId")
    private Integer studentId;
    @Basic
    @Column(name = "courseId")
    private Integer courseId;
    @Basic
    @Column(name = "teacherId")
    private Integer teacherId;

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public Double getGradeValue() {
        return gradeValue;
    }

    public void setGradeValue(Double gradeValue) {
        this.gradeValue = gradeValue;
    }

    public Integer getGradeCoefficient() {
        return gradeCoefficient;
    }

    public void setGradeCoefficient(Integer gradeCoefficient) {
        this.gradeCoefficient = gradeCoefficient;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

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
