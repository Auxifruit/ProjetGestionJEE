package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Course;
import com.example.projetjee.model.entities.Lesson;
import com.example.projetjee.util.HibernateUtil;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
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

    public static String addCourseInTable(Course course) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.persist(course);
            tx.commit();
        } catch (PersistenceException e) {
            return "Erreur : Le cours existe déjà.";
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return "Erreur : Erreur lors de l'ajout du cours.";
        } finally {
            session.close();
        }

        return null;
    }

    public static boolean deleteCourseFromTable(int courseId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean success = false;

        try {
            tx = session.beginTransaction();
            Course course = session.get(Course.class, courseId);
            if (course != null) {
                session.remove(course);
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

    public static String modifyCourseFromTable(Course course) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.merge(course);
            tx.commit();
        } catch (PersistenceException e) {
            return "Erreur : Le cours existe déjà.";
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return "Erreur : Erreur lors de la modification du cours";
        } finally {
            session.close();
        }

        return null;
    }

    public static String getCourseName(int courseId) {
        Course course = getCourseById(courseId);
        return course != null ? course.getCourseName() : null;
    }

    public static int getCourseSubjectId(int courseId) {
        Course course = getCourseById(courseId);
        return course != null ? course.getSubjectId() : -1;
    }

    // send back a list of all the Course teach by the teacher
    public static List<Course> getAllTeacherCourseByTeacherId(int teacherId) {
        List<Course> disciplines = new ArrayList<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = """
            SELECT DISTINCT c 
            FROM Course c 
            JOIN Lesson l ON c.courseId = l.courseId 
            WHERE l.teacherId = :teacherId
            """;

            Query<Course> query = session.createQuery(hql, Course.class);
            query.setParameter("teacherId", teacherId);

            disciplines = query.getResultList();

        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des disciplines : " + e.getMessage());
            e.printStackTrace();
        }

        return disciplines;
    }

    // list the course from the same subject
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
