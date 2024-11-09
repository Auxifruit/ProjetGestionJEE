package com.example.projetjee.model.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Datecours {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "dateCours")
    private Timestamp dateCours;

    public Timestamp getDateCours() {
        return dateCours;
    }

    public void setDateCours(Timestamp dateCours) {
        this.dateCours = dateCours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Datecours datecours = (Datecours) o;

        return Objects.equals(dateCours, datecours.dateCours);
    }

    @Override
    public int hashCode() {
        return dateCours != null ? dateCours.hashCode() : 0;
    }
}
