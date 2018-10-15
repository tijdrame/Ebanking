package com.novatech.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Beneficiaire.
 */
@Entity
@Table(name = "beneficiaire")
public class Beneficiaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "titulaire", nullable = false)
    private String titulaire;

    @NotNull
    @Column(name = "numero_compte", nullable = false)
    private String numeroCompte;

    @Column(name = "date_demande")
    private LocalDate dateDemande;

    @Column(name = "date_acceptation")
    private LocalDate dateAcceptation;

    @Column(name = "deleted")
    private Boolean deleted;

    @ManyToOne
    private Abonne abonne;

    @ManyToOne
    private Statut statut;

    @ManyToOne
    private BanquesPartenaires banquesPartenaires;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulaire() {
        return titulaire;
    }

    public Beneficiaire titulaire(String titulaire) {
        this.titulaire = titulaire;
        return this;
    }

    public void setTitulaire(String titulaire) {
        this.titulaire = titulaire;
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public Beneficiaire numeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
        return this;
    }

    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public LocalDate getDateDemande() {
        return dateDemande;
    }

    public Beneficiaire dateDemande(LocalDate dateDemande) {
        this.dateDemande = dateDemande;
        return this;
    }

    public void setDateDemande(LocalDate dateDemande) {
        this.dateDemande = dateDemande;
    }

    public LocalDate getDateAcceptation() {
        return dateAcceptation;
    }

    public Beneficiaire dateAcceptation(LocalDate dateAcceptation) {
        this.dateAcceptation = dateAcceptation;
        return this;
    }

    public void setDateAcceptation(LocalDate dateAcceptation) {
        this.dateAcceptation = dateAcceptation;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public Beneficiaire deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Abonne getAbonne() {
        return abonne;
    }

    public Beneficiaire abonne(Abonne abonne) {
        this.abonne = abonne;
        return this;
    }

    public void setAbonne(Abonne abonne) {
        this.abonne = abonne;
    }

    public Statut getStatut() {
        return statut;
    }

    public Beneficiaire statut(Statut statut) {
        this.statut = statut;
        return this;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public BanquesPartenaires getBanquesPartenaires() {
        return banquesPartenaires;
    }

    public Beneficiaire banquesPartenaires(BanquesPartenaires banquesPartenaires) {
        this.banquesPartenaires = banquesPartenaires;
        return this;
    }

    public void setBanquesPartenaires(BanquesPartenaires banquesPartenaires) {
        this.banquesPartenaires = banquesPartenaires;
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
        Beneficiaire beneficiaire = (Beneficiaire) o;
        if (beneficiaire.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), beneficiaire.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Beneficiaire{" +
            "id=" + getId() +
            ", titulaire='" + getTitulaire() + "'" +
            ", numeroCompte='" + getNumeroCompte() + "'" +
            ", dateDemande='" + getDateDemande() + "'" +
            ", dateAcceptation='" + getDateAcceptation() + "'" +
            ", deleted='" + isDeleted() + "'" +
            "}";
    }
}
