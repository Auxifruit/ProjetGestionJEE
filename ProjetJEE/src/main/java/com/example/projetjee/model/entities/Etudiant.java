package com.example.projetjee.model.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class    Etudiant {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "emailEtudiant")
    private String emailEtudiant;
    @Basic
    @Column(name = "nomEtudiant")
    private String nomEtudiant;
    @Basic
    @Column(name = "prénomEtudiant")
    private String prénomEtudiant;
    @Basic
    @Column(name = "dateDeNaissanceEtudiant")
    private String dateDeNaissanceEtudiant;
    @Basic
    @Column(name = "idUtilisateur")
    private Integer idUtilisateur;
    @Basic
    @Column(name = "idClasse")
    private Integer idClasse;

    public String getEmailEtudiant() {
        return emailEtudiant;
    }

    public void setEmailEtudiant(String emailEtudiant) {
        this.emailEtudiant = emailEtudiant;
    }

    public String getNomEtudiant() {
        return nomEtudiant;
    }

    public void setNomEtudiant(String nomEtudiant) {
        this.nomEtudiant = nomEtudiant;
    }

    public String getPrénomEtudiant() {
        return prénomEtudiant;
    }

    public void setPrénomEtudiant(String prénomEtudiant) {
        this.prénomEtudiant = prénomEtudiant;
    }

    public String getDateDeNaissanceEtudiant() {
        return dateDeNaissanceEtudiant;
    }

    public void setDateDeNaissanceEtudiant(String dateDeNaissanceEtudiant) {
        this.dateDeNaissanceEtudiant = dateDeNaissanceEtudiant;
    }

    public Integer getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Integer idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
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

        if (!Objects.equals(emailEtudiant, etudiant.emailEtudiant))
            return false;
        if (!Objects.equals(nomEtudiant, etudiant.nomEtudiant))
            return false;
        if (!Objects.equals(prénomEtudiant, etudiant.prénomEtudiant))
            return false;
        if (!Objects.equals(dateDeNaissanceEtudiant, etudiant.dateDeNaissanceEtudiant))
            return false;
        if (!Objects.equals(idUtilisateur, etudiant.idUtilisateur))
            return false;
        return Objects.equals(idClasse, etudiant.idClasse);
    }

    @Override
    public int hashCode() {
        int result = emailEtudiant != null ? emailEtudiant.hashCode() : 0;
        result = 31 * result + (nomEtudiant != null ? nomEtudiant.hashCode() : 0);
        result = 31 * result + (prénomEtudiant != null ? prénomEtudiant.hashCode() : 0);
        result = 31 * result + (dateDeNaissanceEtudiant != null ? dateDeNaissanceEtudiant.hashCode() : 0);
        result = 31 * result + (idUtilisateur != null ? idUtilisateur.hashCode() : 0);
        result = 31 * result + (idClasse != null ? idClasse.hashCode() : 0);
        return result;
    }
}
