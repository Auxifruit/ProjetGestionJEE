package com.example.projetjee.model.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Lesson {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "lessonId")
    private int lessonId;
    @Basic
    @Column(name = "lessonStartDate")
    private Timestamp lessonStartDate;
    @Basic
    @Column(name = "lessonEndDate")
    private Timestamp lessonEndDate;
    @Basic
    @Column(name = "courseId")
    private Integer courseId;
    @Basic
    @Column(name = "teacherId")
    private Integer teacherId;

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public Timestamp getLessonStartDate() {
        return lessonStartDate;
    }

    public void setLessonStartDate(Timestamp lessonStartDate) {
        this.lessonStartDate = lessonStartDate;
    }

    public Timestamp getLessonEndDate() {
        return lessonEndDate;
    }

    public void setLessonEndDate(Timestamp lessonEndDate) {
        this.lessonEndDate = lessonEndDate;
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

        Lesson lesson = (Lesson) o;

        if (lessonId != lesson.lessonId) return false;
        if (lessonStartDate != null ? !lessonStartDate.equals(lesson.lessonStartDate) : lesson.lessonStartDate != null)
            return false;
        if (lessonEndDate != null ? !lessonEndDate.equals(lesson.lessonEndDate) : lesson.lessonEndDate != null)
            return false;
        if (courseId != null ? !courseId.equals(lesson.courseId) : lesson.courseId != null) return false;
        if (teacherId != null ? !teacherId.equals(lesson.teacherId) : lesson.teacherId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = lessonId;
        result = 31 * result + (lessonStartDate != null ? lessonStartDate.hashCode() : 0);
        result = 31 * result + (lessonEndDate != null ? lessonEndDate.hashCode() : 0);
        result = 31 * result + (courseId != null ? courseId.hashCode() : 0);
        result = 31 * result + (teacherId != null ? teacherId.hashCode() : 0);
        return result;
    }
}
