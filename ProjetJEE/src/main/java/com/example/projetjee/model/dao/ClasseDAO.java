package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Classes;
import com.example.projetjee.model.entities.Lesson;
import com.example.projetjee.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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

    public static boolean addClasseInTable(Classes classe) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean success = false;

        try {
            tx = session.beginTransaction();
            session.persist(classe);
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

    public static boolean modifyClassFromTable(Classes classe) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean success = false;

        try {
            tx = session.beginTransaction();
            session.merge(classe);
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

}
