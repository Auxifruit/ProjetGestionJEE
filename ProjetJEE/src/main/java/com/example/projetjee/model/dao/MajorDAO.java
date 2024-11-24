package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Major;
import com.example.projetjee.util.HibernateUtil;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class MajorDAO {
    private static final String MAJOR_TABLE = "major";
    private static final String MAJOR_ID = "majorId";
    private static final String MAJOR_NAME = "majorName";

    public static List<Major> getAllMajor() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Major> majors = session.createQuery("FROM Major", Major.class).list();
        session.close();
        return majors;
    }

    public static Major getMajorById(int majorId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Major major = session.get(Major.class, majorId);
        session.close();
        return major;
    }

    public static String addMajorInTable(Major major) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.persist(major);
            tx.commit();
        } catch (PersistenceException e) {
            return "Erreur : La filière existe déjà.";
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return "Erreur : Erreur lors de la création de la filière.";
        } finally {
            session.close();
        }

        return null;
    }

    public static boolean deleteMajorFromTable(int majorId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean success = false;

        try {
            tx = session.beginTransaction();
            Major major = session.get(Major.class, majorId);
            if (major != null) {
                session.remove(major);
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

    public static String modifyMajorFromTable(Major major) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.merge(major);
            tx.commit();
        } catch (PersistenceException e) {
            return "Erreur : La filière existe déjà.";
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return "Erreur : Erreur de la modification de la filière.";
        } finally {
            session.close();
        }

        return null;
    }

}
