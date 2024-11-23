package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Classes;
import com.example.projetjee.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class ClasseDAO {

    public static Classes getClasse(int classeId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Classes.class, classeId);
        } catch (Exception e) {
            e.printStackTrace();
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
}
