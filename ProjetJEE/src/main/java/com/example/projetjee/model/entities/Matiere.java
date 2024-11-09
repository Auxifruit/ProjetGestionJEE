package models;

import jakarta.persistence.*;

@Entity
public class Matiere {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idMatiere")
    private int idMatiere;
    @Basic
    @Column(name = "nomMatière")
    private String nomMatière;

    public int getIdMatiere() {
        return idMatiere;
    }

    public void setIdMatiere(int idMatiere) {
        this.idMatiere = idMatiere;
    }

    public String getNomMatière() {
        return nomMatière;
    }

    public void setNomMatière(String nomMatière) {
        this.nomMatière = nomMatière;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Matiere matiere = (Matiere) o;

        if (idMatiere != matiere.idMatiere) return false;
        if (nomMatière != null ? !nomMatière.equals(matiere.nomMatière) : matiere.nomMatière != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idMatiere;
        result = 31 * result + (nomMatière != null ? nomMatière.hashCode() : 0);
        return result;
    }
}
