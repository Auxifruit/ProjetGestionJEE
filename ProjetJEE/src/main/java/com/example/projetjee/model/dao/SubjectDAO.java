package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Subjects;
import com.example.projetjee.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class SubjectDAO {
    private static final String SUBJECT_TABLE = "Subjects";
    private static final String SUBJECT_ID = "subjectId";
    private static final String SUBJECT_NAME = "subjectName";

    public static List<Subjects> getAllSubjects() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Subjects> subjects = session.createQuery("FROM " + SUBJECT_TABLE, Subjects.class).list();
        session.close();
        return subjects;
    }

    public static Subjects getSubjectById(int subjectId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Subjects subject = session.get(Subjects.class, subjectId);
        session.close();
        return subject;
    }

    public static void addSubjectInTable(Subjects subject) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(subject);
        tx.commit();
        session.close();
    }

    public static void deleteSubjectFromTable(int subjectId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Subjects subject = session.get(Subjects.class, subjectId);
        if (subject != null) {
            session.remove(subject);
        }
        tx.commit();
        session.close();
    }

    public static void modifySubjectFromTable(Subjects subject) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.merge(subject);
        tx.commit();
        session.close();
    }
}
