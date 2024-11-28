package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Course;
import com.example.projetjee.model.entities.Lesson;
import com.example.projetjee.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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

    public static Course getCourseById(int courseId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.get(Course.class, courseId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean addCourseInTable(Course course) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean success = false;

        try {
            tx = session.beginTransaction();
            session.persist(course);
            tx.commit();
            success = true;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return success;
    }

    public static boolean deleteCourseFromTable(int courseId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean success = false;

        try {
            tx = session.beginTransaction();
            Lesson lesson = session.get(Lesson.class, courseId);
            if (lesson != null) {
                session.remove(lesson);
                success = true;
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return success;
    }

    public static boolean modifyCourseFromTable(Course course) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean success = false;

        try {
            tx = session.beginTransaction();
            session.merge(course);
            tx.commit();
            success = true;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return success;
    }

    public static String getCourseName(int courseId) {
        Course course = getCourseById(courseId);
        return course != null ? course.getCourseName() : null;
    }

    public static int getCourseSubjectId(int courseId) {
        Course course = getCourseById(courseId);
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

    public static List<Course> getCoursesBySubjectId(int subjectId) {
        if (subjectId <= 0) {
            throw new IllegalArgumentException("subjectId doit être supérieur à 0");
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // HQL
            String hql = """
            SELECT c
            FROM Course c
            WHERE c.subjectId = :subjectId
        """;

            Query<Course> query = session.createQuery(hql, Course.class);
            query.setParameter("subjectId", subjectId);

            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération des cours pour le subjectId: " + subjectId, e);
        }
    }

}
