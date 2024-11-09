package com.example.projetjee.model.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Classe {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @jakarta.persistence.Column(name = "idClasse")
    private int idClasse;

    public int getIdClasse() {
        return idClasse;
    }

    public void setIdClasse(int idClasse) {
        this.idClasse = idClasse;
    }

    @Basic
    @Column(name = "nomClasse")
    private String nomClasse;

    public String getNomClasse() {
        return nomClasse;
    }

    public void setNomClasse(String nomClasse) {
        this.nomClasse = nomClasse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Classe classe = (Classe) o;

        if (idClasse != classe.idClasse) return false;
        return Objects.equals(nomClasse, classe.nomClasse);
    }

    @Override
    public int hashCode() {
        int result = idClasse;
        result = 31 * result + (nomClasse != null ? nomClasse.hashCode() : 0);
        return result;
    }
}
