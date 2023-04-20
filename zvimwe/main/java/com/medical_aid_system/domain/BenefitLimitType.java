package com.medical_aid_system.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BenefitLimitType.
 */
@Entity
@Table(name = "benefit_limit_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BenefitLimitType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @OneToMany(mappedBy = "benefitLimitType")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "benefitLimitType", "planBenefit" }, allowSetters = true)
    private Set<BenefitLimit> benefitLimits = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BenefitLimitType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public BenefitLimitType name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return this.active;
    }

    public BenefitLimitType active(Boolean active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<BenefitLimit> getBenefitLimits() {
        return this.benefitLimits;
    }

    public void setBenefitLimits(Set<BenefitLimit> benefitLimits) {
        if (this.benefitLimits != null) {
            this.benefitLimits.forEach(i -> i.setBenefitLimitType(null));
        }
        if (benefitLimits != null) {
            benefitLimits.forEach(i -> i.setBenefitLimitType(this));
        }
        this.benefitLimits = benefitLimits;
    }

    public BenefitLimitType benefitLimits(Set<BenefitLimit> benefitLimits) {
        this.setBenefitLimits(benefitLimits);
        return this;
    }

    public BenefitLimitType addBenefitLimit(BenefitLimit benefitLimit) {
        this.benefitLimits.add(benefitLimit);
        benefitLimit.setBenefitLimitType(this);
        return this;
    }

    public BenefitLimitType removeBenefitLimit(BenefitLimit benefitLimit) {
        this.benefitLimits.remove(benefitLimit);
        benefitLimit.setBenefitLimitType(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BenefitLimitType)) {
            return false;
        }
        return id != null && id.equals(((BenefitLimitType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BenefitLimitType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", active='" + getActive() + "'" +
            "}";
    }
}
