package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Grade;
import com.example.projetjee.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class GradeDAO {

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
        // Open a session from Hibernate session factory
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean success = false;

        try {
            // Start a transaction
            tx = session.beginTransaction();

            // Persist the grade object (insert it into the database)
            session.persist(grade);

            // Commit the transaction to apply changes
            tx.commit();
            success = true;

        } catch (Exception e) {
            // In case of any error, rollback the transaction
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace(); // Print the exception for debugging
        } finally {
            // Close the session after completing the operation
            session.close();
        }

        // Return true if the insertion was successful, otherwise false
        return success;
    }


}
