package com.example.projetjee.model.dao;

import com.example.projetjee.model.entities.*;
import com.example.projetjee.util.HibernateUtil;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDAO {
    public static List<Users> getAllUsers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.createQuery("FROM Users ", Users.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Users> getAllUsersByFilter(Role roleFilter) {
        List<Users> userList = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            String hql = "FROM Users";

            if (roleFilter != null) {
                hql += " WHERE userRole = :userRole";
            }

            Query<Users> query = session.createQuery(hql, Users.class);

            if (roleFilter != null) {
                query.setParameter("userRole", roleFilter);
            }

            userList = query.list();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return userList;
    }


    public static Users getUserById(int userId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Users user = session.get(Users.class, userId);
        session.close();
        return user;
    }

    public static String addUserInTable(Users user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.persist(user);
            tx.commit();
        } catch (PersistenceException e) {
            return "Erreur : Le mail existe déjà.";
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return "Erreur : Erreur lors de l'inscription.";
        } finally {
            session.close();
        }

        return null;
    }

    public static boolean deleteUserFromTable(int userId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean success = false;

        try {
            tx = session.beginTransaction();
            Users user = session.get(Users.class, userId);
            if (user != null) {
                session.remove(user);
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

    public static String modifyUserFromTable(Users user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.merge(user);
            tx.commit();
        } catch (PersistenceException e) {
            return "Erreur : Le mail existe déjà.";
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return "Erreur : Erreur lors de la modification des informations.";
        } finally {
            session.close();
        }

        return null;
    }

    public static Users getUserByEmail(String mail) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Users user = session.createQuery("from Users where userEmail = :mail", Users.class).setParameter("mail", mail).getSingleResultOrNull();
        session.close();
        return user;
    }

    /**
     * Method to get the user's class by his id
     * @return the user's class
     */
    public static List<Map<String, Object>> getAllUsersWithClasses() {
        List<Map<String, Object>> usersWithClasses = new ArrayList<>();

        // HQL query to fetch the user and class details
        String hql = "SELECT u.userId, u.userName, u.userLastName, u.userEmail, u.userBirthdate, c.classId, c.className " +
                "FROM Users u " +
                "JOIN Student s ON u.userId = s.studentId " +
                "JOIN Classes c ON s.classId = c.classId";

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

    public static boolean modifyUserRole(Users user, Role oldUserRole, Role newUserRole) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean success = false;

        try {
            tx = session.beginTransaction();
            int userID = user.getUserId();

            // Modifier le rôle de l'utilisateur
            user.setUserRole(newUserRole);
            UserDAO.modifyUserFromTable(user);

            switch (newUserRole) {
                case student:
                    Student student = new Student();
                    student.setStudentId(userID);
                    student.setClassId(null);
                    student.setMajorId(null);

                    StudentDAO.addStudentInTable(student);
                    break;
                case teacher:
                    Teacher teacher = new Teacher();
                    teacher.setTeacherId(userID);

                    TeacherDAO.addTeacherInTable(teacher);
                    break;
                case administrator:
                    Administrator admin = new Administrator();
                    admin.setAdministratorId(userID);

                    AdminDAO.addAdminInTable(admin);
                    break;
            }

            // Supprimer l'utilisateur de l'ancien rôle
            switch (oldUserRole) {
                case student:
                    StudentDAO.deleteStudentFromTable(userID);
                    break;
                case teacher:
                    TeacherDAO.deleteTeacherFromTable(userID);
                    break;
                case administrator:
                    AdminDAO.deleteAdminFromTable(userID);
                    break;
            }

            tx.commit();
            success = true;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();  // Rollback en cas d'erreur
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return success;
    }

    public static boolean isEmailInTable(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        boolean isIn = false;

        String hql = "SELECT COUNT(u) FROM Users u WHERE u.userEmail = :email";

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("email", email);

            long count = query.uniqueResult();

            isIn = count > 0;

            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
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
