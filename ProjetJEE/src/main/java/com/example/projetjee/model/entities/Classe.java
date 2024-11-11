package com.example.projetjee.model.entities;

import jakarta.persistence.*;

@Entity
public class Classe {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idClasse")
    private int idClasse;
    @Basic
    @Column(name = "nomClasse")
    private String nomClasse;

    public int getIdClasse() {
        return idClasse;
    }

    public void setIdClasse(int idClasse) {
        this.idClasse = idClasse;
    }

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
        if (nomClasse != null ? !nomClasse.equals(classe.nomClasse) : classe.nomClasse != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idClasse;
        result = 31 * result + (nomClasse != null ? nomClasse.hashCode() : 0);
        return result;
    }
}
