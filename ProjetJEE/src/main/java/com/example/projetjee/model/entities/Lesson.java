package com.example.projetjee.model.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;

/**
 * Entity representing a Lesson in the system.
 */
@Entity
@Table(name="lesson")
public class Lesson {
    /**
     * The unique identifier for a lesson.
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "lessonId")
    private int lessonId;
    /**
     * The start date and time of the lesson.
     */
    @Basic
    @Column(name = "lessonStartDate")
    private Timestamp lessonStartDate;
    /**
     * The end date and time of the lesson.
     */
    @Basic
    @Column(name = "lessonEndDate")
    private Timestamp lessonEndDate;
    /**
     * The identifier of the course associated with the lesson.
     */
    @Basic
    @Column(name = "courseId")
    private Integer courseId;
    /**
     * The identifier of the teacher associated with the lesson.
     */
    @Basic
    @Column(name = "teacherId")
    private Integer teacherId;

    /*
     * Uncomment the following code to enable a Many-to-One relationship with the Course entity.
     *
     * @ManyToOne(fetch = FetchType.LAZY)
     * @JoinColumn(name = "courseId", referencedColumnName = "courseId", insertable = false, updatable = false)
     * private Course course; // Relationship to Course
     */

    /**
     * Gets the unique identifier of the lesson.
     *
     * @return the lesson ID.
     */
    public int getLessonId() {
        return lessonId;
    }

    /**
     * Sets the unique identifier of the lesson.
     *
     * @param lessonId the lesson ID to set.
     */
    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    /**
     * Gets the start date and time of the lesson.
     *
     * @return the lesson start date and time.
     */
    public Timestamp getLessonStartDate() {
        return lessonStartDate;
    }

    /**
     * Sets the start date and time of the lesson.
     *
     * @param lessonStartDate the lesson start date and time to set.
     */
    public void setLessonStartDate(Timestamp lessonStartDate) {
        this.lessonStartDate = lessonStartDate;
    }

    /**
     * Gets the end date and time of the lesson.
     *
     * @return the lesson end date and time.
     */
    public Timestamp getLessonEndDate() {
        return lessonEndDate;
    }

    /**
     * Sets the end date and time of the lesson.
     *
     * @param lessonEndDate the lesson end date and time to set.
     */
    public void setLessonEndDate(Timestamp lessonEndDate) {
        this.lessonEndDate = lessonEndDate;
    }

    /**
     * Gets the course ID associated with the lesson.
     *
     * @return the course ID.
     */
    public Integer getCourseId() {
        return courseId;
    }

    /**
     * Sets the course ID associated with the lesson.
     *
     * @param courseId the course ID to set.
     */
    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }


    /*
     * Uncomment the following code to enable a getter and setter for the Course entity.
     *
     * public Course getCourse() {
     *     return course;
     * }
     *
     * public void setCourse(Course course) {
     *     this.course = course;
     * }
     */

    /**
     * Gets the teacher ID associated with the lesson.
     *
     * @return the teacher ID.
     */
    public Integer getTeacherId() {
        return teacherId;
    }

    /**
     * Sets the teacher ID associated with the lesson.
     *
     * @param teacherId the teacher ID to set.
     */
    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    /**
     * Checks if this lesson is equal to another object.
     *
     * @param o the object to compare to.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
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

    /**
     * Computes the hash code for this lesson.
     *
     * @return the hash code of the lesson.
     */
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
