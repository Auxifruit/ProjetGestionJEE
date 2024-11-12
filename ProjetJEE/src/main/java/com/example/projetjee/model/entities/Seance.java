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
    @Column(name = "dateDebutSeance")
    private Timestamp dateDebutSeance;
    @Basic
    @Column(name = "dateFinSeance")
    private Timestamp dateFinSeance;
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

    public Timestamp getDateDebutSeance() {
        return dateDebutSeance;
    }

    public void setDateDebutSeance(Timestamp dateDebutSeance) {
        this.dateDebutSeance = dateDebutSeance;
    }

    public Timestamp getDateFinSeance() {
        return dateFinSeance;
    }

    public void setDateFinSeance(Timestamp dateFinSeance) {
        this.dateFinSeance = dateFinSeance;
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
        if (dateDebutSeance != null ? !dateDebutSeance.equals(seance.dateDebutSeance) : seance.dateDebutSeance != null)
            return false;
        if (dateFinSeance != null ? !dateFinSeance.equals(seance.dateFinSeance) : seance.dateFinSeance != null)
            return false;
        if (idCours != null ? !idCours.equals(seance.idCours) : seance.idCours != null) return false;
        if (idEnseignant != null ? !idEnseignant.equals(seance.idEnseignant) : seance.idEnseignant != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idSeance;
        result = 31 * result + (dateDebutSeance != null ? dateDebutSeance.hashCode() : 0);
        result = 31 * result + (dateFinSeance != null ? dateFinSeance.hashCode() : 0);
        result = 31 * result + (idCours != null ? idCours.hashCode() : 0);
        result = 31 * result + (idEnseignant != null ? idEnseignant.hashCode() : 0);
        return result;
    }
}
