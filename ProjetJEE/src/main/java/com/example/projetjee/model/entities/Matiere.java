package com.example.projetjee.model.entities;

import jakarta.persistence.*;

@Entity
public class Matiere {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idMatiere")
    private int idMatiere;
    @Basic
    @Column(name = "nomMatiere")
    private String nomMatiere;

    public int getIdMatiere() {
        return idMatiere;
    }

    public void setIdMatiere(int idMatiere) {
        this.idMatiere = idMatiere;
    }

    public String getNomMatiere() {
        return nomMatiere;
    }

    public void setNomMatiere(String nomMatiere) {
        this.nomMatiere = nomMatiere;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Matiere matiere = (Matiere) o;

        if (idMatiere != matiere.idMatiere) return false;
        if (nomMatiere != null ? !nomMatiere.equals(matiere.nomMatiere) : matiere.nomMatiere != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idMatiere;
        result = 31 * result + (nomMatiere != null ? nomMatiere.hashCode() : 0);
        return result;
    }
}
