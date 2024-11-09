package com.example.projetjee.model.entities;

import jakarta.persistence.*;

@Entity
public class Rolepossible {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idRole")
    private int idRole;
    @Basic
    @Column(name = "nomRole")
    private String nomRole;

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public String getNomRole() {
        return nomRole;
    }

    public void setNomRole(String nomRole) {
        this.nomRole = nomRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rolepossible that = (Rolepossible) o;

        if (idRole != that.idRole) return false;
        if (nomRole != null ? !nomRole.equals(that.nomRole) : that.nomRole != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idRole;
        result = 31 * result + (nomRole != null ? nomRole.hashCode() : 0);
        return result;
    }
}
