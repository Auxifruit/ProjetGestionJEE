package com.example.projetjee.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "lessonclass")
public class Lessonclass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lessonClassId")
    private int lessonClassId;

    @JoinColumn(name = "lessonId", nullable = false)
    private Integer lessonId;

    @JoinColumn(name = "classesId", nullable = false)
    private Integer classId;

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

}
