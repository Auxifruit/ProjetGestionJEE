package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Userstovalidate;
import com.example.projetjee.util.HibernateUtil;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserToValidateDAO {
    public static List<Userstovalidate> getAllUserstovalidate() {
        List<Userstovalidate> userstovalidateList = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            String hql = "FROM Userstovalidate ";

            Query<Userstovalidate> query = session.createQuery(hql, Userstovalidate.class);

            userstovalidateList = query.list();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return userstovalidateList;
    }

    public static Userstovalidate getUserToValidateById(int userstovalidateId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Userstovalidate userstovalidate = session.get(Userstovalidate.class, userstovalidateId);
        session.close();
        return userstovalidate;
    }

    public static String addUserstovalidateInTable(Userstovalidate userstovalidate) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.persist(userstovalidate);
            tx.commit();
        } catch (PersistenceException e) {
            return "Erreur : Le mail existe déjà.";
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return "Erreur : Erreur lors de l'inscription.";
        } finally {
            session.close();
        }

        return null;
    }

    public static boolean deleteUserstovalidateFromTable(int userstovalidateId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean success = false;

        try {
            tx = session.beginTransaction();
            Userstovalidate userstovalidate = session.get(Userstovalidate.class, userstovalidateId);
            if (userstovalidate != null) {
                session.remove(userstovalidate);
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
}
