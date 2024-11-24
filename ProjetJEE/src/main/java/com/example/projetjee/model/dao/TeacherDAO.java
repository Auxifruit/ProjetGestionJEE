package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Teacher;
import com.example.projetjee.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class TeacherDAO {
    private static final String TEACHER_TABLE = "teacher";
    private static final String TEACHER_ID = "teacherId";

    public static List<Teacher> getAllTeacher() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Teacher> teachers = session.createQuery("FROM Teacher ", Teacher.class).list();
        session.close();
        return teachers;
    }

    public static boolean addTeacherInTable(Teacher teacher) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean success = false;

        try {
            tx = session.beginTransaction();
            if (!session.contains(teacher)) {
                session.persist(teacher);
            } else {
                session.merge(teacher);
            }
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

    public static void deleteTeacherFromTable(int teacherId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Teacher teacher = session.get(Teacher.class, teacherId);
        if (teacher != null) {
            session.remove(teacher);
        }
        tx.commit();
        session.close();
    }

    // send back a list of all the Course teach by the teacher
    public static List<Course> getAllDiscipline(int teacherId) {
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
    
    public static List<Classes> getAllClasses(int teacherId) {
        List<Classes> classesList = new ArrayList<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = """
            SELECT DISTINCT c 
            FROM Classes c 
            JOIN Lessonclass lc ON c.classId = lc.lessonClassId 
            JOIN Lesson l ON lc.lessonClassId = l.lessonId 
            WHERE l.teacherId = :teacherId
            """;

            Query<Classes> query = session.createQuery(hql, Classes.class);
            query.setParameter("teacherId", teacherId);

            classesList = query.getResultList();

        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des classes : " + e.getMessage());
            e.printStackTrace();
        }

        return classesList;
    }
}
