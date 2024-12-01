package com.example.projetjee.model.entities;

import jakarta.persistence.*;

/**
 * Entity representing a possible role in the system.
 */
@Entity
@Table(name="possiblerole")
public class Possiblerole {

    /**
     * The unique identifier for a role.
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "roleId")
    private int roleId;

    /**
     * The name of the role.
     * The role name must be unique in the system.
     */
    @Column(name = "roleName", unique = true)
    private String roleName;

    /**
     * Gets the unique identifier of the role.
     *
     * @return the role ID.
     */
    public int getRoleId() {
        return roleId;
    }

    /**
     * Sets the unique identifier of the role.
     *
     * @param roleId the role ID to set.
     */
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    /**
     * Gets the name of the role.
     *
     * @return the role name.
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * Sets the name of the role.
     *
     * @param roleName the role name to set.
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * Checks if this role is equal to another object.
     *
     * @param o the object to compare to.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Possiblerole that = (Possiblerole) o;

        if (roleId != that.roleId) return false;
        if (roleName != null ? !roleName.equals(that.roleName) : that.roleName != null) return false;

        return true;
    }

    /**
     * Computes the hash code for this role.
     *
     * @return the hash code of the role.
     */
    @Override
    public int hashCode() {
        int result = roleId;
        result = 31 * result + (roleName != null ? roleName.hashCode() : 0);
        return result;
    }
}