package com.novatech.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A NbreFeuillesChequier.
 */
@Entity
@Table(name = "nbre_feuilles_chequier")
public class NbreFeuillesChequier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nb_feuilles", nullable = false)
    private Integer nbFeuilles;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "deleted")
    private Boolean deleted;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNbFeuilles() {
        return nbFeuilles;
    }

    public NbreFeuillesChequier nbFeuilles(Integer nbFeuilles) {
        this.nbFeuilles = nbFeuilles;
        return this;
    }

    public void setNbFeuilles(Integer nbFeuilles) {
        this.nbFeuilles = nbFeuilles;
    }

    public String getLibelle() {
        return libelle;
    }

    public NbreFeuillesChequier libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public NbreFeuillesChequier deleted(Boolean deleted) {
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
        NbreFeuillesChequier nbreFeuillesChequier = (NbreFeuillesChequier) o;
        if (nbreFeuillesChequier.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), nbreFeuillesChequier.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NbreFeuillesChequier{" +
            "id=" + getId() +
            ", nbFeuilles=" + getNbFeuilles() +
            ", libelle='" + getLibelle() + "'" +
            ", deleted='" + isDeleted() + "'" +
            "}";
    }
}
