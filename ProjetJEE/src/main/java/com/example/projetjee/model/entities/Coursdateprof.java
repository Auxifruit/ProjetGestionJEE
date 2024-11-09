package com.example.projetjee.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.sql.Timestamp;

@Entity
@jakarta.persistence.IdClass(com.example.projetjee.model.entities.CoursdateprofPK.class)
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
    @jakarta.persistence.Column(name = "idEnseignant")
    private int idEnseignant;

    public int getIdEnseignant() {
        return idEnseignant;
    }

    public void setIdEnseignant(int idEnseignant) {
        this.idEnseignant = idEnseignant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coursdateprof that = (Coursdateprof) o;

        if (idCours != that.idCours) return false;
        if (idEnseignant != that.idEnseignant) return false;
        if (dateCours != null ? !dateCours.equals(that.dateCours) : that.dateCours != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dateCours != null ? dateCours.hashCode() : 0;
        result = 31 * result + idCours;
        result = 31 * result + idEnseignant;
        return result;
    }
}
