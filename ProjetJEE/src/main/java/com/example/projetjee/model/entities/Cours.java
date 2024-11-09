package com.example.projetjee.model.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Cours {
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

    @Basic
    @Column(name = "nomCours")
    private String nomCours;

    public String getNomCours() {
        return nomCours;
    }

    public void setNomCours(String nomCours) {
        this.nomCours = nomCours;
    }

    @Basic
    @Column(name = "idMatiere")
    private Integer idMatiere;

    public Integer getIdMatiere() {
        return idMatiere;
    }

    public void setIdMatiere(Integer idMatiere) {
        this.idMatiere = idMatiere;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cours cours = (Cours) o;

        if (idCours != cours.idCours) return false;
        if (!Objects.equals(nomCours, cours.nomCours)) return false;
        return Objects.equals(idMatiere, cours.idMatiere);
    }

    @Override
    public int hashCode() {
        int result = idCours;
        result = 31 * result + (nomCours != null ? nomCours.hashCode() : 0);
        result = 31 * result + (idMatiere != null ? idMatiere.hashCode() : 0);
        return result;
    }
}
