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
 * A Plans.
 */
@Entity
@Table(name = "plans")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Plans implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "plan_code", nullable = false, unique = true)
    private String planCode;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "base_premium")
    private String basePremium;

    @NotNull
    @Column(name = "cover_amount", nullable = false)
    private String coverAmount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "cover_period_unit", nullable = false)
    private PeriodUnit coverPeriodUnit;

    @NotNull
    @Column(name = "cover_period_value", nullable = false)
    private Integer coverPeriodValue;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @OneToMany(mappedBy = "plans")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tariffs", "wallets", "tarrifClaims", "plans" }, allowSetters = true)
    private Set<Currency> currencies = new HashSet<>();

    @OneToMany(mappedBy = "plans")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "benefitLimits", "plans", "benefitType" }, allowSetters = true)
    private Set<PlanBenefit> planBenefits = new HashSet<>();

    @OneToMany(mappedBy = "plans")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "plans" }, allowSetters = true)
    private Set<PlanBillingCycle> planBillingCycles = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_plans__policy",
        joinColumns = @JoinColumn(name = "plans_id"),
        inverseJoinColumns = @JoinColumn(name = "policy_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "planBillingCycle", "claims", "nextOfKins", "plans" }, allowSetters = true)
    private Set<Policy> policies = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "plans" }, allowSetters = true)
    private PlanCategory planCategory;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Plans id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlanCode() {
        return this.planCode;
    }

    public Plans planCode(String planCode) {
        this.setPlanCode(planCode);
        return this;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public String getName() {
        return this.name;
    }

    public Plans name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBasePremium() {
        return this.basePremium;
    }

    public Plans basePremium(String basePremium) {
        this.setBasePremium(basePremium);
        return this;
    }

    public void setBasePremium(String basePremium) {
        this.basePremium = basePremium;
    }

    public String getCoverAmount() {
        return this.coverAmount;
    }

    public Plans coverAmount(String coverAmount) {
        this.setCoverAmount(coverAmount);
        return this;
    }

    public void setCoverAmount(String coverAmount) {
        this.coverAmount = coverAmount;
    }

    public PeriodUnit getCoverPeriodUnit() {
        return this.coverPeriodUnit;
    }

    public Plans coverPeriodUnit(PeriodUnit coverPeriodUnit) {
        this.setCoverPeriodUnit(coverPeriodUnit);
        return this;
    }

    public void setCoverPeriodUnit(PeriodUnit coverPeriodUnit) {
        this.coverPeriodUnit = coverPeriodUnit;
    }

    public Integer getCoverPeriodValue() {
        return this.coverPeriodValue;
    }

    public Plans coverPeriodValue(Integer coverPeriodValue) {
        this.setCoverPeriodValue(coverPeriodValue);
        return this;
    }

    public void setCoverPeriodValue(Integer coverPeriodValue) {
        this.coverPeriodValue = coverPeriodValue;
    }

    public Boolean getActive() {
        return this.active;
    }

    public Plans active(Boolean active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<Currency> getCurrencies() {
        return this.currencies;
    }

    public void setCurrencies(Set<Currency> currencies) {
        if (this.currencies != null) {
            this.currencies.forEach(i -> i.setPlans(null));
        }
        if (currencies != null) {
            currencies.forEach(i -> i.setPlans(this));
        }
        this.currencies = currencies;
    }

    public Plans currencies(Set<Currency> currencies) {
        this.setCurrencies(currencies);
        return this;
    }

    public Plans addCurrency(Currency currency) {
        this.currencies.add(currency);
        currency.setPlans(this);
        return this;
    }

    public Plans removeCurrency(Currency currency) {
        this.currencies.remove(currency);
        currency.setPlans(null);
        return this;
    }

    public Set<PlanBenefit> getPlanBenefits() {
        return this.planBenefits;
    }

    public void setPlanBenefits(Set<PlanBenefit> planBenefits) {
        if (this.planBenefits != null) {
            this.planBenefits.forEach(i -> i.setPlans(null));
        }
        if (planBenefits != null) {
            planBenefits.forEach(i -> i.setPlans(this));
        }
        this.planBenefits = planBenefits;
    }

    public Plans planBenefits(Set<PlanBenefit> planBenefits) {
        this.setPlanBenefits(planBenefits);
        return this;
    }

    public Plans addPlanBenefit(PlanBenefit planBenefit) {
        this.planBenefits.add(planBenefit);
        planBenefit.setPlans(this);
        return this;
    }

    public Plans removePlanBenefit(PlanBenefit planBenefit) {
        this.planBenefits.remove(planBenefit);
        planBenefit.setPlans(null);
        return this;
    }

    public Set<PlanBillingCycle> getPlanBillingCycles() {
        return this.planBillingCycles;
    }

    public void setPlanBillingCycles(Set<PlanBillingCycle> planBillingCycles) {
        if (this.planBillingCycles != null) {
            this.planBillingCycles.forEach(i -> i.setPlans(null));
        }
        if (planBillingCycles != null) {
            planBillingCycles.forEach(i -> i.setPlans(this));
        }
        this.planBillingCycles = planBillingCycles;
    }

    public Plans planBillingCycles(Set<PlanBillingCycle> planBillingCycles) {
        this.setPlanBillingCycles(planBillingCycles);
        return this;
    }

    public Plans addPlanBillingCycle(PlanBillingCycle planBillingCycle) {
        this.planBillingCycles.add(planBillingCycle);
        planBillingCycle.setPlans(this);
        return this;
    }

    public Plans removePlanBillingCycle(PlanBillingCycle planBillingCycle) {
        this.planBillingCycles.remove(planBillingCycle);
        planBillingCycle.setPlans(null);
        return this;
    }

    public Set<Policy> getPolicies() {
        return this.policies;
    }

    public void setPolicies(Set<Policy> policies) {
        this.policies = policies;
    }

    public Plans policies(Set<Policy> policies) {
        this.setPolicies(policies);
        return this;
    }

    public Plans addPolicy(Policy policy) {
        this.policies.add(policy);
        policy.getPlans().add(this);
        return this;
    }

    public Plans removePolicy(Policy policy) {
        this.policies.remove(policy);
        policy.getPlans().remove(this);
        return this;
    }

    public PlanCategory getPlanCategory() {
        return this.planCategory;
    }

    public void setPlanCategory(PlanCategory planCategory) {
        this.planCategory = planCategory;
    }

    public Plans planCategory(PlanCategory planCategory) {
        this.setPlanCategory(planCategory);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Plans)) {
            return false;
        }
        return id != null && id.equals(((Plans) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Plans{" +
            "id=" + getId() +
            ", planCode='" + getPlanCode() + "'" +
            ", name='" + getName() + "'" +
            ", basePremium='" + getBasePremium() + "'" +
            ", coverAmount='" + getCoverAmount() + "'" +
            ", coverPeriodUnit='" + getCoverPeriodUnit() + "'" +
            ", coverPeriodValue=" + getCoverPeriodValue() +
            ", active='" + getActive() + "'" +
            "}";
    }
}
