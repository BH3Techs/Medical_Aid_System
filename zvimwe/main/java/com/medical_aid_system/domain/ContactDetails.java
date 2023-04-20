package com.medical_aid_system.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ContactDetails.
 */
@Entity
@Table(name = "contact_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContactDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Pattern(regexp = "^[0][7][0-9]{8}$")
    @Column(name = "primary_phone_number", nullable = false)
    private String primaryPhoneNumber;

    @Pattern(regexp = "^[0][7][0-9]{8}$")
    @Column(name = "secondary_phone_number")
    private String secondaryPhoneNumber;

    @Column(name = "email_address")
    private String emailAddress;

    @NotNull
    @Column(name = "physical_address", nullable = false)
    private String physicalAddress;

    @Column(name = "whatsapp_number")
    private String whatsappNumber;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ContactDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrimaryPhoneNumber() {
        return this.primaryPhoneNumber;
    }

    public ContactDetails primaryPhoneNumber(String primaryPhoneNumber) {
        this.setPrimaryPhoneNumber(primaryPhoneNumber);
        return this;
    }

    public void setPrimaryPhoneNumber(String primaryPhoneNumber) {
        this.primaryPhoneNumber = primaryPhoneNumber;
    }

    public String getSecondaryPhoneNumber() {
        return this.secondaryPhoneNumber;
    }

    public ContactDetails secondaryPhoneNumber(String secondaryPhoneNumber) {
        this.setSecondaryPhoneNumber(secondaryPhoneNumber);
        return this;
    }

    public void setSecondaryPhoneNumber(String secondaryPhoneNumber) {
        this.secondaryPhoneNumber = secondaryPhoneNumber;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public ContactDetails emailAddress(String emailAddress) {
        this.setEmailAddress(emailAddress);
        return this;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhysicalAddress() {
        return this.physicalAddress;
    }

    public ContactDetails physicalAddress(String physicalAddress) {
        this.setPhysicalAddress(physicalAddress);
        return this;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public String getWhatsappNumber() {
        return this.whatsappNumber;
    }

    public ContactDetails whatsappNumber(String whatsappNumber) {
        this.setWhatsappNumber(whatsappNumber);
        return this;
    }

    public void setWhatsappNumber(String whatsappNumber) {
        this.whatsappNumber = whatsappNumber;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContactDetails)) {
            return false;
        }
        return id != null && id.equals(((ContactDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContactDetails{" +
            "id=" + getId() +
            ", primaryPhoneNumber='" + getPrimaryPhoneNumber() + "'" +
            ", secondaryPhoneNumber='" + getSecondaryPhoneNumber() + "'" +
            ", emailAddress='" + getEmailAddress() + "'" +
            ", physicalAddress='" + getPhysicalAddress() + "'" +
            ", whatsappNumber='" + getWhatsappNumber() + "'" +
            "}";
    }
}
