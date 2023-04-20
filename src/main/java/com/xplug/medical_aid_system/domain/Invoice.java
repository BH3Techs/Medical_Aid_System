package com.xplug.medical_aid_system.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xplug.medical_aid_system.domain.enumeration.InvoiceStatus;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Invoice.
 */
@Entity
@Table(name = "invoice")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "invoice_number", nullable = false)
    private String invoiceNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "invoice_status", nullable = false)
    private InvoiceStatus invoiceStatus;

    @NotNull
    @Column(name = "amount_payable", nullable = false)
    private Double amountPayable;

    @NotNull
    @Column(name = "invoice_date", nullable = false)
    private Instant invoiceDate;

    @NotNull
    @Column(name = "next_invoice_date", nullable = false)
    private LocalDate nextInvoiceDate;

    @NotNull
    @Column(name = "invoice_amount", nullable = false)
    private Double invoiceAmount;

    @Column(name = "expected_payment_date")
    private LocalDate expectedPaymentDate;

    @NotNull
    @Column(name = "grace_period", nullable = false)
    private LocalDate gracePeriod;

    @JsonIgnoreProperties(value = { "planBillingCycle", "claims", "nextOfKins", "plans" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Policy policy;

    @OneToOne
    @JoinColumn(unique = true)
    private ContactDetails contactDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Invoice id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return this.invoiceNumber;
    }

    public Invoice invoiceNumber(String invoiceNumber) {
        this.setInvoiceNumber(invoiceNumber);
        return this;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public InvoiceStatus getInvoiceStatus() {
        return this.invoiceStatus;
    }

    public Invoice invoiceStatus(InvoiceStatus invoiceStatus) {
        this.setInvoiceStatus(invoiceStatus);
        return this;
    }

    public void setInvoiceStatus(InvoiceStatus invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public Double getAmountPayable() {
        return this.amountPayable;
    }

    public Invoice amountPayable(Double amountPayable) {
        this.setAmountPayable(amountPayable);
        return this;
    }

    public void setAmountPayable(Double amountPayable) {
        this.amountPayable = amountPayable;
    }

    public Instant getInvoiceDate() {
        return this.invoiceDate;
    }

    public Invoice invoiceDate(Instant invoiceDate) {
        this.setInvoiceDate(invoiceDate);
        return this;
    }

    public void setInvoiceDate(Instant invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public LocalDate getNextInvoiceDate() {
        return this.nextInvoiceDate;
    }

    public Invoice nextInvoiceDate(LocalDate nextInvoiceDate) {
        this.setNextInvoiceDate(nextInvoiceDate);
        return this;
    }

    public void setNextInvoiceDate(LocalDate nextInvoiceDate) {
        this.nextInvoiceDate = nextInvoiceDate;
    }

    public Double getInvoiceAmount() {
        return this.invoiceAmount;
    }

    public Invoice invoiceAmount(Double invoiceAmount) {
        this.setInvoiceAmount(invoiceAmount);
        return this;
    }

    public void setInvoiceAmount(Double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public LocalDate getExpectedPaymentDate() {
        return this.expectedPaymentDate;
    }

    public Invoice expectedPaymentDate(LocalDate expectedPaymentDate) {
        this.setExpectedPaymentDate(expectedPaymentDate);
        return this;
    }

    public void setExpectedPaymentDate(LocalDate expectedPaymentDate) {
        this.expectedPaymentDate = expectedPaymentDate;
    }

    public LocalDate getGracePeriod() {
        return this.gracePeriod;
    }

    public Invoice gracePeriod(LocalDate gracePeriod) {
        this.setGracePeriod(gracePeriod);
        return this;
    }

    public void setGracePeriod(LocalDate gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    public Policy getPolicy() {
        return this.policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    public Invoice policy(Policy policy) {
        this.setPolicy(policy);
        return this;
    }

    public ContactDetails getContactDetails() {
        return this.contactDetails;
    }

    public void setContactDetails(ContactDetails contactDetails) {
        this.contactDetails = contactDetails;
    }

    public Invoice contactDetails(ContactDetails contactDetails) {
        this.setContactDetails(contactDetails);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Invoice)) {
            return false;
        }
        return id != null && id.equals(((Invoice) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Invoice{" +
            "id=" + getId() +
            ", invoiceNumber='" + getInvoiceNumber() + "'" +
            ", invoiceStatus='" + getInvoiceStatus() + "'" +
            ", amountPayable=" + getAmountPayable() +
            ", invoiceDate='" + getInvoiceDate() + "'" +
            ", nextInvoiceDate='" + getNextInvoiceDate() + "'" +
            ", invoiceAmount=" + getInvoiceAmount() +
            ", expectedPaymentDate='" + getExpectedPaymentDate() + "'" +
            ", gracePeriod='" + getGracePeriod() + "'" +
            "}";
    }
}
