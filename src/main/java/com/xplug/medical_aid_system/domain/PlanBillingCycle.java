package com.xplug.medical_aid_system.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xplug.medical_aid_system.domain.enumeration.DateConfiguration;
import com.xplug.medical_aid_system.domain.enumeration.PeriodUnit;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PlanBillingCycle.
 */
@Entity
@Table(name = "plan_billing_cycle")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlanBillingCycle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "period_unit", nullable = false)
    private PeriodUnit periodUnit;

    @NotNull
    @Column(name = "period_value", nullable = false)
    private Integer periodValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "date_configuration")
    private DateConfiguration dateConfiguration;

    @Column(name = "billing_date")
    private String billingDate;

    @ManyToOne
    @JsonIgnoreProperties(value = { "currencies", "planBenefits", "planBillingCycles", "policies", "planCategory" }, allowSetters = true)
    private Plans plans;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PlanBillingCycle id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PeriodUnit getPeriodUnit() {
        return this.periodUnit;
    }

    public PlanBillingCycle periodUnit(PeriodUnit periodUnit) {
        this.setPeriodUnit(periodUnit);
        return this;
    }

    public void setPeriodUnit(PeriodUnit periodUnit) {
        this.periodUnit = periodUnit;
    }

    public Integer getPeriodValue() {
        return this.periodValue;
    }

    public PlanBillingCycle periodValue(Integer periodValue) {
        this.setPeriodValue(periodValue);
        return this;
    }

    public void setPeriodValue(Integer periodValue) {
        this.periodValue = periodValue;
    }

    public DateConfiguration getDateConfiguration() {
        return this.dateConfiguration;
    }

    public PlanBillingCycle dateConfiguration(DateConfiguration dateConfiguration) {
        this.setDateConfiguration(dateConfiguration);
        return this;
    }

    public void setDateConfiguration(DateConfiguration dateConfiguration) {
        this.dateConfiguration = dateConfiguration;
    }

    public String getBillingDate() {
        return this.billingDate;
    }

    public PlanBillingCycle billingDate(String billingDate) {
        this.setBillingDate(billingDate);
        return this;
    }

    public void setBillingDate(String billingDate) {
        this.billingDate = billingDate;
    }

    public Plans getPlans() {
        return this.plans;
    }

    public void setPlans(Plans plans) {
        this.plans = plans;
    }

    public PlanBillingCycle plans(Plans plans) {
        this.setPlans(plans);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlanBillingCycle)) {
            return false;
        }
        return id != null && id.equals(((PlanBillingCycle) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlanBillingCycle{" +
            "id=" + getId() +
            ", periodUnit='" + getPeriodUnit() + "'" +
            ", periodValue=" + getPeriodValue() +
            ", dateConfiguration='" + getDateConfiguration() + "'" +
            ", billingDate='" + getBillingDate() + "'" +
            "}";
    }
}
