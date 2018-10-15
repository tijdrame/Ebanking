package com.novatech.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Devise.
 */
@Entity
@Table(name = "devise")
public class Devise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @Column(name = "code")
    private String code;

    @Column(name = "achat_bb")
    private Double achatBb;

    @Column(name = "achat_tc")
    private Double achatTc;

    @Column(name = "achat_tr")
    private Double achatTr;

    @Column(name = "vente_bb")
    private Double venteBb;

    @Column(name = "vente_tc")
    private Double venteTc;

    @Column(name = "vente_tr")
    private Double venteTr;

    @Column(name = "date_maj")
    private LocalDate dateMaj;

    @Column(name = "deleted")
    private Boolean deleted;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public Devise libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getCode() {
        return code;
    }

    public Devise code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getAchatBb() {
        return achatBb;
    }

    public Devise achatBb(Double achatBb) {
        this.achatBb = achatBb;
        return this;
    }

    public void setAchatBb(Double achatBb) {
        this.achatBb = achatBb;
    }

    public Double getAchatTc() {
        return achatTc;
    }

    public Devise achatTc(Double achatTc) {
        this.achatTc = achatTc;
        return this;
    }

    public void setAchatTc(Double achatTc) {
        this.achatTc = achatTc;
    }

    public Double getAchatTr() {
        return achatTr;
    }

    public Devise achatTr(Double achatTr) {
        this.achatTr = achatTr;
        return this;
    }

    public void setAchatTr(Double achatTr) {
        this.achatTr = achatTr;
    }

    public Double getVenteBb() {
        return venteBb;
    }

    public Devise venteBb(Double venteBb) {
        this.venteBb = venteBb;
        return this;
    }

    public void setVenteBb(Double venteBb) {
        this.venteBb = venteBb;
    }

    public Double getVenteTc() {
        return venteTc;
    }

    public Devise venteTc(Double venteTc) {
        this.venteTc = venteTc;
        return this;
    }

    public void setVenteTc(Double venteTc) {
        this.venteTc = venteTc;
    }

    public Double getVenteTr() {
        return venteTr;
    }

    public Devise venteTr(Double venteTr) {
        this.venteTr = venteTr;
        return this;
    }

    public void setVenteTr(Double venteTr) {
        this.venteTr = venteTr;
    }

    public LocalDate getDateMaj() {
        return dateMaj;
    }

    public Devise dateMaj(LocalDate dateMaj) {
        this.dateMaj = dateMaj;
        return this;
    }

    public void setDateMaj(LocalDate dateMaj) {
        this.dateMaj = dateMaj;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public Devise deleted(Boolean deleted) {
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
        Devise devise = (Devise) o;
        if (devise.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), devise.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Devise{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", code='" + getCode() + "'" +
            ", achatBb=" + getAchatBb() +
            ", achatTc=" + getAchatTc() +
            ", achatTr=" + getAchatTr() +
            ", venteBb=" + getVenteBb() +
            ", venteTc=" + getVenteTc() +
            ", venteTr=" + getVenteTr() +
            ", dateMaj='" + getDateMaj() + "'" +
            ", deleted='" + isDeleted() + "'" +
            "}";
    }
}
