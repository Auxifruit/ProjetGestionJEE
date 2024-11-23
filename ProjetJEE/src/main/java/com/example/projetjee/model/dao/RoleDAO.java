package com.example.projetjee.model.dao;

import com.example.projetjee.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class RoleDAO {
    private static final String ROLE_TABLE = "possibleRole";
    private static final String ROLE_ID = "roleId";
    private static final String ROLE_NAME = "roleName";

    public static String getRoleNameById(int roleId) {
        String roleName = null;
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            // Requête HQL pour récupérer le nom du rôle
            String hql = "SELECT r.roleName FROM Possiblerole r WHERE r.roleId = :roleId";
            Query<String> query = session.createQuery(hql, String.class);
            query.setParameter("roleId", roleId);

            roleName = query.uniqueResult(); // Retourne le nom du rôle ou null si non trouvé
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close(); // Toujours fermer la session après utilisation
        }

        return roleName != null ? roleName : " ";
    }

}
