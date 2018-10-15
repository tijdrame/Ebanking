package com.novatech.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Abonne.
 */
@Entity
@Table(name = "abonne")
public class Abonne implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "date_deleted")
    private LocalDate dateDeleted;

    @Column(name = "date_updated")
    private LocalDate dateUpdated;

    @ManyToOne
    private Agence agence;

    @ManyToOne
    private User gestionnaire;

    @OneToOne
    @MapsId
    private User user;

    @ManyToOne
    private User userCreated;

    @ManyToOne
    private User userUpdated;

    @ManyToOne
    private User userDeleted;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTelephone() {
        return telephone;
    }

    public Abonne telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public Abonne deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Abonne dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateDeleted() {
        return dateDeleted;
    }

    public Abonne dateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
        return this;
    }

    public void setDateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public Abonne dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Agence getAgence() {
        return agence;
    }

    public Abonne agence(Agence agence) {
        this.agence = agence;
        return this;
    }

    public void setAgence(Agence agence) {
        this.agence = agence;
    }

    public User getGestionnaire() {
        return gestionnaire;
    }

    public Abonne gestionnaire(User user) {
        this.gestionnaire = user;
        return this;
    }

    public void setGestionnaire(User user) {
        this.gestionnaire = user;
    }

    public User getUser() {
        return user;
    }

    public Abonne user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUserCreated() {
        return userCreated;
    }

    public Abonne userCreated(User user) {
        this.userCreated = user;
        return this;
    }

    public void setUserCreated(User user) {
        this.userCreated = user;
    }

    public User getUserUpdated() {
        return userUpdated;
    }

    public Abonne userUpdated(User user) {
        this.userUpdated = user;
        return this;
    }

    public void setUserUpdated(User user) {
        this.userUpdated = user;
    }

    public User getUserDeleted() {
        return userDeleted;
    }

    public Abonne userDeleted(User user) {
        this.userDeleted = user;
        return this;
    }

    public void setUserDeleted(User user) {
        this.userDeleted = user;
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
        Abonne abonne = (Abonne) o;
        if (abonne.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), abonne.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Abonne{" +
            "id=" + getId() +
            ", telephone='" + getTelephone() + "'" +
            ", deleted='" + isDeleted() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateDeleted='" + getDateDeleted() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            "}";
    }
}
