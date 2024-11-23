package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Student;
import com.example.projetjee.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class StudentDAO {
    private static final String STUDENT_TABLE = "student";
    private static final String STUDENT_ID = "studentId";
    private static final String CLASS_ID = "classId";
    private static final String MAJOR_ID = "majorId";

    public static List<Student> getAllStudent() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Student> students = session.createQuery("FROM " + STUDENT_TABLE, Student.class).list();
        session.close();
        return students;
    }

    public static boolean addStudentInTable(Student student) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean success = false;

        try {
            tx = session.beginTransaction();
            session.persist(student);
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

    public static void deleteStudentFromTable(int studentId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Student student = session.get(Student.class, studentId);
        if (student != null) {
            session.remove(student);
        }
        tx.commit();
        session.close();
    }

}
