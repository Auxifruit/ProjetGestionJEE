package com.example.projetjee.model.entities;

import jakarta.persistence.*;

@Entity
public class Enseignant {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idEnseignant")
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

        Enseignant that = (Enseignant) o;

        if (idEnseignant != that.idEnseignant) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return idEnseignant;
    }
}
