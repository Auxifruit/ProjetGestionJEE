package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Major;
import com.example.projetjee.model.entities.Student;
import com.example.projetjee.model.entities.Users;
import com.example.projetjee.util.HibernateUtil;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private static final String STUDENT_TABLE = "student";
    private static final String STUDENT_ID = "studentId";
    private static final String CLASS_ID = "classId";
    private static final String MAJOR_ID = "majorId";

    public static List<Student> getAllStudent() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Student> students = session.createQuery("FROM Student ", Student.class).list();
        session.close();
        return students;
    }

    public static boolean addStudentInTable(Student student) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean success = false;

        System.out.println("addStudent : " + student.getStudentId());

        try {
            tx = session.beginTransaction();
            if (student.getStudentId() == 0) {
                session.persist(student);
            } else {
                session.merge(student);
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

    public static boolean deleteStudentFromTable(int studentId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean success = false;

        try {
            tx = session.beginTransaction();
            Student student = session.get(Student.class, studentId);
            if (student != null) {
                session.remove(student);
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

    // this is an use for a teacher and need a version without teacherID for student or admin i guess
    public static List<Users> getStudentsByDisciplineAndClass(int courseId, int classId, int teacherID) {
        List<Users> users = new ArrayList<>();
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            System.out.println("flag3");

            // HQL pour récupérer les étudiants
            String hql = """ 
            SELECT u.userId, u.userName, u.userLastName, u.userEmail 
            FROM Users u 
            JOIN Student s ON u.userId = s.studentId 
            JOIN Classes c ON s.classId = c.classId 
            JOIN Lessonclass lc ON c.classId = lc.lessonClassId 
            JOIN Lesson l ON lc.lessonClassId = l.lessonId 
            JOIN Course co ON l.courseId = co.courseId 
            WHERE co.courseId = :courseId 
              AND c.classId = :classId 
              AND u.roleId = 1 
              AND l.teacherId = :teacherID
        """;

            // Utilisation d'Object[] comme type de résultat
            Query<Object[]> query = session.createQuery(hql);
            query.setParameter("courseId", courseId);
            query.setParameter("classId", classId);
            query.setParameter("teacherID", teacherID);

            // Récupération des résultats
            List<Object[]> resultList = query.getResultList();

            // Mapper les résultats dans des objets Users
            for (Object[] result : resultList) {
                Users user = new Users();
                user.setUserId((Integer) result[0]);
                user.setUserName((String) result[1]);
                user.setUserLastName((String) result[2]);
                user.setUserEmail((String) result[3]);
                users.add(user);
            }

            System.out.println("flag4");
            if (transaction.isActive()) {
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                try {
                    transaction.rollback();
                } catch (Exception rollbackException) {
                    System.err.println("Erreur lors du rollback : " + rollbackException.getMessage());
                }
            }
            e.printStackTrace();
            System.out.println("Error fetching students (users): " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return users;
    }

}
