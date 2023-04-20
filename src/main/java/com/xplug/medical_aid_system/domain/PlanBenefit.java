package com.xplug.medical_aid_system.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xplug.medical_aid_system.domain.enumeration.PeriodUnit;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PlanBenefit.
 */
@Entity
@Table(name = "plan_benefit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlanBenefit implements Serializable {

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
    @Enumerated(EnumType.STRING)
    @Column(name = "waiting_period_unit", nullable = false)
    private PeriodUnit waitingPeriodUnit;

    @NotNull
    @Column(name = "waiting_period_value", nullable = false)
    private Integer waitingPeriodValue;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @OneToMany(mappedBy = "planBenefit")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "benefitLimitType", "planBenefit" }, allowSetters = true)
    private Set<BenefitLimit> benefitLimits = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "currencies", "planBenefits", "planBillingCycles", "policies", "planCategory" }, allowSetters = true)
    private Plans plans;

    @ManyToOne
    @JsonIgnoreProperties(value = { "benefits", "planBenefits" }, allowSetters = true)
    private BenefitType benefitType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PlanBenefit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public PlanBenefit name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PeriodUnit getWaitingPeriodUnit() {
        return this.waitingPeriodUnit;
    }

    public PlanBenefit waitingPeriodUnit(PeriodUnit waitingPeriodUnit) {
        this.setWaitingPeriodUnit(waitingPeriodUnit);
        return this;
    }

    public void setWaitingPeriodUnit(PeriodUnit waitingPeriodUnit) {
        this.waitingPeriodUnit = waitingPeriodUnit;
    }

    public Integer getWaitingPeriodValue() {
        return this.waitingPeriodValue;
    }

    public PlanBenefit waitingPeriodValue(Integer waitingPeriodValue) {
        this.setWaitingPeriodValue(waitingPeriodValue);
        return this;
    }

    public void setWaitingPeriodValue(Integer waitingPeriodValue) {
        this.waitingPeriodValue = waitingPeriodValue;
    }

    public String getDescription() {
        return this.description;
    }

    public PlanBenefit description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return this.active;
    }

    public PlanBenefit active(Boolean active) {
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
            this.benefitLimits.forEach(i -> i.setPlanBenefit(null));
        }
        if (benefitLimits != null) {
            benefitLimits.forEach(i -> i.setPlanBenefit(this));
        }
        this.benefitLimits = benefitLimits;
    }

    public PlanBenefit benefitLimits(Set<BenefitLimit> benefitLimits) {
        this.setBenefitLimits(benefitLimits);
        return this;
    }

    public PlanBenefit addBenefitLimit(BenefitLimit benefitLimit) {
        this.benefitLimits.add(benefitLimit);
        benefitLimit.setPlanBenefit(this);
        return this;
    }

    public PlanBenefit removeBenefitLimit(BenefitLimit benefitLimit) {
        this.benefitLimits.remove(benefitLimit);
        benefitLimit.setPlanBenefit(null);
        return this;
    }

    public Plans getPlans() {
        return this.plans;
    }

    public void setPlans(Plans plans) {
        this.plans = plans;
    }

    public PlanBenefit plans(Plans plans) {
        this.setPlans(plans);
        return this;
    }

    public BenefitType getBenefitType() {
        return this.benefitType;
    }

    public void setBenefitType(BenefitType benefitType) {
        this.benefitType = benefitType;
    }

    public PlanBenefit benefitType(BenefitType benefitType) {
        this.setBenefitType(benefitType);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlanBenefit)) {
            return false;
        }
        return id != null && id.equals(((PlanBenefit) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlanBenefit{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", waitingPeriodUnit='" + getWaitingPeriodUnit() + "'" +
            ", waitingPeriodValue=" + getWaitingPeriodValue() +
            ", description='" + getDescription() + "'" +
            ", active='" + getActive() + "'" +
            "}";
    }
}
