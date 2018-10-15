package com.novatech.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Parametres.
 */
@Entity
@Table(name = "parametres")
public class Parametres implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "cle", nullable = false)
    private String cle;

    @NotNull
    @Column(name = "valeur", nullable = false)
    private String valeur;

    @Column(name = "deleted")
    private Boolean deleted;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCle() {
        return cle;
    }

    public Parametres cle(String cle) {
        this.cle = cle;
        return this;
    }

    public void setCle(String cle) {
        this.cle = cle;
    }

    public String getValeur() {
        return valeur;
    }

    public Parametres valeur(String valeur) {
        this.valeur = valeur;
        return this;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public Parametres deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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
        Parametres parametres = (Parametres) o;
        if (parametres.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), parametres.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Parametres{" +
            "id=" + getId() +
            ", cle='" + getCle() + "'" +
            ", valeur='" + getValeur() + "'" +
            ", deleted='" + isDeleted() + "'" +
            "}";
    }
}
