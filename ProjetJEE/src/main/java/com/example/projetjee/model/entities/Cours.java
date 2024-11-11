package com.example.projetjee.model.entities;

import jakarta.persistence.*;

@Entity
public class Cours {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idCours")
    private int idCours;
    @Basic
    @Column(name = "nomCours")
    private String nomCours;
    @Basic
    @Column(name = "idMatiere")
    private Integer idMatiere;

    public int getIdCours() {
        return idCours;
    }

    public void setIdCours(int idCours) {
        this.idCours = idCours;
    }

    public String getNomCours() {
        return nomCours;
    }

    public void setNomCours(String nomCours) {
        this.nomCours = nomCours;
    }

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
        if (nomCours != null ? !nomCours.equals(cours.nomCours) : cours.nomCours != null) return false;
        if (idMatiere != null ? !idMatiere.equals(cours.idMatiere) : cours.idMatiere != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCours;
        result = 31 * result + (nomCours != null ? nomCours.hashCode() : 0);
        result = 31 * result + (idMatiere != null ? idMatiere.hashCode() : 0);
        return result;
    }
}
