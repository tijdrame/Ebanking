package com.novatech.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A OperationsVirement.
 */
@Entity
@Table(name = "operations_virement")
public class OperationsVirement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "montant", nullable = false)
    private Double montant;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "deleted")
    private Boolean deleted;

    @ManyToOne
    private Beneficiaire beneficiaire;

    @ManyToOne
    private Devise devise;

    @ManyToOne
    private Statut statut;

    @ManyToOne
    private Operation operation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMontant() {
        return montant;
    }

    public OperationsVirement montant(Double montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public String getCode() {
        return code;
    }

    public OperationsVirement code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public OperationsVirement deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Beneficiaire getBeneficiaire() {
        return beneficiaire;
    }

    public OperationsVirement beneficiaire(Beneficiaire beneficiaire) {
        this.beneficiaire = beneficiaire;
        return this;
    }

    public void setBeneficiaire(Beneficiaire beneficiaire) {
        this.beneficiaire = beneficiaire;
    }

    public Devise getDevise() {
        return devise;
    }

    public OperationsVirement devise(Devise devise) {
        this.devise = devise;
        return this;
    }

    public void setDevise(Devise devise) {
        this.devise = devise;
    }

    public Statut getStatut() {
        return statut;
    }

    public OperationsVirement statut(Statut statut) {
        this.statut = statut;
        return this;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public Operation getOperation() {
        return operation;
    }

    public OperationsVirement operation(Operation operation) {
        this.operation = operation;
        return this;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
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
        OperationsVirement operationsVirement = (OperationsVirement) o;
        if (operationsVirement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), operationsVirement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OperationsVirement{" +
            "id=" + getId() +
            ", montant=" + getMontant() +
            ", code='" + getCode() + "'" +
            ", deleted='" + isDeleted() + "'" +
            "}";
    }
}
