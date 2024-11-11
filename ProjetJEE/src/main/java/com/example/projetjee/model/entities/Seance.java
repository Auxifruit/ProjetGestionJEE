package com.example.projetjee.model.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Seance {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idSeance")
    private int idSeance;
    @Basic
    @Column(name = "dateDébutCours")
    private Timestamp dateDébutCours;
    @Basic
    @Column(name = "dateFinCours")
    private Timestamp dateFinCours;
    @Basic
    @Column(name = "idCours")
    private Integer idCours;
    @Basic
    @Column(name = "idEnseignant")
    private Integer idEnseignant;

    public int getIdSeance() {
        return idSeance;
    }

    public void setIdSeance(int idSeance) {
        this.idSeance = idSeance;
    }

    public Timestamp getDateDébutCours() {
        return dateDébutCours;
    }

    public void setDateDébutCours(Timestamp dateDébutCours) {
        this.dateDébutCours = dateDébutCours;
    }

    public Timestamp getDateFinCours() {
        return dateFinCours;
    }

    public void setDateFinCours(Timestamp dateFinCours) {
        this.dateFinCours = dateFinCours;
    }

    public Integer getIdCours() {
        return idCours;
    }

    public void setIdCours(Integer idCours) {
        this.idCours = idCours;
    }

    public Integer getIdEnseignant() {
        return idEnseignant;
    }

    public void setIdEnseignant(Integer idEnseignant) {
        this.idEnseignant = idEnseignant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Seance seance = (Seance) o;

        if (idSeance != seance.idSeance) return false;
        if (dateDébutCours != null ? !dateDébutCours.equals(seance.dateDébutCours) : seance.dateDébutCours != null)
            return false;
        if (dateFinCours != null ? !dateFinCours.equals(seance.dateFinCours) : seance.dateFinCours != null)
            return false;
        if (idCours != null ? !idCours.equals(seance.idCours) : seance.idCours != null) return false;
        if (idEnseignant != null ? !idEnseignant.equals(seance.idEnseignant) : seance.idEnseignant != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idSeance;
        result = 31 * result + (dateDébutCours != null ? dateDébutCours.hashCode() : 0);
        result = 31 * result + (dateFinCours != null ? dateFinCours.hashCode() : 0);
        result = 31 * result + (idCours != null ? idCours.hashCode() : 0);
        result = 31 * result + (idEnseignant != null ? idEnseignant.hashCode() : 0);
        return result;
    }
}
