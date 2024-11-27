package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Classes;
import com.example.projetjee.model.entities.Course;
import com.example.projetjee.model.entities.Teacher;
import com.example.projetjee.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
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

}
