package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Subjects;
import com.example.projetjee.util.HibernateUtil;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

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

    public static String addSubjectInTable(Subjects subject) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.persist(subject);
            tx.commit();
        } catch (PersistenceException e) {
            return "Erreur : La matière existe déjà.";
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return "Erreur : Erreur lors de la création de la matière.";
        } finally {
            session.close();
        }

        return null;
    }

    public static boolean deleteSubjectFromTable(int subjectId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean success = false;

        try {
            tx = session.beginTransaction();
            Subjects subject = session.get(Subjects.class, subjectId);
            if (subject != null) {
                session.remove(subject);
                success = true;
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            success = false;
        } finally {
            session.close();
        }

        return success;
    }

    public static String modifySubjectFromTable(Subjects subject) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.merge(subject);
            tx.commit();
        } catch (PersistenceException e) {
            return "Erreur : La matière existe déjà.";
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return "Erreur : Erreur lors de la modification de la matière.";
        } finally {
            session.close();
        }

        return null;
    }
}
