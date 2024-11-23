package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Administrator;
import com.example.projetjee.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class AdminDAO {

    public static void deleteAdminFromTable(int adminID) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Administrator admin = session.get(Administrator.class, adminID);
            if (admin != null) {
                session.delete(admin);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public static boolean addAdminInTable(Administrator administrator) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean success = false;

        try {
            tx = session.beginTransaction();
            if (!session.contains(administrator)) {
                session.persist(administrator);
            } else {
                session.merge(administrator);
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
}
