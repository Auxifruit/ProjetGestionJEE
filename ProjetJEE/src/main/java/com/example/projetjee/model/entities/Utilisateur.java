package models;

import jakarta.persistence.*;

@Entity
public class Utilisateur {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idUtilisateur")
    private int idUtilisateur;
    @Basic
    @Column(name = "identifiantUtilisateur")
    private String identifiantUtilisateur;
    @Basic
    @Column(name = "motDePasseUtilisateur")
    private String motDePasseUtilisateur;
    @Basic
    @Column(name = "roleUtilisateur")
    private String roleUtilisateur;

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getIdentifiantUtilisateur() {
        return identifiantUtilisateur;
    }

    public void setIdentifiantUtilisateur(String identifiantUtilisateur) {
        this.identifiantUtilisateur = identifiantUtilisateur;
    }

    public String getMotDePasseUtilisateur() {
        return motDePasseUtilisateur;
    }

    public void setMotDePasseUtilisateur(String motDePasseUtilisateur) {
        this.motDePasseUtilisateur = motDePasseUtilisateur;
    }

    public String getRoleUtilisateur() {
        return roleUtilisateur;
    }

    public void setRoleUtilisateur(String roleUtilisateur) {
        this.roleUtilisateur = roleUtilisateur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Utilisateur that = (Utilisateur) o;

        if (idUtilisateur != that.idUtilisateur) return false;
        if (identifiantUtilisateur != null ? !identifiantUtilisateur.equals(that.identifiantUtilisateur) : that.identifiantUtilisateur != null)
            return false;
        if (motDePasseUtilisateur != null ? !motDePasseUtilisateur.equals(that.motDePasseUtilisateur) : that.motDePasseUtilisateur != null)
            return false;
        if (roleUtilisateur != null ? !roleUtilisateur.equals(that.roleUtilisateur) : that.roleUtilisateur != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idUtilisateur;
        result = 31 * result + (identifiantUtilisateur != null ? identifiantUtilisateur.hashCode() : 0);
        result = 31 * result + (motDePasseUtilisateur != null ? motDePasseUtilisateur.hashCode() : 0);
        result = 31 * result + (roleUtilisateur != null ? roleUtilisateur.hashCode() : 0);
        return result;
    }
}
