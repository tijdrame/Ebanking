package com.novatech.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A PaiementFacture.
 */
@Entity
@Table(name = "paiement_facture")
public class PaiementFacture implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "numero_facture", nullable = false)
    private String numeroFacture;

    @NotNull
    @Column(name = "montant", nullable = false)
    private Double montant;

    @NotNull
    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    @NotNull
    @Column(name = "date_fin", nullable = false)
    private LocalDate dateFin;

    @Column(name = "date_acceptation")
    private LocalDate dateAcceptation;

    @Column(name = "date_demande")
    private LocalDate dateDemande;

    @Column(name = "est_telecharge")
    private Boolean estTelecharge;

    @Column(name = "deleted")
    private Boolean deleted;

    @ManyToOne
    private Devise devise;

    @ManyToOne
    private Compte compte;

    @ManyToOne
    private Facturier facturier;

    @ManyToOne
    private Statut statut;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroFacture() {
        return numeroFacture;
    }

    public PaiementFacture numeroFacture(String numeroFacture) {
        this.numeroFacture = numeroFacture;
        return this;
    }

    public void setNumeroFacture(String numeroFacture) {
        this.numeroFacture = numeroFacture;
    }

    public Double getMontant() {
        return montant;
    }

    public PaiementFacture montant(Double montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public PaiementFacture dateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
        return this;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public PaiementFacture dateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
        return this;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public LocalDate getDateAcceptation() {
        return dateAcceptation;
    }

    public PaiementFacture dateAcceptation(LocalDate dateAcceptation) {
        this.dateAcceptation = dateAcceptation;
        return this;
    }

    public void setDateAcceptation(LocalDate dateAcceptation) {
        this.dateAcceptation = dateAcceptation;
    }

    public LocalDate getDateDemande() {
        return dateDemande;
    }

    public PaiementFacture dateDemande(LocalDate dateDemande) {
        this.dateDemande = dateDemande;
        return this;
    }

    public void setDateDemande(LocalDate dateDemande) {
        this.dateDemande = dateDemande;
    }

    public Boolean isEstTelecharge() {
        return estTelecharge;
    }

    public PaiementFacture estTelecharge(Boolean estTelecharge) {
        this.estTelecharge = estTelecharge;
        return this;
    }

    public void setEstTelecharge(Boolean estTelecharge) {
        this.estTelecharge = estTelecharge;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public PaiementFacture deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Devise getDevise() {
        return devise;
    }

    public PaiementFacture devise(Devise devise) {
        this.devise = devise;
        return this;
    }

    public void setDevise(Devise devise) {
        this.devise = devise;
    }

    public Compte getCompte() {
        return compte;
    }

    public PaiementFacture compte(Compte compte) {
        this.compte = compte;
        return this;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public Facturier getFacturier() {
        return facturier;
    }

    public PaiementFacture facturier(Facturier facturier) {
        this.facturier = facturier;
        return this;
    }

    public void setFacturier(Facturier facturier) {
        this.facturier = facturier;
    }

    public Statut getStatut() {
        return statut;
    }

    public PaiementFacture statut(Statut statut) {
        this.statut = statut;
        return this;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
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
        PaiementFacture paiementFacture = (PaiementFacture) o;
        if (paiementFacture.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paiementFacture.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PaiementFacture{" +
            "id=" + getId() +
            ", numeroFacture='" + getNumeroFacture() + "'" +
            ", montant=" + getMontant() +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", dateAcceptation='" + getDateAcceptation() + "'" +
            ", dateDemande='" + getDateDemande() + "'" +
            ", estTelecharge='" + isEstTelecharge() + "'" +
            ", deleted='" + isDeleted() + "'" +
            "}";
    }
}
