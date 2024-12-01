package com.example.projetjee.model.entities;

import jakarta.persistence.*;

/**
 * Entity representing a user in the system.
 */
@Entity
@Table(name = "users")
public class Users {
    /**
     * The unique identifier for the user.
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "userId")
    private int userId;
    /**
     * The password associated with the user.
     */
    @Basic
    @Column(name = "userPassword")
    private String userPassword;
    /**
     * The last name of the user.
     */
    @Basic
    @Column(name = "userLastName")
    private String userLastName;
    /**
     * The first name of the user.
     */
    @Basic
    @Column(name = "userName")
    private String userName;
    /**
     * The email address of the user.
     */
    @Basic
    @Column(name = "userEmail")
    private String userEmail;
    /**
     * The birthdate of the user.
     */
    @Basic
    @Column(name = "userBirthdate")
    private String userBirthdate;
    /**
     * The role assigned to the user.
     * This is an enumerated type that specifies the user's role.
     */
    @Basic
    @Column(name = "userRole")
    @Enumerated(EnumType.STRING)
    private Role userRole;

    /**
     * Gets the unique identifier for the user.
     *
     * @return the user ID.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the unique identifier for the user.
     *
     * @param userId the user ID to set.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the password of the user.
     *
     * @return the user's password.
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * Sets the password for the user.
     *
     * @param userPassword the password to set.
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * Gets the last name of the user.
     *
     * @return the user's last name.
     */
    public String getUserLastName() {
        return userLastName;
    }

    /**
     * Sets the last name of the user.
     *
     * @param userLastName the last name to set.
     */
    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    /**
     * Gets the first name of the user.
     *
     * @return the user's first name.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the first name of the user.
     *
     * @param userName the first name to set.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the email address of the user.
     *
     * @return the user's email address.
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * Sets the email address of the user.
     *
     * @param userEmail the email address to set.
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    /**
     * Gets the birthdate of the user.
     *
     * @return the user's birthdate.
     */
    public String getUserBirthdate() {
        return userBirthdate;
    }

    /**
     * Sets the birthdate of the user.
     *
     * @param userBirthdate the birthdate to set.
     */
    public void setUserBirthdate(String userBirthdate) {
        this.userBirthdate = userBirthdate;
    }

    /**
     * Gets the role of the user.
     *
     * @return the user's role.
     */
    public Role getUserRole() {
        return userRole;
    }

    /**
     * Sets the role of the user.
     *
     * @param userRole the role to set.
     */
    public void setUserRole(Role userRole) {
        this.userRole = userRole;
    }

    /**
     * Compares this user with another object.
     *
     * @param o the object to compare with.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Users users = (Users) o;

        if (userId != users.userId) return false;
        if (userPassword != null ? !userPassword.equals(users.userPassword) : users.userPassword != null) return false;
        if (userLastName != null ? !userLastName.equals(users.userLastName) : users.userLastName != null) return false;
        if (userName != null ? !userName.equals(users.userName) : users.userName != null) return false;
        if (userEmail != null ? !userEmail.equals(users.userEmail) : users.userEmail != null) return false;
        if (userBirthdate != null ? !userBirthdate.equals(users.userBirthdate) : users.userBirthdate != null)
            return false;
        if (userRole != null ? !userRole.equals(users.userRole) : users.userRole != null) return false;

        return true;
    }

    /**
     * Returns the hash code of this user.
     *
     * @return the hash code of the user.
     */
    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (userPassword != null ? userPassword.hashCode() : 0);
        result = 31 * result + (userLastName != null ? userLastName.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (userEmail != null ? userEmail.hashCode() : 0);
        result = 31 * result + (userBirthdate != null ? userBirthdate.hashCode() : 0);
        result = 31 * result + (userRole != null ? userRole.hashCode() : 0);
        return result;
    }
}
