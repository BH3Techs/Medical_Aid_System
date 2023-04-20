package com.medical_aid_system.domain;

import com.medical_aid_system.domain.enumeration.SponsorType;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SponsorAdministration.
 */
@Entity
@Table(name = "sponsor_administration")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SponsorAdministration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "initial")
    private String initial;

    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @NotNull
    @Column(name = "sponsor_id", nullable = false)
    private String sponsorId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sponsor_type", nullable = false)
    private SponsorType sponsorType;

    @OneToOne
    @JoinColumn(unique = true)
    private ContactDetails contactDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SponsorAdministration id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public SponsorAdministration firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public SponsorAdministration lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getInitial() {
        return this.initial;
    }

    public SponsorAdministration initial(String initial) {
        this.setInitial(initial);
        return this;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public SponsorAdministration dateOfBirth(LocalDate dateOfBirth) {
        this.setDateOfBirth(dateOfBirth);
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSponsorId() {
        return this.sponsorId;
    }

    public SponsorAdministration sponsorId(String sponsorId) {
        this.setSponsorId(sponsorId);
        return this;
    }

    public void setSponsorId(String sponsorId) {
        this.sponsorId = sponsorId;
    }

    public SponsorType getSponsorType() {
        return this.sponsorType;
    }

    public SponsorAdministration sponsorType(SponsorType sponsorType) {
        this.setSponsorType(sponsorType);
        return this;
    }

    public void setSponsorType(SponsorType sponsorType) {
        this.sponsorType = sponsorType;
    }

    public ContactDetails getContactDetails() {
        return this.contactDetails;
    }

    public void setContactDetails(ContactDetails contactDetails) {
        this.contactDetails = contactDetails;
    }

    public SponsorAdministration contactDetails(ContactDetails contactDetails) {
        this.setContactDetails(contactDetails);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SponsorAdministration)) {
            return false;
        }
        return id != null && id.equals(((SponsorAdministration) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SponsorAdministration{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", initial='" + getInitial() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", sponsorId='" + getSponsorId() + "'" +
            ", sponsorType='" + getSponsorType() + "'" +
            "}";
    }
}
