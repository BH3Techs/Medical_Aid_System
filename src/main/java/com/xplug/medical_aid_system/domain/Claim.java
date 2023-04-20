package com.xplug.medical_aid_system.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xplug.medical_aid_system.domain.enumeration.ClaimStatus;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Claim.
 */
@Entity
@Table(name = "claim")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Claim implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "submission_date", nullable = false)
    private LocalDate submissionDate;

    @Column(name = "approval_date")
    private LocalDate approvalDate;

    @Column(name = "processing_date")
    private LocalDate processingDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "claim_status")
    private ClaimStatus claimStatus;

    @Column(name = "diagnosis")
    private String diagnosis;

    @Column(name = "claimant")
    private String claimant;

    @Column(name = "relationship_to_member")
    private String relationshipToMember;

    @OneToMany(mappedBy = "claim")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "currency", "claim" }, allowSetters = true)
    private Set<TarrifClaim> tarrifClaims = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "planBillingCycle", "claims", "nextOfKins", "plans" }, allowSetters = true)
    private Policy policy;

    @ManyToOne
    @JsonIgnoreProperties(value = { "contactDetails", "claims" }, allowSetters = true)
    private ServiceProvider serviceProvider;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Claim id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getSubmissionDate() {
        return this.submissionDate;
    }

    public Claim submissionDate(LocalDate submissionDate) {
        this.setSubmissionDate(submissionDate);
        return this;
    }

    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }

    public LocalDate getApprovalDate() {
        return this.approvalDate;
    }

    public Claim approvalDate(LocalDate approvalDate) {
        this.setApprovalDate(approvalDate);
        return this;
    }

    public void setApprovalDate(LocalDate approvalDate) {
        this.approvalDate = approvalDate;
    }

    public LocalDate getProcessingDate() {
        return this.processingDate;
    }

    public Claim processingDate(LocalDate processingDate) {
        this.setProcessingDate(processingDate);
        return this;
    }

    public void setProcessingDate(LocalDate processingDate) {
        this.processingDate = processingDate;
    }

    public ClaimStatus getClaimStatus() {
        return this.claimStatus;
    }

    public Claim claimStatus(ClaimStatus claimStatus) {
        this.setClaimStatus(claimStatus);
        return this;
    }

    public void setClaimStatus(ClaimStatus claimStatus) {
        this.claimStatus = claimStatus;
    }

    public String getDiagnosis() {
        return this.diagnosis;
    }

    public Claim diagnosis(String diagnosis) {
        this.setDiagnosis(diagnosis);
        return this;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getClaimant() {
        return this.claimant;
    }

    public Claim claimant(String claimant) {
        this.setClaimant(claimant);
        return this;
    }

    public void setClaimant(String claimant) {
        this.claimant = claimant;
    }

    public String getRelationshipToMember() {
        return this.relationshipToMember;
    }

    public Claim relationshipToMember(String relationshipToMember) {
        this.setRelationshipToMember(relationshipToMember);
        return this;
    }

    public void setRelationshipToMember(String relationshipToMember) {
        this.relationshipToMember = relationshipToMember;
    }

    public Set<TarrifClaim> getTarrifClaims() {
        return this.tarrifClaims;
    }

    public void setTarrifClaims(Set<TarrifClaim> tarrifClaims) {
        if (this.tarrifClaims != null) {
            this.tarrifClaims.forEach(i -> i.setClaim(null));
        }
        if (tarrifClaims != null) {
            tarrifClaims.forEach(i -> i.setClaim(this));
        }
        this.tarrifClaims = tarrifClaims;
    }

    public Claim tarrifClaims(Set<TarrifClaim> tarrifClaims) {
        this.setTarrifClaims(tarrifClaims);
        return this;
    }

    public Claim addTarrifClaim(TarrifClaim tarrifClaim) {
        this.tarrifClaims.add(tarrifClaim);
        tarrifClaim.setClaim(this);
        return this;
    }

    public Claim removeTarrifClaim(TarrifClaim tarrifClaim) {
        this.tarrifClaims.remove(tarrifClaim);
        tarrifClaim.setClaim(null);
        return this;
    }

    public Policy getPolicy() {
        return this.policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    public Claim policy(Policy policy) {
        this.setPolicy(policy);
        return this;
    }

    public ServiceProvider getServiceProvider() {
        return this.serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public Claim serviceProvider(ServiceProvider serviceProvider) {
        this.setServiceProvider(serviceProvider);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Claim)) {
            return false;
        }
        return id != null && id.equals(((Claim) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Claim{" +
            "id=" + getId() +
            ", submissionDate='" + getSubmissionDate() + "'" +
            ", approvalDate='" + getApprovalDate() + "'" +
            ", processingDate='" + getProcessingDate() + "'" +
            ", claimStatus='" + getClaimStatus() + "'" +
            ", diagnosis='" + getDiagnosis() + "'" +
            ", claimant='" + getClaimant() + "'" +
            ", relationshipToMember='" + getRelationshipToMember() + "'" +
            "}";
    }
}
