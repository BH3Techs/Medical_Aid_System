package com.medical_aid_system.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.medical_aid_system.domain.enumeration.PolicyStatus;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Policy.
 */
@Entity
@Table(name = "policy")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Policy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "policy_number", nullable = false)
    private String policyNumber;

    @NotNull
    @Column(name = "suffix", nullable = false)
    private String suffix;

    @Column(name = "pricing_group")
    private String pricingGroup;

    @Column(name = "next_of_kin")
    private String nextOfKin;

    @Column(name = "member_identifier")
    private String memberIdentifier;

    @Column(name = "parent_policy")
    private String parentPolicy;

    @Column(name = "sponsor_identifier")
    private String sponsorIdentifier;

    @Column(name = "sponsor_type")
    private String sponsorType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PolicyStatus status;

    @NotNull
    @Column(name = "balance", nullable = false)
    private Double balance;

    @JsonIgnoreProperties(value = { "plans" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private PlanBillingCycle planBillingCycle;

    @OneToMany(mappedBy = "policy")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tarrifClaims", "policy", "serviceProvider" }, allowSetters = true)
    private Set<Claim> claims = new HashSet<>();

    @OneToMany(mappedBy = "policy")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "contactDetails", "policy" }, allowSetters = true)
    private Set<NextOfKin> nextOfKins = new HashSet<>();

    @ManyToMany(mappedBy = "policies")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "currencies", "planBenefits", "planBillingCycles", "policies", "planCategory" }, allowSetters = true)
    private Set<Plans> plans = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Policy id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPolicyNumber() {
        return this.policyNumber;
    }

    public Policy policyNumber(String policyNumber) {
        this.setPolicyNumber(policyNumber);
        return this;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public Policy suffix(String suffix) {
        this.setSuffix(suffix);
        return this;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getPricingGroup() {
        return this.pricingGroup;
    }

    public Policy pricingGroup(String pricingGroup) {
        this.setPricingGroup(pricingGroup);
        return this;
    }

    public void setPricingGroup(String pricingGroup) {
        this.pricingGroup = pricingGroup;
    }

    public String getNextOfKin() {
        return this.nextOfKin;
    }

    public Policy nextOfKin(String nextOfKin) {
        this.setNextOfKin(nextOfKin);
        return this;
    }

    public void setNextOfKin(String nextOfKin) {
        this.nextOfKin = nextOfKin;
    }

    public String getMemberIdentifier() {
        return this.memberIdentifier;
    }

    public Policy memberIdentifier(String memberIdentifier) {
        this.setMemberIdentifier(memberIdentifier);
        return this;
    }

    public void setMemberIdentifier(String memberIdentifier) {
        this.memberIdentifier = memberIdentifier;
    }

    public String getParentPolicy() {
        return this.parentPolicy;
    }

    public Policy parentPolicy(String parentPolicy) {
        this.setParentPolicy(parentPolicy);
        return this;
    }

    public void setParentPolicy(String parentPolicy) {
        this.parentPolicy = parentPolicy;
    }

    public String getSponsorIdentifier() {
        return this.sponsorIdentifier;
    }

    public Policy sponsorIdentifier(String sponsorIdentifier) {
        this.setSponsorIdentifier(sponsorIdentifier);
        return this;
    }

    public void setSponsorIdentifier(String sponsorIdentifier) {
        this.sponsorIdentifier = sponsorIdentifier;
    }

    public String getSponsorType() {
        return this.sponsorType;
    }

    public Policy sponsorType(String sponsorType) {
        this.setSponsorType(sponsorType);
        return this;
    }

    public void setSponsorType(String sponsorType) {
        this.sponsorType = sponsorType;
    }

    public PolicyStatus getStatus() {
        return this.status;
    }

    public Policy status(PolicyStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(PolicyStatus status) {
        this.status = status;
    }

    public Double getBalance() {
        return this.balance;
    }

    public Policy balance(Double balance) {
        this.setBalance(balance);
        return this;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public PlanBillingCycle getPlanBillingCycle() {
        return this.planBillingCycle;
    }

    public void setPlanBillingCycle(PlanBillingCycle planBillingCycle) {
        this.planBillingCycle = planBillingCycle;
    }

    public Policy planBillingCycle(PlanBillingCycle planBillingCycle) {
        this.setPlanBillingCycle(planBillingCycle);
        return this;
    }

    public Set<Claim> getClaims() {
        return this.claims;
    }

    public void setClaims(Set<Claim> claims) {
        if (this.claims != null) {
            this.claims.forEach(i -> i.setPolicy(null));
        }
        if (claims != null) {
            claims.forEach(i -> i.setPolicy(this));
        }
        this.claims = claims;
    }

    public Policy claims(Set<Claim> claims) {
        this.setClaims(claims);
        return this;
    }

    public Policy addClaim(Claim claim) {
        this.claims.add(claim);
        claim.setPolicy(this);
        return this;
    }

    public Policy removeClaim(Claim claim) {
        this.claims.remove(claim);
        claim.setPolicy(null);
        return this;
    }

    public Set<NextOfKin> getNextOfKins() {
        return this.nextOfKins;
    }

    public void setNextOfKins(Set<NextOfKin> nextOfKins) {
        if (this.nextOfKins != null) {
            this.nextOfKins.forEach(i -> i.setPolicy(null));
        }
        if (nextOfKins != null) {
            nextOfKins.forEach(i -> i.setPolicy(this));
        }
        this.nextOfKins = nextOfKins;
    }

    public Policy nextOfKins(Set<NextOfKin> nextOfKins) {
        this.setNextOfKins(nextOfKins);
        return this;
    }

    public Policy addNextOfKin(NextOfKin nextOfKin) {
        this.nextOfKins.add(nextOfKin);
        nextOfKin.setPolicy(this);
        return this;
    }

    public Policy removeNextOfKin(NextOfKin nextOfKin) {
        this.nextOfKins.remove(nextOfKin);
        nextOfKin.setPolicy(null);
        return this;
    }

    public Set<Plans> getPlans() {
        return this.plans;
    }

    public void setPlans(Set<Plans> plans) {
        if (this.plans != null) {
            this.plans.forEach(i -> i.removePolicy(this));
        }
        if (plans != null) {
            plans.forEach(i -> i.addPolicy(this));
        }
        this.plans = plans;
    }

    public Policy plans(Set<Plans> plans) {
        this.setPlans(plans);
        return this;
    }

    public Policy addPlans(Plans plans) {
        this.plans.add(plans);
        plans.getPolicies().add(this);
        return this;
    }

    public Policy removePlans(Plans plans) {
        this.plans.remove(plans);
        plans.getPolicies().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Policy)) {
            return false;
        }
        return id != null && id.equals(((Policy) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Policy{" +
            "id=" + getId() +
            ", policyNumber='" + getPolicyNumber() + "'" +
            ", suffix='" + getSuffix() + "'" +
            ", pricingGroup='" + getPricingGroup() + "'" +
            ", nextOfKin='" + getNextOfKin() + "'" +
            ", memberIdentifier='" + getMemberIdentifier() + "'" +
            ", parentPolicy='" + getParentPolicy() + "'" +
            ", sponsorIdentifier='" + getSponsorIdentifier() + "'" +
            ", sponsorType='" + getSponsorType() + "'" +
            ", status='" + getStatus() + "'" +
            ", balance=" + getBalance() +
            "}";
    }
}
