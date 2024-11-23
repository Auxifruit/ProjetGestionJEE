package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Administrator;
import com.example.projetjee.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class AdminDAO {

    public static void deleteAdminById(int adminID) {
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

    public static void addAdminInTable(int adminID) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Administrator admin = new Administrator();
            admin.setAdministratorId(adminID);

            session.save(admin);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
