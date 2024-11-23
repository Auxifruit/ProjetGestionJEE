package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.Student;
import com.example.projetjee.model.entities.Teacher;
import com.example.projetjee.model.entities.Users;
import com.example.projetjee.util.HibernateUtil;
import com.example.progetjee.model.entities.Administrator;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDAO {
    private static final String USER_TABLE = "users";
    private static final String USER_ID = "userId";
    private static final String USER_PASSWORD = "userPassword";
    private static final String USER_LASTNAME = "userLastName";
    private static final String USER_NAME = "userName";
    private static final String USER_EMAIL = "userEmail";
    private static final String USER_BIRTHDATE = "userBirthdate";
    private static final String ROLE_ID = "roleId";

    public static List<Users> getAllUsers() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Users> users = session.createQuery("FROM " + USER_TABLE, Users.class).list();
        session.close();
        return users;
    }


    public static Users getUserById(int userId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Users user = session.get(Users.class, userId);
        session.close();
        return user;
    }

    public static void saveUser(Users user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(user);
        tx.commit();
        session.close();
    }

    public static void deleteUser(int userId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Users user = session.get(Users.class, userId);
        if (user != null) {
            session.remove(user);
        }
        tx.commit();
        session.close();
    }

    public static void modifyUser(Users user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.merge(user);
        tx.commit();
        session.close();
    }

    /**
     * Method to get the user's class by his id
     * @return the user's class
     */
    public static List<Map<String, Object>> getAllUsersWithClasses() {
        List<Map<String, Object>> usersWithClasses = new ArrayList<>();

        // HQL query to fetch the user and class details
        String hql = "SELECT u.userId, u.userName, u.userLastName, u.userEmail, u.userBirthdate, c.classId, c.className " +
                "FROM " + USER_TABLE + " u " +
                "JOIN u.students s " +
                "JOIN s.class c";  // Assuming User has a relationship to Student, and Student has a relationship to Class

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Create the query
            Query<Object[]> query = session.createQuery(hql, Object[].class);
            List<Object[]> resultList = query.getResultList();

            // Loop through the results and map them into a list of maps
            for (Object[] result : resultList) {
                Map<String, Object> userWithClass = new HashMap<>();
                userWithClass.put("userId", result[0]);
                userWithClass.put("userName", result[1]);
                userWithClass.put("userLastName", result[2]);
                userWithClass.put("userEmail", result[3]);
                userWithClass.put("userBirthdate", result[4]);
                userWithClass.put("classId", result[5]);
                userWithClass.put("className", result[6]);
                usersWithClasses.add(userWithClass);
            }

            // Commit the transaction
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();  // Rollback the transaction if an error occurs
            }
            System.out.println("Error in getAllUsersWithClasses:");
            e.printStackTrace();
        } finally {
            session.close();  // Always close the session to release resources
        }

        return usersWithClasses;
    }

    /**
     * Method to get all distinct class names from the Classes table.
     * @return a list of class names.
     */
    public static List<String> getAllClassNames() {
        List<String> classNames = new ArrayList<>();

        // HQL query to fetch distinct class names
        String hql = "SELECT DISTINCT c.className FROM Classes c";  // Nous utilisons l'entité "Class"

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Exécuter la requête HQL pour récupérer les noms de classe distincts
            Query<String> query = session.createQuery(hql, String.class);
            classNames = query.getResultList();  // Récupérer les résultats de la requête

            // Commit de la transaction
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();  // Rollback de la transaction en cas d'erreur
            }
            System.out.println("Error in getAllClassNames:");
            e.printStackTrace();
        } finally {
            session.close();  // Toujours fermer la session pour libérer les ressources
        }

        return classNames;
    }

    public static boolean modifyUserRole(int userID, int oldRoleID, int newRoleID) {
        try {
            Connection connection = DatabaseManager.getConnection();

            String query = "UPDATE " + USER_TABLE + " SET " + ROLE_ID + " = ? WHERE " + USER_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, newRoleID);
            preparedStatement.setInt(2, userID);

            preparedStatement.executeUpdate();

            switch (oldRoleID) {
                case 1:
                    StudentDAO.deleteStudentFromTable(userID);
                    break;
                case 2:
                    TeacherDAO.deleteTeacherFromTable(userID);
                    break;
                case 3:
                    AdminDAO.deleteAdminById(userID);
                    break;
            }

            switch (newRoleID) {
                case 1:
                    Student student = new Student();
                    student.setStudentId(userID);

                    StudentDAO.addStudentInTable(student);
                    break;
                case 2:
                    Teacher teacher = new Teacher();
                    teacher.setTeacherId(userID);

                    TeacherDAO.addTeacherInTable(teacher);
                    break;
                case 3:
                    Administrator admin = new Administrator();
                    admin.setAdministratorId(userID);

                    AdminDAO.addAdminInTable(userID);
                    break;
            }
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static boolean isEmailInTable(String email) {
        if (email == null || email.isEmpty()) {
            return false; // Si l'email est null ou vide, on retourne false
        }

        boolean isIn = false;  // Par défaut, on suppose que l'email n'existe pas

        // HQL query pour vérifier si l'email existe dans la table Users
        String hql = "SELECT COUNT(u) FROM Users u WHERE u.userEmail = :email";

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Créer la requête HQL pour compter les utilisateurs avec l'email donné
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("email", email);  // Paramétrage de l'email dans la requête

            // Récupérer le nombre d'utilisateurs avec cet email
            long count = query.uniqueResult();  // Cela renvoie un seul résultat (le nombre d'occurrences)

            // Si le compte est supérieur à 0, l'email existe dans la table
            isIn = count > 0;

            // Commit de la transaction
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();  // Rollback de la transaction en cas d'erreur
            }
            System.out.println("Error in isEmailInTable:");
            e.printStackTrace();
        } finally {
            session.close();  // Toujours fermer la session pour libérer les ressources
        }

        return isIn;
    }

    public static Integer userConnection(String userEmail, String userPassword) {
        Integer userId = null;
        Session session = null;

        try {
            // Ouvrir une session Hibernate
            session = HibernateUtil.getSessionFactory().openSession();

            // Créer la requête HQL pour rechercher un utilisateur par email et mot de passe
            String hql = "SELECT u.userId FROM Users u WHERE u.userEmail = :email AND u.userPassword = :password";

            // Créer la requête
            Query<Integer> query = session.createQuery(hql, Integer.class);
            query.setParameter("email", userEmail); // Paramétrer l'email
            query.setParameter("password", userPassword); // Paramétrer le mot de passe

            // Exécuter la requête et obtenir le résultat
            userId = query.uniqueResult(); // Récupérer l'ID de l'utilisateur

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) {
                session.close(); // Fermer la session pour libérer les ressources
            }
        }
        return userId;
    }
}