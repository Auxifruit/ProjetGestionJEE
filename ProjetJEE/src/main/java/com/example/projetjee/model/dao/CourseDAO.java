package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Course;
import com.example.projetjee.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CourseDAO {

    public static List<Course> getAllCourses() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.createQuery("FROM Course", Course.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Course getCourse(int courseId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.get(Course.class, courseId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean addCourse(String courseName, int subjectId) {
        if (courseName == null || courseName.isEmpty() || subjectId <= 0) {
            return false;
        }

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Course course = new Course();
            course.setCourseName(courseName);
            course.setSubjectId(subjectId);

            session.save(course);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteCourse(int courseId) {
        if (courseId <= 0) {
            return false;
        }

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Course course = session.get(Course.class, courseId);
            if (course != null) {
                session.delete(course);
                transaction.commit();
                return true;
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return false;
    }

    public static boolean modifyCourse(int courseId, String newCourseName, int newSubjectId) {
        if (courseId <= 0 || newCourseName == null || newCourseName.isEmpty() || newSubjectId <= 0) {
            return false;
        }

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Course course = session.get(Course.class, courseId);
            if (course != null) {
                course.setCourseName(newCourseName);
                course.setSubjectId(newSubjectId);

                session.update(course);
                transaction.commit();
                return true;
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return false;
    }

    public static String getCourseName(int courseId) {
        Course course = getCourse(courseId);
        return course != null ? course.getCourseName() : null;
    }

    public static int getCourseSubjectId(int courseId) {
        Course course = getCourse(courseId);
        return course != null ? course.getSubjectId() : -1;
    }

    public static boolean isCourseInTableByNameAndSubject(String courseName, int subjectId) {
        if (courseName == null || courseName.isEmpty() || subjectId <= 0) {
            return false;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT COUNT(c) FROM Course c WHERE c.courseName = :courseName AND c.subjectId = :subjectId";
            Long count = session.createQuery(hql, Long.class)
                    .setParameter("courseName", courseName)
                    .setParameter("subjectId", subjectId)
                    .uniqueResult();

            return count != null && count > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isCourseInTableById(int courseId) {
        if (courseId <= 0) {
            return false;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT COUNT(c) FROM Course c WHERE c.courseId = :courseId";
            Long count = session.createQuery(hql, Long.class)
                    .setParameter("courseId", courseId)
                    .uniqueResult();

            return count != null && count > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
