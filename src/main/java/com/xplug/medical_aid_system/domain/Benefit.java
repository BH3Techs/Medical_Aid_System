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
 * A Benefit.
 */
@Entity
@Table(name = "benefit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Benefit implements Serializable {

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
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "benefit_code", nullable = false, unique = true)
    private String benefitCode;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @OneToMany(mappedBy = "benefit")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "currency", "benefit" }, allowSetters = true)
    private Set<Tariff> tariffs = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "benefits", "planBenefits" }, allowSetters = true)
    private BenefitType benefitType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Benefit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Benefit name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Benefit description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBenefitCode() {
        return this.benefitCode;
    }

    public Benefit benefitCode(String benefitCode) {
        this.setBenefitCode(benefitCode);
        return this;
    }

    public void setBenefitCode(String benefitCode) {
        this.benefitCode = benefitCode;
    }

    public Boolean getActive() {
        return this.active;
    }

    public Benefit active(Boolean active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<Tariff> getTariffs() {
        return this.tariffs;
    }

    public void setTariffs(Set<Tariff> tariffs) {
        if (this.tariffs != null) {
            this.tariffs.forEach(i -> i.setBenefit(null));
        }
        if (tariffs != null) {
            tariffs.forEach(i -> i.setBenefit(this));
        }
        this.tariffs = tariffs;
    }

    public Benefit tariffs(Set<Tariff> tariffs) {
        this.setTariffs(tariffs);
        return this;
    }

    public Benefit addTariff(Tariff tariff) {
        this.tariffs.add(tariff);
        tariff.setBenefit(this);
        return this;
    }

    public Benefit removeTariff(Tariff tariff) {
        this.tariffs.remove(tariff);
        tariff.setBenefit(null);
        return this;
    }

    public BenefitType getBenefitType() {
        return this.benefitType;
    }

    public void setBenefitType(BenefitType benefitType) {
        this.benefitType = benefitType;
    }

    public Benefit benefitType(BenefitType benefitType) {
        this.setBenefitType(benefitType);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Benefit)) {
            return false;
        }
        return id != null && id.equals(((Benefit) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Benefit{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", benefitCode='" + getBenefitCode() + "'" +
            ", active='" + getActive() + "'" +
            "}";
    }
}
