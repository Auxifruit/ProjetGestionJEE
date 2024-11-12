package com.example.projetjee.model.entities;

import jakarta.persistence.*;

@Entity
public class Administrateur {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idAdministrateur")
    private int idAdministrateur;

    public int getIdAdministrateur() {
        return idAdministrateur;
    }

    public void setIdAdministrateur(int idAdministrateur) {
        this.idAdministrateur = idAdministrateur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Administrateur that = (Administrateur) o;

        if (idAdministrateur != that.idAdministrateur) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return idAdministrateur;
    }
}
