package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Classes;
import com.example.projetjee.model.entities.Lesson;
import com.example.projetjee.util.HibernateUtil;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class ClasseDAO {
    public static List<Classes> getAllClasses() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.createQuery("FROM Classes ", Classes.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Classes getClasseById(int classeId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Classes.class, classeId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String addClasseInTable(Classes classe) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.persist(classe);
            tx.commit();
        } catch (PersistenceException e) {
            return "Erreur : La classe existe déjà.";
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return "Erreur : Erreur lors de l'ajout de la classe.";
        } finally {
            session.close();
        }

        return null;
    }

    public static boolean deleteClasseFromTable(int classId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean success = false;

        try {
            tx = session.beginTransaction();
            Classes classe = session.get(Classes.class, classId);
            if (classe != null) {
                session.remove(classe);
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

    public static String modifyClassFromTable(Classes classe) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.merge(classe);
            tx.commit();
        } catch (PersistenceException e) {
            return "Erreur : La classe existe déjà.";
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return "Erreur : Erreur lors de la modification de la classe.";
        } finally {
            session.close();
        }

        return null;
    }

    public static List<Classes> getAvailableClassesForLesson(int idLesson) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Classes c WHERE c.classId NOT IN (SELECT lc.classId FROM Lessonclass lc WHERE lc.lessonId = :idLesson)";
            Query<Classes> query = session.createQuery(hql, Classes.class);
            query.setParameter("idLesson", idLesson);

            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Classes> getAllTeacherClassByTeacherId(int teacherId) {
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
