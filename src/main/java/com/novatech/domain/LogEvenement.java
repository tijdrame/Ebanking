package com.novatech.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A LogEvenement.
 */
@Entity
@Table(name = "log_evenement")
public class LogEvenement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_objet")
    private Long codeObjet;

    @Column(name = "entity_name")
    private String entityName;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "adresse_mac")
    private String adresseMac;

    @Column(name = "adresse_ip")
    private String adresseIP;

    @Column(name = "date_created")
    private Instant dateCreated;

    @ManyToOne
    private User userCreated;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCodeObjet() {
        return codeObjet;
    }

    public LogEvenement codeObjet(Long codeObjet) {
        this.codeObjet = codeObjet;
        return this;
    }

    public void setCodeObjet(Long codeObjet) {
        this.codeObjet = codeObjet;
    }

    public String getEntityName() {
        return entityName;
    }

    public LogEvenement entityName(String entityName) {
        this.entityName = entityName;
        return this;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEventName() {
        return eventName;
    }

    public LogEvenement eventName(String eventName) {
        this.eventName = eventName;
        return this;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getAdresseMac() {
        return adresseMac;
    }

    public LogEvenement adresseMac(String adresseMac) {
        this.adresseMac = adresseMac;
        return this;
    }

    public void setAdresseMac(String adresseMac) {
        this.adresseMac = adresseMac;
    }

    public String getAdresseIP() {
        return adresseIP;
    }

    public LogEvenement adresseIP(String adresseIP) {
        this.adresseIP = adresseIP;
        return this;
    }

    public void setAdresseIP(String adresseIP) {
        this.adresseIP = adresseIP;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public LogEvenement dateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public User getUserCreated() {
        return userCreated;
    }

    public LogEvenement userCreated(User user) {
        this.userCreated = user;
        return this;
    }

    public void setUserCreated(User user) {
        this.userCreated = user;
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
        LogEvenement logEvenement = (LogEvenement) o;
        if (logEvenement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), logEvenement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LogEvenement{" +
            "id=" + getId() +
            ", codeObjet=" + getCodeObjet() +
            ", entityName='" + getEntityName() + "'" +
            ", eventName='" + getEventName() + "'" +
            ", adresseMac='" + getAdresseMac() + "'" +
            ", adresseIP='" + getAdresseIP() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            "}";
    }
}
