package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Course;
import com.example.projetjee.model.entities.Major;
import com.example.projetjee.model.entities.Student;
import com.example.projetjee.util.HibernateUtil;
import jakarta.persistence.PersistenceException;
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

    public static Student getStudentById(int studentId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.get(Student.class, studentId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean addStudentInTable(Student student) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean success = false;

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

    public static String modifyStudentFromTable(Student student) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.merge(student);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return "Erreur : Erreur lors de la modification de l'étudiant";
        } finally {
            session.close();
        }

        return null;
    }

    public static List<Student> getStudentListWithoutClasses() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Student> studentListWithoutClasses = new ArrayList<>();

        try {
            String hql = "FROM Student s WHERE s.classId IS NULL";
            Query<Student> query = session.createQuery(hql, Student.class);

            studentListWithoutClasses = query.list();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération des étudiants sans classe.");
        }
        return studentListWithoutClasses;
    }

    public static List<Student> getStudentListFromClassesId(int classId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Student> studentListFromClasses = new ArrayList<>();

        try {
            String hql = "FROM Student s WHERE s.classId = :classId";
            Query<Student> query = session.createQuery(hql, Student.class);

            query.setParameter("classId", classId);

            studentListFromClasses = query.list();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération des étudiants de la classe.");
        }
        return studentListFromClasses;
    }

    // this is an use for a teacher and need a version without teacherID for student (or admin i guess)
    public static List<Users> getStudentsByDisciplineAndClass(int courseId, int classId, int teacherID) {
        List<Users> users = new ArrayList<>();
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            System.out.println("flag3");

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

            // use of Object[] for type of result
            Query<Object[]> query = session.createQuery(hql);
            query.setParameter("courseId", courseId);
            query.setParameter("classId", classId);
            query.setParameter("teacherID", teacherID);

            // get the result
            List<Object[]> resultList = query.getResultList();

            // map the result in object users
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
