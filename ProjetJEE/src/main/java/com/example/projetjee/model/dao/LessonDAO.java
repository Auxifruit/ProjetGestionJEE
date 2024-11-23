package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Lesson;
import com.example.projetjee.util.DateUtil;
import com.example.projetjee.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class LessonDAO {
    private static final String LESSON_TABLE = "Lesson";
    private static final String LESSON_ID = "lessonId";
    private static final String LESSON_START_DATE = "lessonStartDate";
    private static final String LESSON_END_DATE = "lessonEndDate";
    private static final String LESSON_COURSE_ID = "courseId";
    private static final String LESSON_TEACHER_ID = "teacherId";

    public static List<Lesson> getAllLesson() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Lesson> lessons = session.createQuery("FROM " + LESSON_TABLE, Lesson.class).list();
        session.close();
        return lessons;
    }

    public static Lesson getLessonById(int lessonId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Lesson lesson = session.get(Lesson.class, lessonId);
        session.close();
        return lesson;
    }

    public static boolean addLessonInTable(Lesson lesson) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean success = false;

        try {
            tx = session.beginTransaction();
            session.persist(lesson);
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

    public static boolean deleteLessonFromTable(int lessonId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean success = false;

        try {
            tx = session.beginTransaction();
            Lesson lesson = session.get(Lesson.class, lessonId);
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

    public static boolean modifyLessonFromTable(Lesson lesson) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean success = false;

        try {
            tx = session.beginTransaction();
            session.merge(lesson);
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

    public static List<Lesson> getStudentLessonFromId(Integer studentId) {
        List<Lesson> studentLessons = new ArrayList<>();
        Session session = null;

        try {
            // Ouvrir une session Hibernate
            session = HibernateUtil.getSessionFactory().openSession();

            // Créer la requête HQL pour récupérer les leçons associées à l'étudiant
            String hql = "SELECT l FROM Lesson l " +
                    "JOIN Lessonclass lc ON l.lessonId = lc.lessonId " +
                    "JOIN Student s ON lc.classId = s.classId " +
                    "WHERE s.studentId = :studentId";

            // Créer la query HQL
            Query<Lesson> query = session.createQuery(hql, Lesson.class);
            query.setParameter("studentId", studentId); // Définir le paramètre studentId

            // Exécuter la requête et obtenir la liste des leçons
            studentLessons = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close(); // Fermer la session Hibernate
            }
        }

        return studentLessons;
    }


    public static List<Lesson> getTeacherLessonFromId(Integer teacherId) {
        List<Lesson> teacherLesson = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            // Utilisation de HQL pour récupérer les leçons associées à un enseignant
            String hql = "FROM Lesson l WHERE l.teacherId = :teacherId";
            Query query = session.createQuery(hql);
            query.setParameter("teacherId", teacherId); // Bind du paramètre teacherId à la requête

            teacherLesson = query.list(); // Exécution de la requête et récupération des résultats

        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close(); // Toujours fermer la session après utilisation
        }

        return teacherLesson;
    }


    public static boolean isLessonPossible(Integer lessonId, int teacherId, String lessonStartDate, String lessonEndDate) {
        boolean isPossible = true;
        Session session = null;

        try {
            // Ouvrir une session Hibernate
            session = HibernateUtil.getSessionFactory().openSession();

            // Créer la requête HQL pour vérifier les conflits de leçons
            String hql = "SELECT COUNT(*) FROM Lesson l " +
                    "WHERE l.teacherId = :teacherId " +
                    "AND (:lessonId IS NULL OR l.lessonId != :lessonId) " +
                    "AND ( " +
                    "(l.lessonStartDate < :lessonEndDate AND l.lessonEndDate > :lessonStartDate) " +
                    "OR (l.lessonStartDate BETWEEN :lessonStartDate AND :lessonEndDate) " +
                    "OR (l.lessonEndDate BETWEEN :lessonStartDate AND :lessonEndDate) " +
                    ")";

            // Créer la query HQL
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("teacherId", teacherId);
            query.setParameter("lessonId", lessonId);
            query.setParameter("lessonStartDate", DateUtil.dateStringToTimestamp(lessonStartDate));
            query.setParameter("lessonEndDate", DateUtil.dateStringToTimestamp(lessonEndDate));

            // Exécuter la requête et obtenir le nombre de leçons conflictuelles
            long count = query.uniqueResult(); // Retourne un long, le nombre de résultats

            // Si le compte est 0, alors la leçon est possible
            isPossible = (count == 0);
        } catch (Exception e) {
            e.printStackTrace();
            isPossible = false; // Si une erreur se produit, retourner false
        } finally {
            if (session != null) {
                session.close(); // Fermer la session Hibernate
            }
        }

        return isPossible;
    }

}
