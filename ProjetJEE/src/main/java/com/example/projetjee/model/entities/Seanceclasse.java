package com.example.projetjee.model.entities;

import jakarta.persistence.*;

@Entity
public class Seanceclasse {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idSeanceClasse")
    private int idSeanceClasse;
    @Basic
    @Column(name = "idSeance")
    private Integer idSeance;
    @Basic
    @Column(name = "idClasse")
    private Integer idClasse;

    public int getIdSeanceClasse() {
        return idSeanceClasse;
    }

    public void setIdSeanceClasse(int idSeanceClasse) {
        this.idSeanceClasse = idSeanceClasse;
    }

    public Integer getIdSeance() {
        return idSeance;
    }

    public void setIdSeance(Integer idSeance) {
        this.idSeance = idSeance;
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

        Seanceclasse that = (Seanceclasse) o;

        if (idSeanceClasse != that.idSeanceClasse) return false;
        if (idSeance != null ? !idSeance.equals(that.idSeance) : that.idSeance != null) return false;
        if (idClasse != null ? !idClasse.equals(that.idClasse) : that.idClasse != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idSeanceClasse;
        result = 31 * result + (idSeance != null ? idSeance.hashCode() : 0);
        result = 31 * result + (idClasse != null ? idClasse.hashCode() : 0);
        return result;
    }
}
