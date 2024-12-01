package com.example.projetjee.model.entities;

import jakarta.persistence.*;

/**
 * Entity representing a Course in the system.
 */
@Entity
@Table(name = "course")
public class Course {
    /**
     * The unique identifier for a course.
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "courseId")
    private int courseId;

    /**
     * The name of the course.
     */
    @Column(name = "courseName", unique = true)
    private String courseName;

    /**
     * The identifier for the subject associated with the course.
     */
    @Column(name = "subjectId")
    private Integer subjectId;

    /**
     * Gets the unique identifier of the course.
     *
     * @return the course ID.
     */
    public int getCourseId() {
        return courseId;
    }

    /**
     * Sets the unique identifier of the course.
     *
     * @param courseId the course ID to set.
     */
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    /**
     * Gets the name of the course.
     *
     * @return the course name.
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Sets the name of the course.
     *
     * @param courseName the course name to set.
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * Gets the identifier for the subject associated with the course.
     *
     * @return the subject ID.
     */
    public Integer getSubjectId() {
        return subjectId;
    }

    /**
     * Sets the identifier for the subject associated with the course.
     *
     * @param subjectId the subject ID to set.
     */
    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    /**
     * Checks if this course is equal to another object.
     *
     * @param o the object to compare to.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (courseId != course.courseId) return false;
        if (courseName != null ? !courseName.equals(course.courseName) : course.courseName != null) return false;
        if (subjectId != null ? !subjectId.equals(course.subjectId) : course.subjectId != null) return false;

        return true;
    }

    /**
     * Computes the hash code for this course.
     *
     * @return the hash code of the course.
     */
    @Override
    public int hashCode() {
        int result = courseId;
        result = 31 * result + (courseName != null ? courseName.hashCode() : 0);
        result = 31 * result + (subjectId != null ? subjectId.hashCode() : 0);
        return result;
    }
}