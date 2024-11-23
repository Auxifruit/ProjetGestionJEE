package com.example.projetjee.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "lessonclass")
public class Lessonclass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lessonClassId")
    private int lessonClassId;

    @Column(name = "lessonId")
    private Integer lessonId;

    @Column(name = "classId")
    private Integer classId;

    public int getLessonClassId() {
        return lessonClassId;
    }

    public void setLessonClassId(int lessonClassId) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lessonclass that = (Lessonclass) o;

        if (lessonClassId != that.lessonClassId) return false;
        if (lessonId != null ? !lessonId.equals(that.lessonId) : that.lessonId != null) return false;
        if (classId != null ? !classId.equals(that.classId) : that.classId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = lessonClassId;
        result = 31 * result + (lessonId != null ? lessonId.hashCode() : 0);
        result = 31 * result + (classId != null ? classId.hashCode() : 0);
        return result;
    }
}