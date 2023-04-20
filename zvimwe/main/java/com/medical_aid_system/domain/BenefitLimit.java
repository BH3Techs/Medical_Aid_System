package com.medical_aid_system.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.medical_aid_system.domain.enumeration.PeriodUnit;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BenefitLimit.
 */
@Entity
@Table(name = "benefit_limit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BenefitLimit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "limit_value", nullable = false)
    private String limitValue;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "limit_period_unit", nullable = false)
    private PeriodUnit limitPeriodUnit;

    @NotNull
    @Column(name = "limit_period_value", nullable = false)
    private Integer limitPeriodValue;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne
    @JsonIgnoreProperties(value = { "benefitLimits" }, allowSetters = true)
    private BenefitLimitType benefitLimitType;

    @ManyToOne
    @JsonIgnoreProperties(value = { "benefitLimits", "plans", "benefitType" }, allowSetters = true)
    private PlanBenefit planBenefit;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BenefitLimit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLimitValue() {
        return this.limitValue;
    }

    public BenefitLimit limitValue(String limitValue) {
        this.setLimitValue(limitValue);
        return this;
    }

    public void setLimitValue(String limitValue) {
        this.limitValue = limitValue;
    }

    public PeriodUnit getLimitPeriodUnit() {
        return this.limitPeriodUnit;
    }

    public BenefitLimit limitPeriodUnit(PeriodUnit limitPeriodUnit) {
        this.setLimitPeriodUnit(limitPeriodUnit);
        return this;
    }

    public void setLimitPeriodUnit(PeriodUnit limitPeriodUnit) {
        this.limitPeriodUnit = limitPeriodUnit;
    }

    public Integer getLimitPeriodValue() {
        return this.limitPeriodValue;
    }

    public BenefitLimit limitPeriodValue(Integer limitPeriodValue) {
        this.setLimitPeriodValue(limitPeriodValue);
        return this;
    }

    public void setLimitPeriodValue(Integer limitPeriodValue) {
        this.limitPeriodValue = limitPeriodValue;
    }

    public Boolean getActive() {
        return this.active;
    }

    public BenefitLimit active(Boolean active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public BenefitLimitType getBenefitLimitType() {
        return this.benefitLimitType;
    }

    public void setBenefitLimitType(BenefitLimitType benefitLimitType) {
        this.benefitLimitType = benefitLimitType;
    }

    public BenefitLimit benefitLimitType(BenefitLimitType benefitLimitType) {
        this.setBenefitLimitType(benefitLimitType);
        return this;
    }

    public PlanBenefit getPlanBenefit() {
        return this.planBenefit;
    }

    public void setPlanBenefit(PlanBenefit planBenefit) {
        this.planBenefit = planBenefit;
    }

    public BenefitLimit planBenefit(PlanBenefit planBenefit) {
        this.setPlanBenefit(planBenefit);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BenefitLimit)) {
            return false;
        }
        return id != null && id.equals(((BenefitLimit) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BenefitLimit{" +
            "id=" + getId() +
            ", limitValue='" + getLimitValue() + "'" +
            ", limitPeriodUnit='" + getLimitPeriodUnit() + "'" +
            ", limitPeriodValue=" + getLimitPeriodValue() +
            ", active='" + getActive() + "'" +
            "}";
    }
}
