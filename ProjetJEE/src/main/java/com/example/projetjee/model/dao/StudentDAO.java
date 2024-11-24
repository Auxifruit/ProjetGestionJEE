package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Course;
import com.example.projetjee.model.entities.Major;
import com.example.projetjee.model.entities.Student;
import com.example.projetjee.util.HibernateUtil;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private static final String STUDENT_TABLE = "student";
    private static final String STUDENT_ID = "studentId";
    private static final String CLASS_ID = "classId";
    private static final String MAJOR_ID = "majorId";

    public static List<Student> getAllStudent() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Student> students = session.createQuery("FROM Student ", Student.class).list();
        session.close();
        return students;
    }

    public static Student getStudentById(int studentId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.get(Student.class, studentId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean addStudentInTable(Student student) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean success = false;

        try {
            tx = session.beginTransaction();
            if (student.getStudentId() == 0) {
                session.persist(student);
            } else {
                session.merge(student);
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

    public static boolean deleteStudentFromTable(int studentId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean success = false;

        try {
            tx = session.beginTransaction();
            Student student = session.get(Student.class, studentId);
            if (student != null) {
                session.remove(student);
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

    public static String modifyStudentFromTable(Student student) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.merge(student);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return "Erreur : Erreur lors de la modification de l'étudiant";
        } finally {
            session.close();
        }

        return null;
    }

    public static List<Student> getStudentListWithoutClasses() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Student> studentListWithoutClasses = new ArrayList<>();

        try {
            String hql = "FROM Student s WHERE s.classId IS NULL";
            Query<Student> query = session.createQuery(hql, Student.class);

            studentListWithoutClasses = query.list();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération des étudiants sans classe.");
        }
        return studentListWithoutClasses;
    }

    public static List<Student> getStudentListFromClassesId(int classId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Student> studentListFromClasses = new ArrayList<>();

        try {
            String hql = "FROM Student s WHERE s.classId = :classId";
            Query<Student> query = session.createQuery(hql, Student.class);

            query.setParameter("classId", classId);

            studentListFromClasses = query.list();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération des étudiants de la classe.");
        }
        return studentListFromClasses;
    }

}
