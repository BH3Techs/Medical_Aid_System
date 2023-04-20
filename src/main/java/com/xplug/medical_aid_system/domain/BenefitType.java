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
 * A BenefitType.
 */
@Entity
@Table(name = "benefit_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BenefitType implements Serializable {

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
    @Column(name = "active", nullable = false)
    private Boolean active;

    @OneToMany(mappedBy = "benefitType")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tariffs", "benefitType" }, allowSetters = true)
    private Set<Benefit> benefits = new HashSet<>();

    @OneToMany(mappedBy = "benefitType")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "benefitLimits", "plans", "benefitType" }, allowSetters = true)
    private Set<PlanBenefit> planBenefits = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BenefitType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public BenefitType name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public BenefitType description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return this.active;
    }

    public BenefitType active(Boolean active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<Benefit> getBenefits() {
        return this.benefits;
    }

    public void setBenefits(Set<Benefit> benefits) {
        if (this.benefits != null) {
            this.benefits.forEach(i -> i.setBenefitType(null));
        }
        if (benefits != null) {
            benefits.forEach(i -> i.setBenefitType(this));
        }
        this.benefits = benefits;
    }

    public BenefitType benefits(Set<Benefit> benefits) {
        this.setBenefits(benefits);
        return this;
    }

    public BenefitType addBenefit(Benefit benefit) {
        this.benefits.add(benefit);
        benefit.setBenefitType(this);
        return this;
    }

    public BenefitType removeBenefit(Benefit benefit) {
        this.benefits.remove(benefit);
        benefit.setBenefitType(null);
        return this;
    }

    public Set<PlanBenefit> getPlanBenefits() {
        return this.planBenefits;
    }

    public void setPlanBenefits(Set<PlanBenefit> planBenefits) {
        if (this.planBenefits != null) {
            this.planBenefits.forEach(i -> i.setBenefitType(null));
        }
        if (planBenefits != null) {
            planBenefits.forEach(i -> i.setBenefitType(this));
        }
        this.planBenefits = planBenefits;
    }

    public BenefitType planBenefits(Set<PlanBenefit> planBenefits) {
        this.setPlanBenefits(planBenefits);
        return this;
    }

    public BenefitType addPlanBenefit(PlanBenefit planBenefit) {
        this.planBenefits.add(planBenefit);
        planBenefit.setBenefitType(this);
        return this;
    }

    public BenefitType removePlanBenefit(PlanBenefit planBenefit) {
        this.planBenefits.remove(planBenefit);
        planBenefit.setBenefitType(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BenefitType)) {
            return false;
        }
        return id != null && id.equals(((BenefitType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BenefitType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", active='" + getActive() + "'" +
            "}";
    }
}
