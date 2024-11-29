package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Classes;
import com.example.projetjee.model.entities.Lesson;
import com.example.projetjee.model.entities.Lessonclass;
import com.example.projetjee.model.entities.Student;
import com.example.projetjee.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class LessonClassesDAO {

    public static List<Lessonclass> getAllLessonClasses() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Lessonclass> lessonclasses = session.createQuery("FROM Lessonclass ", Lessonclass.class).list();
        session.close();
        return lessonclasses;
    }

    public static List<Classes> getLessonClasses(int lessonId) {
        List<Classes> availableClasses = new ArrayList<>();

        // Ouverture d'une session Hibernate
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            // Requête HQL pour récupérer les classes associées à un lessonId
            String hql = "SELECT classId FROM Lessonclass WHERE lessonId = :lessonId";

            // Création de la requête Hibernate
            Query<Integer> query = session.createQuery(hql, Integer.class);

            // Définition du paramètre
            query.setParameter("lessonId", lessonId);

            // Exécution de la requête et récupération des résultats
            List<Integer> classIdList = query.list();

            for(Integer classId : classIdList) {
                if(classId != null) {
                    availableClasses.add(ClasseDAO.getClasseById(classId));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return availableClasses;
    }

    public static Lessonclass getLessonClassByLessonIdAndClassId(int lessonId, int classId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Lessonclass lessonclass = session.createQuery("From Lessonclass where lessonId = :lessonId AND classId = :classId", Lessonclass.class).setParameter("lessonId", lessonId).setParameter("classId", classId).getSingleResultOrNull();
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
            Lessonclass lessonclass = session.get(Lessonclass.class, lessonclassId);
            if (lessonclass != null) {
                session.remove(lessonclass);
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
            Lesson lesson = LessonDAO.getLessonById(lessonId);

            String hql = """
                SELECT COUNT(*)
                FROM Lessonclass lc
                JOIN Lesson l ON lc.lessonId = l.lessonId
                WHERE lc.classId = :classId
                  AND l.lessonId != :excludedLessonId
                  AND (
                        (l.lessonStartDate < :endDate AND l.lessonEndDate > :startDate)
                      )
                """;

            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("classId", classId);
            query.setParameter("excludedLessonId", lessonId);
            query.setParameter("startDate", lesson.getLessonStartDate());
            query.setParameter("endDate", lesson.getLessonEndDate());

            Long count = query.uniqueResult();
            canParticipate = count == 0;

        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return canParticipate;
    }

    public static List<Student> getStudentsByClassId(int classId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Student> students = new ArrayList<>();

        try {
            // Requête HQL pour récupérer les étudiants associés à une classe (classId)
            String hql = """
        SELECT s
        FROM Student s
        WHERE s.classId = :classId
        """;

            // Créer la requête
            Query<Student> query = session.createQuery(hql, Student.class);

            // Définir le paramètre classId
            query.setParameter("classId", classId);

            // Exécuter la requête et récupérer les résultats
            students = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();  // Toujours fermer la session après utilisation
        }

        return students;
    }

    public static List<Student> getStudentsByLessonId(int lessonId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Student> students = new ArrayList<>();

        try {
            // Requête HQL pour récupérer les étudiants associés à une séance (lessonId)
            String hql = """
        SELECT s
        FROM Student s
        JOIN Lessonclass lc ON lc.classId = s.classId
        WHERE lc.lessonId = :lessonId
        """;

            // Créer la requête
            Query<Student> query = session.createQuery(hql, Student.class);

            // Définir le paramètre lessonId
            query.setParameter("lessonId", lessonId);

            // Exécuter la requête et récupérer les résultats
            students = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();  // Toujours fermer la session après utilisation
        }

        return students;
    }

    public static String getLessonNameById(int lessonId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String lessonName = null;

        try {
            // Récupérer la leçon par son ID
            Lesson lesson = session.get(Lesson.class, lessonId);
            if (lesson != null && CourseDAO.getCourseById(lesson.getCourseId()) != null) {
                lessonName = CourseDAO.getCourseById(lesson.getCourseId()).getCourseName(); // Récupérer le nom du cours (matière)
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return lessonName;
    }

}
