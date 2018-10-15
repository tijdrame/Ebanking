package com.novatech.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Operation.
 */
@Entity
@Table(name = "operation")
public class Operation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date_operation")
    private LocalDate dateOperation;

    @Column(name = "date_acceptation")
    private LocalDate dateAcceptation;

    @Column(name = "date_execution")
    private LocalDate dateExecution;

    @NotNull
    @Column(name = "motif", nullable = false)
    private String motif;

    @NotNull
    @Column(name = "numero_transaction", nullable = false)
    private String numeroTransaction;

    @Column(name = "deleted")
    private Boolean deleted;

    @ManyToOne
    private OperationType operationType;

    @ManyToOne
    private PriseEnCharge priseEnCharge;

    @ManyToOne
    private Compte compte;

    @OneToMany(mappedBy = "operation")
    @JsonIgnore
    private Set<OperationsVirement> operationsVirements = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateOperation() {
        return dateOperation;
    }

    public Operation dateOperation(LocalDate dateOperation) {
        this.dateOperation = dateOperation;
        return this;
    }

    public void setDateOperation(LocalDate dateOperation) {
        this.dateOperation = dateOperation;
    }

    public LocalDate getDateAcceptation() {
        return dateAcceptation;
    }

    public Operation dateAcceptation(LocalDate dateAcceptation) {
        this.dateAcceptation = dateAcceptation;
        return this;
    }

    public void setDateAcceptation(LocalDate dateAcceptation) {
        this.dateAcceptation = dateAcceptation;
    }

    public LocalDate getDateExecution() {
        return dateExecution;
    }

    public Operation dateExecution(LocalDate dateExecution) {
        this.dateExecution = dateExecution;
        return this;
    }

    public void setDateExecution(LocalDate dateExecution) {
        this.dateExecution = dateExecution;
    }

    public String getMotif() {
        return motif;
    }

    public Operation motif(String motif) {
        this.motif = motif;
        return this;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getNumeroTransaction() {
        return numeroTransaction;
    }

    public Operation numeroTransaction(String numeroTransaction) {
        this.numeroTransaction = numeroTransaction;
        return this;
    }

    public void setNumeroTransaction(String numeroTransaction) {
        this.numeroTransaction = numeroTransaction;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public Operation deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public Operation operationType(OperationType operationType) {
        this.operationType = operationType;
        return this;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public PriseEnCharge getPriseEnCharge() {
        return priseEnCharge;
    }

    public Operation priseEnCharge(PriseEnCharge priseEnCharge) {
        this.priseEnCharge = priseEnCharge;
        return this;
    }

    public void setPriseEnCharge(PriseEnCharge priseEnCharge) {
        this.priseEnCharge = priseEnCharge;
    }

    public Compte getCompte() {
        return compte;
    }

    public Operation compte(Compte compte) {
        this.compte = compte;
        return this;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public Set<OperationsVirement> getOperationsVirements() {
        return operationsVirements;
    }

    public Operation operationsVirements(Set<OperationsVirement> operationsVirements) {
        this.operationsVirements = operationsVirements;
        return this;
    }

    public Operation addOperationsVirement(OperationsVirement operationsVirement) {
        this.operationsVirements.add(operationsVirement);
        operationsVirement.setOperation(this);
        return this;
    }

    public Operation removeOperationsVirement(OperationsVirement operationsVirement) {
        this.operationsVirements.remove(operationsVirement);
        operationsVirement.setOperation(null);
        return this;
    }

    public void setOperationsVirements(Set<OperationsVirement> operationsVirements) {
        this.operationsVirements = operationsVirements;
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
        Operation operation = (Operation) o;
        if (operation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), operation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Operation{" +
            "id=" + getId() +
            ", dateOperation='" + getDateOperation() + "'" +
            ", dateAcceptation='" + getDateAcceptation() + "'" +
            ", dateExecution='" + getDateExecution() + "'" +
            ", motif='" + getMotif() + "'" +
            ", numeroTransaction='" + getNumeroTransaction() + "'" +
            ", deleted='" + isDeleted() + "'" +
            "}";
    }
}
