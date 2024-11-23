package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Lesson;
import com.example.projetjee.model.entities.Lessonclass;
import com.example.projetjee.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class LessonClassesDAO {
    private static String LESSON_CLASS_TABLE = "LessonClass";
    private static String LESSON_CLASS_ID = "lessonClassId";
    private static String LESSON_ID = "lessonId";
    private static String CLASS_ID = "classId";

    public static List<Lessonclass> getAllLessonClasses() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Lessonclass> lessonclasses = session.createQuery("FROM " + LESSON_CLASS_TABLE, Lessonclass.class).list();
        session.close();
        return lessonclasses;
    }

    public static Lessonclass getLessonClassByLessonIdAndClassId(int lessonId, int classId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Lessonclass lessonclass = session.createQuery("from " + LESSON_CLASS_TABLE + " where " + LESSON_ID + " = :lessonId AND " + CLASS_ID + " = :classId", Lessonclass.class).setParameter("lessonId", lessonId).setParameter("classId", classId).getSingleResultOrNull();
        session.close();
        return lessonclass;
    }

    public static boolean addLessonClassInTable(Lessonclass lessonclass) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean success = false;

        try {
            tx = session.beginTransaction();
            session.persist(lessonclass);
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

    public static boolean deleteLessonclassFromTable(int lessonclassId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean success = false;

        try {
            tx = session.beginTransaction();
            Lesson lesson = session.get(Lesson.class, lessonclassId);
            if (lesson != null) {
                session.remove(lesson);
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

    public static boolean canClassParticipate(int classId, int lessonId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        boolean canParticipate = false;

        try {
            // Récupérer la leçon pour les dates
            Lesson lesson = session.find(Lesson.class, lessonId);
            if (lesson == null) {
                return false; // Si la leçon n'existe pas, impossible de vérifier
            }

            String hql = """
            SELECT COUNT(l)
            FROM Lessonclass lc
            JOIN lc.lesson l
            WHERE lc.lessonClassId = :classId
              AND l.lessonId != :lessonId
              AND (
                  (l.lessonStartDate < :lessonEndDate AND l.lessonEndDate > :lessonStartDate)
                  OR (l.lessonStartDate >= :lessonStartDate AND l.lessonEndDate <= :lessonEndDate)
              )
            """;

            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("classId", classId);
            query.setParameter("lessonId", lessonId);
            query.setParameter("lessonStartDate", lesson.getLessonStartDate());
            query.setParameter("lessonEndDate", lesson.getLessonEndDate());

            Long count = query.uniqueResult();
            canParticipate = count == 0; // Pas de conflit, participation possible

        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close(); // Toujours fermer la session après utilisation
        }

        return canParticipate;
    }

}
