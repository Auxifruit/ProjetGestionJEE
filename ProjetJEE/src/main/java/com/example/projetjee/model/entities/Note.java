package models;

import jakarta.persistence.*;

@Entity
public class Note {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idNote")
    private int idNote;
    @Basic
    @Column(name = "valeurNote")
    private Double valeurNote;
    @Basic
    @Column(name = "coefficientNote")
    private Integer coefficientNote;
    @Basic
    @Column(name = "emailEtudiant")
    private String emailEtudiant;
    @Basic
    @Column(name = "idMatiere")
    private Integer idMatiere;
    @Basic
    @Column(name = "emailEnseignant")
    private String emailEnseignant;

    public int getIdNote() {
        return idNote;
    }

    public void setIdNote(int idNote) {
        this.idNote = idNote;
    }

    public Double getValeurNote() {
        return valeurNote;
    }

    public void setValeurNote(Double valeurNote) {
        this.valeurNote = valeurNote;
    }

    public Integer getCoefficientNote() {
        return coefficientNote;
    }

    public void setCoefficientNote(Integer coefficientNote) {
        this.coefficientNote = coefficientNote;
    }

    public String getEmailEtudiant() {
        return emailEtudiant;
    }

    public void setEmailEtudiant(String emailEtudiant) {
        this.emailEtudiant = emailEtudiant;
    }

    public Integer getIdMatiere() {
        return idMatiere;
    }

    public void setIdMatiere(Integer idMatiere) {
        this.idMatiere = idMatiere;
    }

    public String getEmailEnseignant() {
        return emailEnseignant;
    }

    public void setEmailEnseignant(String emailEnseignant) {
        this.emailEnseignant = emailEnseignant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;

        if (idNote != note.idNote) return false;
        if (valeurNote != null ? !valeurNote.equals(note.valeurNote) : note.valeurNote != null) return false;
        if (coefficientNote != null ? !coefficientNote.equals(note.coefficientNote) : note.coefficientNote != null)
            return false;
        if (emailEtudiant != null ? !emailEtudiant.equals(note.emailEtudiant) : note.emailEtudiant != null)
            return false;
        if (idMatiere != null ? !idMatiere.equals(note.idMatiere) : note.idMatiere != null) return false;
        if (emailEnseignant != null ? !emailEnseignant.equals(note.emailEnseignant) : note.emailEnseignant != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idNote;
        result = 31 * result + (valeurNote != null ? valeurNote.hashCode() : 0);
        result = 31 * result + (coefficientNote != null ? coefficientNote.hashCode() : 0);
        result = 31 * result + (emailEtudiant != null ? emailEtudiant.hashCode() : 0);
        result = 31 * result + (idMatiere != null ? idMatiere.hashCode() : 0);
        result = 31 * result + (emailEnseignant != null ? emailEnseignant.hashCode() : 0);
        return result;
    }
}
