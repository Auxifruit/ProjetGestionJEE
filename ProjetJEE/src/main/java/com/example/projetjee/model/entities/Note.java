package com.example.projetjee.model.entities;

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
    @Column(name = "idEtudiant")
    private Integer idEtudiant;
    @Basic
    @Column(name = "idMatiere")
    private Integer idMatiere;
    @Basic
    @Column(name = "idEnseignant")
    private Integer idEnseignant;

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

    public Integer getIdEtudiant() {
        return idEtudiant;
    }

    public void setIdEtudiant(Integer idEtudiant) {
        this.idEtudiant = idEtudiant;
    }

    public Integer getIdMatiere() {
        return idMatiere;
    }

    public void setIdMatiere(Integer idMatiere) {
        this.idMatiere = idMatiere;
    }

    public Integer getIdEnseignant() {
        return idEnseignant;
    }

    public void setIdEnseignant(Integer idEnseignant) {
        this.idEnseignant = idEnseignant;
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
        if (idEtudiant != null ? !idEtudiant.equals(note.idEtudiant) : note.idEtudiant != null) return false;
        if (idMatiere != null ? !idMatiere.equals(note.idMatiere) : note.idMatiere != null) return false;
        if (idEnseignant != null ? !idEnseignant.equals(note.idEnseignant) : note.idEnseignant != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idNote;
        result = 31 * result + (valeurNote != null ? valeurNote.hashCode() : 0);
        result = 31 * result + (coefficientNote != null ? coefficientNote.hashCode() : 0);
        result = 31 * result + (idEtudiant != null ? idEtudiant.hashCode() : 0);
        result = 31 * result + (idMatiere != null ? idMatiere.hashCode() : 0);
        result = 31 * result + (idEnseignant != null ? idEnseignant.hashCode() : 0);
        return result;
    }
}
