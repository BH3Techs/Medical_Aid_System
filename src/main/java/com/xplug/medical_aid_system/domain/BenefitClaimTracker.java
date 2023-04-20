package com.xplug.medical_aid_system.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BenefitClaimTracker.
 */
@Entity
@Table(name = "benefit_claim_tracker")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BenefitClaimTracker implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "reset_date")
    private LocalDate resetDate;

    @Column(name = "next_possible_claim_date")
    private LocalDate nextPossibleClaimDate;

    @Column(name = "current_limit_value")
    private String currentLimitValue;

    @Column(name = "current_limit_period")
    private Integer currentLimitPeriod;

    @JsonIgnoreProperties(value = { "benefitLimitType", "planBenefit" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private BenefitLimit benefitLimit;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BenefitClaimTracker id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getResetDate() {
        return this.resetDate;
    }

    public BenefitClaimTracker resetDate(LocalDate resetDate) {
        this.setResetDate(resetDate);
        return this;
    }

    public void setResetDate(LocalDate resetDate) {
        this.resetDate = resetDate;
    }

    public LocalDate getNextPossibleClaimDate() {
        return this.nextPossibleClaimDate;
    }

    public BenefitClaimTracker nextPossibleClaimDate(LocalDate nextPossibleClaimDate) {
        this.setNextPossibleClaimDate(nextPossibleClaimDate);
        return this;
    }

    public void setNextPossibleClaimDate(LocalDate nextPossibleClaimDate) {
        this.nextPossibleClaimDate = nextPossibleClaimDate;
    }

    public String getCurrentLimitValue() {
        return this.currentLimitValue;
    }

    public BenefitClaimTracker currentLimitValue(String currentLimitValue) {
        this.setCurrentLimitValue(currentLimitValue);
        return this;
    }

    public void setCurrentLimitValue(String currentLimitValue) {
        this.currentLimitValue = currentLimitValue;
    }

    public Integer getCurrentLimitPeriod() {
        return this.currentLimitPeriod;
    }

    public BenefitClaimTracker currentLimitPeriod(Integer currentLimitPeriod) {
        this.setCurrentLimitPeriod(currentLimitPeriod);
        return this;
    }

    public void setCurrentLimitPeriod(Integer currentLimitPeriod) {
        this.currentLimitPeriod = currentLimitPeriod;
    }

    public BenefitLimit getBenefitLimit() {
        return this.benefitLimit;
    }

    public void setBenefitLimit(BenefitLimit benefitLimit) {
        this.benefitLimit = benefitLimit;
    }

    public BenefitClaimTracker benefitLimit(BenefitLimit benefitLimit) {
        this.setBenefitLimit(benefitLimit);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BenefitClaimTracker)) {
            return false;
        }
        return id != null && id.equals(((BenefitClaimTracker) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BenefitClaimTracker{" +
            "id=" + getId() +
            ", resetDate='" + getResetDate() + "'" +
            ", nextPossibleClaimDate='" + getNextPossibleClaimDate() + "'" +
            ", currentLimitValue='" + getCurrentLimitValue() + "'" +
            ", currentLimitPeriod=" + getCurrentLimitPeriod() +
            "}";
    }
}
