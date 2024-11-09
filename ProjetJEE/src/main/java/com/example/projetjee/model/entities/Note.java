package com.example.projetjee.model.entities;

import jakarta.persistence.*;

import java.util.Objects;

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
        if (!Objects.equals(valeurNote, note.valeurNote)) return false;
        if (!Objects.equals(coefficientNote, note.coefficientNote))
            return false;
        if (!Objects.equals(emailEtudiant, note.emailEtudiant))
            return false;
        if (!Objects.equals(idMatiere, note.idMatiere)) return false;
        return Objects.equals(emailEnseignant, note.emailEnseignant);
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
