package com.example.projetjee.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "lessonclass")
public class Lessonclass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lessonClassId")
    private int lessonClassId;
    @Basic
    @JoinColumn(name = "lessonId", nullable = false)
    @Column(name = "lessonId")
    private Integer lessonId;
    @JoinColumn(name = "classId", nullable = false, insertable = false, updatable = false)
    private Integer classId;
    @Basic
    @Column(name = "classId", insertable = false, updatable = false)
    private Integer classesId;

    public int getLessonClassId() {
        return lessonClassId;
    }

    public void setLessonClassId(int lessonClassId) {
        this.lessonClassId = lessonClassId;
    }

    // Getters et Setters
    public int getId() {
        return lessonClassId;
    }

    public void setId(int lessonClassId) {
        this.lessonClassId = lessonClassId;
    }

    public Integer getLessonId() {
        return lessonId;
    }

    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getClassesId() {
        return classesId;
    }

    public void setClassesId(Integer classesId) {
        this.classesId = classesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lessonclass that = (Lessonclass) o;

        if (lessonClassId != that.lessonClassId) return false;
        if (lessonId != null ? !lessonId.equals(that.lessonId) : that.lessonId != null) return false;
        if (classesId != null ? !classesId.equals(that.classesId) : that.classesId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = lessonClassId;
        result = 31 * result + (lessonId != null ? lessonId.hashCode() : 0);
        result = 31 * result + (classesId != null ? classesId.hashCode() : 0);
        return result;
    }
}
