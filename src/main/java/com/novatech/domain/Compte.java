package com.novatech.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Compte.
 */
@Entity
@Table(name = "compte")
public class Compte implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "numero", nullable = false)
    private String numero;

    @NotNull
    @Column(name = "numero_complet", nullable = false)
    private String numeroComplet;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @Column(name = "solde")
    private Double solde;

    @Column(name = "deleted")
    private Boolean deleted;

    @NotNull
    @Column(name = "sens", nullable = false)
    private String sens;

    @ManyToOne
    private Abonne abonne;

    @ManyToOne
    private Devise devise;

    @ManyToOne
    private TypeCompte typeCompte;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public Compte numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNumeroComplet() {
        return numeroComplet;
    }

    public Compte numeroComplet(String numeroComplet) {
        this.numeroComplet = numeroComplet;
        return this;
    }

    public void setNumeroComplet(String numeroComplet) {
        this.numeroComplet = numeroComplet;
    }

    public String getLibelle() {
        return libelle;
    }

    public Compte libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Double getSolde() {
        return solde;
    }

    public Compte solde(Double solde) {
        this.solde = solde;
        return this;
    }

    public void setSolde(Double solde) {
        this.solde = solde;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public Compte deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getSens() {
        return sens;
    }

    public Compte sens(String sens) {
        this.sens = sens;
        return this;
    }

    public void setSens(String sens) {
        this.sens = sens;
    }

    public Abonne getAbonne() {
        return abonne;
    }

    public Compte abonne(Abonne abonne) {
        this.abonne = abonne;
        return this;
    }

    public void setAbonne(Abonne abonne) {
        this.abonne = abonne;
    }

    public Devise getDevise() {
        return devise;
    }

    public Compte devise(Devise devise) {
        this.devise = devise;
        return this;
    }

    public void setDevise(Devise devise) {
        this.devise = devise;
    }

    public TypeCompte getTypeCompte() {
        return typeCompte;
    }

    public Compte typeCompte(TypeCompte typeCompte) {
        this.typeCompte = typeCompte;
        return this;
    }

    public void setTypeCompte(TypeCompte typeCompte) {
        this.typeCompte = typeCompte;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Compte compte = (Compte) o;
        if (compte.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), compte.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Compte{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", numeroComplet='" + getNumeroComplet() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", solde=" + getSolde() +
            ", deleted='" + isDeleted() + "'" +
            ", sens='" + getSens() + "'" +
            "}";
    }
}
