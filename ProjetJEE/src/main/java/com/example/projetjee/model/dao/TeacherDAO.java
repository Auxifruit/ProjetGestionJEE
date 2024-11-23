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
        List<Teacher> teachers = session.createQuery("FROM " + TEACHER_TABLE, Teacher.class).list();
        session.close();
        return teachers;
    }

    public static void addTeacherInTable(Teacher teacher) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(teacher);
        tx.commit();
        session.close();
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
