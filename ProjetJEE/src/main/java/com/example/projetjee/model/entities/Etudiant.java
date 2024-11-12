package com.example.projetjee.model.entities;

import jakarta.persistence.*;

@Entity
public class Etudiant {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idEtudiant")
    private int idEtudiant;
    @Basic
    @Column(name = "idClasse")
    private Integer idClasse;

    public int getIdEtudiant() {
        return idEtudiant;
    }

    public void setIdEtudiant(int idEtudiant) {
        this.idEtudiant = idEtudiant;
    }

    public Integer getIdClasse() {
        return idClasse;
    }

    public void setIdClasse(Integer idClasse) {
        this.idClasse = idClasse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Etudiant etudiant = (Etudiant) o;

        if (idEtudiant != etudiant.idEtudiant) return false;
        if (idClasse != null ? !idClasse.equals(etudiant.idClasse) : etudiant.idClasse != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idEtudiant;
        result = 31 * result + (idClasse != null ? idClasse.hashCode() : 0);
        return result;
    }
}
