package com.example.projetjee.model.entities;

import jakarta.persistence.*;

/**
 * Entity representing a user that needs to be validated.
 */
@Entity
public class Userstovalidate {
    /**
     * The unique identifier for the user that needs validation.
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "userToValidateId")
    private int userToValidateId;
    /**
     * The password of the user that needs validation.
     */
    @Basic
    @Column(name = "userToValidatePassword")
    private String userToValidatePassword;
    /**
     * The last name of the user that needs validation.
     */
    @Basic
    @Column(name = "userToValidateLastName")
    private String userToValidateLastName;
    /**
     * The first name of the user that needs validation.
     */
    @Basic
    @Column(name = "userToValidateName")
    private String userToValidateName;
    /**
     * The email address of the user that needs validation.
     */
    @Basic
    @Column(name = "userToValidateEmail")
    private String userToValidateEmail;
    /**
     * The birthdate of the user that needs validation.
     */
    @Basic
    @Column(name = "userToValidateBirthdate")
    private String userToValidateBirthdate;
    /**
     * The role assigned to the user that needs validation.
     * This is an enumerated type that specifies the user's role.
     */
    @Basic
    @Column(name = "userToValidateRole")
    @Enumerated(EnumType.STRING)
    private Role userToValidateRole;
    /**
     * The major ID assigned to the user that needs validation.
     */
    @Basic
    @Column(name = "userToValidateMajorId")
    private Integer userToValidateMajorId;

    /**
     * Gets the unique identifier for the user to validate.
     *
     * @return the ID of the user to validate.
     */
    public int getUserToValidateId() {
        return userToValidateId;
    }

    /**
     * Sets the unique identifier for the user to validate.
     *
     * @param userToValidateId the ID to set.
     */
    public void setUserToValidateId(int userToValidateId) {
        this.userToValidateId = userToValidateId;
    }

    /**
     * Gets the password of the user to validate.
     *
     * @return the password of the user to validate.
     */
    public String getUserToValidatePassword() {
        return userToValidatePassword;
    }

    /**
     * Sets the password for the user to validate.
     *
     * @param userToValidatePassword the password to set.
     */
    public void setUserToValidatePassword(String userToValidatePassword) {
        this.userToValidatePassword = userToValidatePassword;
    }

    /**
     * Gets the last name of the user to validate.
     *
     * @return the last name of the user to validate.
     */
    public String getUserToValidateLastName() {
        return userToValidateLastName;
    }

    /**
     * Sets the last name for the user to validate.
     *
     * @param userToValidateLastName the last name to set.
     */
    public void setUserToValidateLastName(String userToValidateLastName) {
        this.userToValidateLastName = userToValidateLastName;
    }

    /**
     * Gets the first name of the user to validate.
     *
     * @return the first name of the user to validate.
     */
    public String getUserToValidateName() {
        return userToValidateName;
    }

    /**
     * Sets the first name for the user to validate.
     *
     * @param userToValidateName the first name to set.
     */
    public void setUserToValidateName(String userToValidateName) {
        this.userToValidateName = userToValidateName;
    }

    /**
     * Gets the email address of the user to validate.
     *
     * @return the email address of the user to validate.
     */
    public String getUserToValidateEmail() {
        return userToValidateEmail;
    }

    /**
     * Sets the email address for the user to validate.
     *
     * @param userToValidateEmail the email address to set.
     */
    public void setUserToValidateEmail(String userToValidateEmail) {
        this.userToValidateEmail = userToValidateEmail;
    }

    /**
     * Gets the birthdate of the user to validate.
     *
     * @return the birthdate of the user to validate.
     */
    public String getUserToValidateBirthdate() {
        return userToValidateBirthdate;
    }

    /**
     * Sets the birthdate for the user to validate.
     *
     * @param userToValidateBirthdate the birthdate to set.
     */
    public void setUserToValidateBirthdate(String userToValidateBirthdate) {
        this.userToValidateBirthdate = userToValidateBirthdate;
    }

    /**
     * Gets the role of the user to validate.
     *
     * @return the role of the user to validate.
     */
    public Role getUserToValidateRole() {
        return userToValidateRole;
    }

    /**
     * Sets the role for the user to validate.
     *
     * @param userToValidateRole the role to set.
     */
    public void setUserToValidateRole(Role userToValidateRole) {
        this.userToValidateRole = userToValidateRole;
    }

    /**
     * Gets the major ID for the user to validate.
     *
     * @return the major ID of the user to validate.
     */
    public Integer getUserToValidateMajorId() {
        return userToValidateMajorId;
    }

    /**
     * Sets the major ID for the user to validate.
     *
     * @param userToValidateMajorId the major ID to set.
     */
    public void setUserToValidateMajorId(Integer userToValidateMajorId) {
        this.userToValidateMajorId = userToValidateMajorId;
    }

    /**
     * Compares this user to another object.
     *
     * @param o the object to compare with.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Userstovalidate that = (Userstovalidate) o;

        if (userToValidateId != that.userToValidateId) return false;
        if (userToValidatePassword != null ? !userToValidatePassword.equals(that.userToValidatePassword) : that.userToValidatePassword != null)
            return false;
        if (userToValidateLastName != null ? !userToValidateLastName.equals(that.userToValidateLastName) : that.userToValidateLastName != null)
            return false;
        if (userToValidateName != null ? !userToValidateName.equals(that.userToValidateName) : that.userToValidateName != null)
            return false;
        if (userToValidateEmail != null ? !userToValidateEmail.equals(that.userToValidateEmail) : that.userToValidateEmail != null)
            return false;
        if (userToValidateBirthdate != null ? !userToValidateBirthdate.equals(that.userToValidateBirthdate) : that.userToValidateBirthdate != null)
            return false;
        if (userToValidateRole != null ? !userToValidateRole.equals(that.userToValidateRole) : that.userToValidateRole != null)
            return false;
        if (userToValidateMajorId != null ? !userToValidateMajorId.equals(that.userToValidateMajorId) : that.userToValidateMajorId != null)
            return false;

        return true;
    }

    /**
     * Returns the hash code of this user to validate.
     *
     * @return the hash code of the user to validate.
     */
    @Override
    public int hashCode() {
        int result = userToValidateId;
        result = 31 * result + (userToValidatePassword != null ? userToValidatePassword.hashCode() : 0);
        result = 31 * result + (userToValidateLastName != null ? userToValidateLastName.hashCode() : 0);
        result = 31 * result + (userToValidateName != null ? userToValidateName.hashCode() : 0);
        result = 31 * result + (userToValidateEmail != null ? userToValidateEmail.hashCode() : 0);
        result = 31 * result + (userToValidateBirthdate != null ? userToValidateBirthdate.hashCode() : 0);
        result = 31 * result + (userToValidateRole != null ? userToValidateRole.hashCode() : 0);
        result = 31 * result + (userToValidateMajorId != null ? userToValidateMajorId.hashCode() : 0);
        return result;
    }
}
