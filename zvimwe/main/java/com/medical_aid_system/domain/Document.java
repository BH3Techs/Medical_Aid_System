package com.medical_aid_system.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.medical_aid_system.domain.enumeration.OwnerType;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Document.
 */
@Entity
@Table(name = "document")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Document implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "format", nullable = false)
    private String format;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "owner_type")
    private OwnerType ownerType;

    @Column(name = "date_created")
    private Instant dateCreated;

    @NotNull
    @Column(name = "validity", nullable = false)
    private Boolean validity;

    @ManyToOne
    @JsonIgnoreProperties(value = { "documents" }, allowSetters = true)
    private DocumentType documentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Document id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFormat() {
        return this.format;
    }

    public Document format(String format) {
        this.setFormat(format);
        return this;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getName() {
        return this.name;
    }

    public Document name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OwnerType getOwnerType() {
        return this.ownerType;
    }

    public Document ownerType(OwnerType ownerType) {
        this.setOwnerType(ownerType);
        return this;
    }

    public void setOwnerType(OwnerType ownerType) {
        this.ownerType = ownerType;
    }

    public Instant getDateCreated() {
        return this.dateCreated;
    }

    public Document dateCreated(Instant dateCreated) {
        this.setDateCreated(dateCreated);
        return this;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Boolean getValidity() {
        return this.validity;
    }

    public Document validity(Boolean validity) {
        this.setValidity(validity);
        return this;
    }

    public void setValidity(Boolean validity) {
        this.validity = validity;
    }

    public DocumentType getDocumentType() {
        return this.documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public Document documentType(DocumentType documentType) {
        this.setDocumentType(documentType);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Document)) {
            return false;
        }
        return id != null && id.equals(((Document) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Document{" +
            "id=" + getId() +
            ", format='" + getFormat() + "'" +
            ", name='" + getName() + "'" +
            ", ownerType='" + getOwnerType() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", validity='" + getValidity() + "'" +
            "}";
    }
}
