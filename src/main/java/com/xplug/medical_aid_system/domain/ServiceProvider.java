package com.xplug.medical_aid_system.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ServiceProvider.
 */
@Entity
@Table(name = "service_provider")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServiceProvider implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "a_hfoz_number")
    private String aHFOZNumber;

    @Column(name = "description")
    private Boolean description;

    @OneToOne
    @JoinColumn(unique = true)
    private ContactDetails contactDetails;

    @OneToMany(mappedBy = "serviceProvider")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tarrifClaims", "policy", "serviceProvider" }, allowSetters = true)
    private Set<Claim> claims = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ServiceProvider id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ServiceProvider name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getaHFOZNumber() {
        return this.aHFOZNumber;
    }

    public ServiceProvider aHFOZNumber(String aHFOZNumber) {
        this.setaHFOZNumber(aHFOZNumber);
        return this;
    }

    public void setaHFOZNumber(String aHFOZNumber) {
        this.aHFOZNumber = aHFOZNumber;
    }

    public Boolean getDescription() {
        return this.description;
    }

    public ServiceProvider description(Boolean description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(Boolean description) {
        this.description = description;
    }

    public ContactDetails getContactDetails() {
        return this.contactDetails;
    }

    public void setContactDetails(ContactDetails contactDetails) {
        this.contactDetails = contactDetails;
    }

    public ServiceProvider contactDetails(ContactDetails contactDetails) {
        this.setContactDetails(contactDetails);
        return this;
    }

    public Set<Claim> getClaims() {
        return this.claims;
    }

    public void setClaims(Set<Claim> claims) {
        if (this.claims != null) {
            this.claims.forEach(i -> i.setServiceProvider(null));
        }
        if (claims != null) {
            claims.forEach(i -> i.setServiceProvider(this));
        }
        this.claims = claims;
    }

    public ServiceProvider claims(Set<Claim> claims) {
        this.setClaims(claims);
        return this;
    }

    public ServiceProvider addClaim(Claim claim) {
        this.claims.add(claim);
        claim.setServiceProvider(this);
        return this;
    }

    public ServiceProvider removeClaim(Claim claim) {
        this.claims.remove(claim);
        claim.setServiceProvider(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceProvider)) {
            return false;
        }
        return id != null && id.equals(((ServiceProvider) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceProvider{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", aHFOZNumber='" + getaHFOZNumber() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
