package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Grade;
import com.example.projetjee.util.HibernateUtil;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class GradeDAO {

    public static List<Grade> getAllGrade() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.createQuery("FROM Grade", Grade.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Grade getGradeById(int gradeId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.get(Grade.class, gradeId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String addGradeInTable(Grade grade) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.persist(grade);
            tx.commit();
        } catch (PersistenceException e) {
            return "Erreur : La note existe déjà.";
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return "Erreur : Erreur lors de l'ajout de la note.";
        } finally {
            session.close();
        }

        return null;
    }

    public static boolean deleteGradeFromTable(int gradeId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean success = false;

        try {
            tx = session.beginTransaction();
            Grade grade = session.get(Grade.class, gradeId);
            if (grade != null) {
                session.remove(grade);
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

    public static String modifyGradeFromTable(Grade grade) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.merge(grade);
            tx.commit();
        } catch (PersistenceException e) {
            return "Erreur : La note existe déjà.";
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return "Erreur : Erreur lors de la modification de la note";
        } finally {
            session.close();
        }

        return null;
    }
    
    public static List<Grade> getGradesByTeacherAndClass(int teacherId, int classId) {
        if (teacherId <= 0 || classId <= 0) {
            throw new IllegalArgumentException("Teacher ID and Class ID must be greater than 0.");
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            String hql = """
            SELECT g.gradeId, g.gradeValue AS valeurNote, g.gradeCoefficient AS coefficientNote,
                   g.studentId AS idEtudiant, sub.subjectName AS nomMatiere
            FROM Grade g
            INNER JOIN Course c ON g.courseId = c.courseId
            INNER JOIN Subjects sub ON c.subjectId = sub.subjectId
            WHERE g.teacherId = ? AND EXISTS (
                SELECT 1 FROM Student s WHERE s.studentId = g.studentId AND s.classId = ?)
            """;

            return session.createQuery(hql, Grade.class)
                    .setParameter("teacherId", teacherId)
                    .setParameter("classId", classId)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean insertGrade(Grade grade) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean success = false;

        try {
            tx = session.beginTransaction();
            session.persist(grade);
            tx.commit();
            success = true;

        } catch (Exception e) {
            // In case of any error
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace(); 
        } finally {
            session.close();
        }
        return success;
    }

    public static List<Grade> getGradeByStudentId(int studentId) {
        if (studentId <= 0) {
            throw new IllegalArgumentException("studentId can't be less or equal to 0");
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // HQL
            String hql = "FROM Grade g WHERE g.studentId = :studentId";

            Query<Grade> query = session.createQuery(hql, Grade.class);
            query.setParameter("studentId", studentId);

            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur de récupération des notes pour studentID: " + studentId, e);
        }
    }
}
