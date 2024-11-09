package com.example.projetjee.model.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Administrateur {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @jakarta.persistence.Column(name = "emailAdmin")
    private String emailAdmin;

    public String getEmailAdmin() {
        return emailAdmin;
    }

    public void setEmailAdmin(String emailAdmin) {
        this.emailAdmin = emailAdmin;
    }

    @Basic
    @Column(name = "nomAdministrateur")
    private String nomAdministrateur;

    public String getNomAdministrateur() {
        return nomAdministrateur;
    }

    public void setNomAdministrateur(String nomAdministrateur) {
        this.nomAdministrateur = nomAdministrateur;
    }

    @Basic
    @Column(name = "prenomAdministrateur")
    private String prenomAdministrateur;

    public String getPrenomAdministrateur() {
        return prenomAdministrateur;
    }

    public void setPrenomAdministrateur(String prenomAdministrateur) {
        this.prenomAdministrateur = prenomAdministrateur;
    }

    @Basic
    @Column(name = "idUtilisateur")
    private Integer idUtilisateur;

    public Integer getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Integer idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Administrateur that = (Administrateur) o;

        if (!Objects.equals(emailAdmin, that.emailAdmin)) return false;
        if (!Objects.equals(nomAdministrateur, that.nomAdministrateur))
            return false;
        if (!Objects.equals(prenomAdministrateur, that.prenomAdministrateur))
            return false;
        return Objects.equals(idUtilisateur, that.idUtilisateur);
    }

    @Override
    public int hashCode() {
        int result = emailAdmin != null ? emailAdmin.hashCode() : 0;
        result = 31 * result + (nomAdministrateur != null ? nomAdministrateur.hashCode() : 0);
        result = 31 * result + (prenomAdministrateur != null ? prenomAdministrateur.hashCode() : 0);
        result = 31 * result + (idUtilisateur != null ? idUtilisateur.hashCode() : 0);
        return result;
    }
}
