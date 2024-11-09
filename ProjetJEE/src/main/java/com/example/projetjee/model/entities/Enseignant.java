package models;

import jakarta.persistence.*;

@Entity
public class Enseignant {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "emailEnseignant")
    private String emailEnseignant;
    @Basic
    @Column(name = "nomEnseignant")
    private String nomEnseignant;
    @Basic
    @Column(name = "prenomEnseignant")
    private String prenomEnseignant;
    @Basic
    @Column(name = "dateDENaissanceEnseignant")
    private String dateDeNaissanceEnseignant;
    @Basic
    @Column(name = "idUtilisateur")
    private Integer idUtilisateur;

    public String getEmailEnseignant() {
        return emailEnseignant;
    }

    public void setEmailEnseignant(String emailEnseignant) {
        this.emailEnseignant = emailEnseignant;
    }

    public String getNomEnseignant() {
        return nomEnseignant;
    }

    public void setNomEnseignant(String nomEnseignant) {
        this.nomEnseignant = nomEnseignant;
    }

    public String getPrenomEnseignant() {
        return prenomEnseignant;
    }

    public void setPrenomEnseignant(String prenomEnseignant) {
        this.prenomEnseignant = prenomEnseignant;
    }

    public String getDateDeNaissanceEnseignant() {
        return dateDeNaissanceEnseignant;
    }

    public void setDateDeNaissanceEnseignant(String dateDeNaissanceEnseignant) {
        this.dateDeNaissanceEnseignant = dateDeNaissanceEnseignant;
    }

    public Integer getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Integer idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Enseignant that = (Enseignant) o;

        if (emailEnseignant != null ? !emailEnseignant.equals(that.emailEnseignant) : that.emailEnseignant != null)
            return false;
        if (nomEnseignant != null ? !nomEnseignant.equals(that.nomEnseignant) : that.nomEnseignant != null)
            return false;
        if (prenomEnseignant != null ? !prenomEnseignant.equals(that.prenomEnseignant) : that.prenomEnseignant != null)
            return false;
        if (dateDeNaissanceEnseignant != null ? !dateDeNaissanceEnseignant.equals(that.dateDeNaissanceEnseignant) : that.dateDeNaissanceEnseignant != null)
            return false;
        if (idUtilisateur != null ? !idUtilisateur.equals(that.idUtilisateur) : that.idUtilisateur != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = emailEnseignant != null ? emailEnseignant.hashCode() : 0;
        result = 31 * result + (nomEnseignant != null ? nomEnseignant.hashCode() : 0);
        result = 31 * result + (prenomEnseignant != null ? prenomEnseignant.hashCode() : 0);
        result = 31 * result + (dateDeNaissanceEnseignant != null ? dateDeNaissanceEnseignant.hashCode() : 0);
        result = 31 * result + (idUtilisateur != null ? idUtilisateur.hashCode() : 0);
        return result;
    }
}
