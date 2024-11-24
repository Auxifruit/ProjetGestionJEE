package com.example.projetjee.model.entities;

import jakarta.persistence.*;

@Entity
public class Userstovalidate {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "userToValidateId")
    private int userToValidateId;
    @Basic
    @Column(name = "userToValidatePassword")
    private String userToValidatePassword;
    @Basic
    @Column(name = "userToValidateLastName")
    private String userToValidateLastName;
    @Basic
    @Column(name = "userToValidateName")
    private String userToValidateName;
    @Basic
    @Column(name = "userToValidateEmail")
    private String userToValidateEmail;
    @Basic
    @Column(name = "userToValidateBirthdate")
    private String userToValidateBirthdate;
    @Basic
    @Column(name = "userToValidateRole")
    @Enumerated(EnumType.STRING)
    private Role userToValidateRole;
    @Basic
    @Column(name = "userToValidateMajorId")
    private Integer userToValidateMajorId;

    public int getUserToValidateId() {
        return userToValidateId;
    }

    public void setUserToValidateId(int userToValidateId) {
        this.userToValidateId = userToValidateId;
    }

    public String getUserToValidatePassword() {
        return userToValidatePassword;
    }

    public void setUserToValidatePassword(String userToValidatePassword) {
        this.userToValidatePassword = userToValidatePassword;
    }

    public String getUserToValidateLastName() {
        return userToValidateLastName;
    }

    public void setUserToValidateLastName(String userToValidateLastName) {
        this.userToValidateLastName = userToValidateLastName;
    }

    public String getUserToValidateName() {
        return userToValidateName;
    }

    public void setUserToValidateName(String userToValidateName) {
        this.userToValidateName = userToValidateName;
    }

    public String getUserToValidateEmail() {
        return userToValidateEmail;
    }

    public void setUserToValidateEmail(String userToValidateEmail) {
        this.userToValidateEmail = userToValidateEmail;
    }

    public String getUserToValidateBirthdate() {
        return userToValidateBirthdate;
    }

    public void setUserToValidateBirthdate(String userToValidateBirthdate) {
        this.userToValidateBirthdate = userToValidateBirthdate;
    }

    public Role getUserToValidateRole() {
        return userToValidateRole;
    }

    public void setUserToValidateRole(Role userToValidateRole) {
        this.userToValidateRole = userToValidateRole;
    }

    public Integer getUserToValidateMajorId() {
        return userToValidateMajorId;
    }

    public void setUserToValidateMajorId(Integer userToValidateMajorId) {
        this.userToValidateMajorId = userToValidateMajorId;
    }

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
