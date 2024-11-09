package models;

import jakarta.persistence.*;

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

        if (emailEtudiant != null ? !emailEtudiant.equals(etudiant.emailEtudiant) : etudiant.emailEtudiant != null)
            return false;
        if (nomEtudiant != null ? !nomEtudiant.equals(etudiant.nomEtudiant) : etudiant.nomEtudiant != null)
            return false;
        if (prénomEtudiant != null ? !prénomEtudiant.equals(etudiant.prénomEtudiant) : etudiant.prénomEtudiant != null)
            return false;
        if (dateDeNaissanceEtudiant != null ? !dateDeNaissanceEtudiant.equals(etudiant.dateDeNaissanceEtudiant) : etudiant.dateDeNaissanceEtudiant != null)
            return false;
        if (idUtilisateur != null ? !idUtilisateur.equals(etudiant.idUtilisateur) : etudiant.idUtilisateur != null)
            return false;
        if (idClasse != null ? !idClasse.equals(etudiant.idClasse) : etudiant.idClasse != null) return false;

        return true;
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
