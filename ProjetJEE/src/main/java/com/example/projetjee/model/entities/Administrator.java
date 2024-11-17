package com.example.projetjee.model.entities;

import jakarta.persistence.*;

@Entity
public class Administrator {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "administratorId")
    private int administratorId;

    public int getAdministratorId() {
        return administratorId;
    }

    public void setAdministratorId(int administratorId) {
        this.administratorId = administratorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Administrator that = (Administrator) o;

        if (administratorId != that.administratorId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return administratorId;
    }
}
