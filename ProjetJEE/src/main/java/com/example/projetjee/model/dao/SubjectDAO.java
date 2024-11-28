package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Subjects;
import com.example.projetjee.util.HibernateUtil;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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

    public static List<Subjects> getSubjectsByStudentId(int studentId) {
        if (studentId <= 0) {
            throw new IllegalArgumentException("studentId can't be less or equal to 0");
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // HQL
            String hql = """
                SELECT DISTINCT s 
                FROM Subjects s
                JOIN Course c ON c.subjectId = s.subjectId
                JOIN Grade g ON g.courseId = c.courseId
                WHERE g.studentId = :studentId
            """;

            Query<Subjects> query = session.createQuery(hql, Subjects.class);
            query.setParameter("studentId", studentId);

            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur de récupération des matières pour studentID: " + studentId, e);
        }
    }

    public static int getSubjectIdByGradeId(int gradeId) {
        if (gradeId <= 0) {
            throw new IllegalArgumentException("gradeId doit être supérieur à 0");
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // HQL
            String hql = """
                SELECT s.subjectId
                FROM Grade g
                JOIN Course c ON g.courseId = c.courseId
                JOIN Subjects s ON c.subjectId = s.subjectId
                WHERE g.gradeId = :gradeId
            """;

            Query<Integer> query = session.createQuery(hql, Integer.class);
            query.setParameter("gradeId", gradeId);

            // Retourne le résultat unique
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération du subjectId pour le gradeId: " + gradeId, e);
        }
    }
}
