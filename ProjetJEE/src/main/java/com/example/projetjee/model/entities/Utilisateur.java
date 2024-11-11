package com.example.projetjee.model.entities;

import jakarta.persistence.*;

@Entity
public class Utilisateur {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idUtilisateur")
    private int idUtilisateur;
    @Basic
    @Column(name = "motDePasseUtilisateur")
    private String motDePasseUtilisateur;
    @Basic
    @Column(name = "nomUtilisateur")
    private String nomUtilisateur;
    @Basic
    @Column(name = "prenomUtilisateur")
    private String prenomUtilisateur;
    @Basic
    @Column(name = "emailUtilisateur")
    private String emailUtilisateur;
    @Basic
    @Column(name = "dateDeNaissanceUtilisateur")
    private String dateDeNaissanceUtilisateur;
    @Basic
    @Column(name = "idRole")
    private Integer idRole;

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getMotDePasseUtilisateur() {
        return motDePasseUtilisateur;
    }

    public void setMotDePasseUtilisateur(String motDePasseUtilisateur) {
        this.motDePasseUtilisateur = motDePasseUtilisateur;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public String getPrenomUtilisateur() {
        return prenomUtilisateur;
    }

    public void setPrenomUtilisateur(String prenomUtilisateur) {
        this.prenomUtilisateur = prenomUtilisateur;
    }

    public String getEmailUtilisateur() {
        return emailUtilisateur;
    }

    public void setEmailUtilisateur(String emailUtilisateur) {
        this.emailUtilisateur = emailUtilisateur;
    }

    public String getDateDeNaissanceUtilisateur() {
        return dateDeNaissanceUtilisateur;
    }

    public void setDateDeNaissanceUtilisateur(String dateDeNaissanceUtilisateur) {
        this.dateDeNaissanceUtilisateur = dateDeNaissanceUtilisateur;
    }

    public Integer getIdRole() {
        return idRole;
    }

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Utilisateur that = (Utilisateur) o;

        if (idUtilisateur != that.idUtilisateur) return false;
        if (motDePasseUtilisateur != null ? !motDePasseUtilisateur.equals(that.motDePasseUtilisateur) : that.motDePasseUtilisateur != null)
            return false;
        if (nomUtilisateur != null ? !nomUtilisateur.equals(that.nomUtilisateur) : that.nomUtilisateur != null)
            return false;
        if (prenomUtilisateur != null ? !prenomUtilisateur.equals(that.prenomUtilisateur) : that.prenomUtilisateur != null)
            return false;
        if (emailUtilisateur != null ? !emailUtilisateur.equals(that.emailUtilisateur) : that.emailUtilisateur != null)
            return false;
        if (dateDeNaissanceUtilisateur != null ? !dateDeNaissanceUtilisateur.equals(that.dateDeNaissanceUtilisateur) : that.dateDeNaissanceUtilisateur != null)
            return false;
        if (idRole != null ? !idRole.equals(that.idRole) : that.idRole != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idUtilisateur;
        result = 31 * result + (motDePasseUtilisateur != null ? motDePasseUtilisateur.hashCode() : 0);
        result = 31 * result + (nomUtilisateur != null ? nomUtilisateur.hashCode() : 0);
        result = 31 * result + (prenomUtilisateur != null ? prenomUtilisateur.hashCode() : 0);
        result = 31 * result + (emailUtilisateur != null ? emailUtilisateur.hashCode() : 0);
        result = 31 * result + (dateDeNaissanceUtilisateur != null ? dateDeNaissanceUtilisateur.hashCode() : 0);
        result = 31 * result + (idRole != null ? idRole.hashCode() : 0);
        return result;
    }
}
