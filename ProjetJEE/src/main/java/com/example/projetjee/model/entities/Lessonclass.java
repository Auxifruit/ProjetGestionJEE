package com.example.projetjee.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "lessonclass")
public class Lessonclass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lessonClassId")
    private int lessonClassId;

    @ManyToOne
    @JoinColumn(name = "lessonId", nullable = false)
    private Lesson lesson;

    @ManyToOne
    @JoinColumn(name = "classesId", nullable = false)
    private Classes classes;

    // Getters et Setters
    public int getId() {
        return lessonClassId;
    }

    public void setId(int lessonClassId) {
        this.lessonClassId = lessonClassId;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public Classes getClasses() {
        return classes;
    }

    public void setClass(Classes classes) {
        this.classes = classes;
    }
}
