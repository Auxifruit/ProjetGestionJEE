package com.example.projetjee.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@jakarta.persistence.IdClass(models.CoursdateprofPK.class)
public class Coursdateprof {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @jakarta.persistence.Column(name = "dateCours")
    private Timestamp dateCours;

    public Timestamp getDateCours() {
        return dateCours;
    }

    public void setDateCours(Timestamp dateCours) {
        this.dateCours = dateCours;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @jakarta.persistence.Column(name = "idCours")
    private int idCours;

    public int getIdCours() {
        return idCours;
    }

    public void setIdCours(int idCours) {
        this.idCours = idCours;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @jakarta.persistence.Column(name = "emailEnseignant")
    private String emailEnseignant;

    public String getEmailEnseignant() {
        return emailEnseignant;
    }

    public void setEmailEnseignant(String emailEnseignant) {
        this.emailEnseignant = emailEnseignant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coursdateprof that = (Coursdateprof) o;

        if (idCours != that.idCours) return false;
        if (!Objects.equals(dateCours, that.dateCours)) return false;
        return Objects.equals(emailEnseignant, that.emailEnseignant);
    }

    @Override
    public int hashCode() {
        int result = dateCours != null ? dateCours.hashCode() : 0;
        result = 31 * result + idCours;
        result = 31 * result + (emailEnseignant != null ? emailEnseignant.hashCode() : 0);
        return result;
    }
}
