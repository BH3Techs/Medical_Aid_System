package com.xplug.medical_aid_system.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Individual.
 */
@Entity
@Table(name = "individual")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Individual implements Serializable {

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

    @Column(name = "gender")
    private String gender;

    @Pattern(regexp = "^\\d{2}-[0-9]{5}[\\w]\\d{2}$")
    @Column(name = "national_id")
    private String nationalId;

    @OneToOne
    @JoinColumn(unique = true)
    private ContactDetails contactDetails;

    @OneToOne
    @JoinColumn(unique = true)
    private BankingDetails bankingDetails;

    @JsonIgnoreProperties(value = { "conditions" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private RiskProfile riskProfile;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Individual id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Individual firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Individual lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getInitial() {
        return this.initial;
    }

    public Individual initial(String initial) {
        this.setInitial(initial);
        return this;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public Individual dateOfBirth(LocalDate dateOfBirth) {
        this.setDateOfBirth(dateOfBirth);
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return this.gender;
    }

    public Individual gender(String gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationalId() {
        return this.nationalId;
    }

    public Individual nationalId(String nationalId) {
        this.setNationalId(nationalId);
        return this;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public ContactDetails getContactDetails() {
        return this.contactDetails;
    }

    public void setContactDetails(ContactDetails contactDetails) {
        this.contactDetails = contactDetails;
    }

    public Individual contactDetails(ContactDetails contactDetails) {
        this.setContactDetails(contactDetails);
        return this;
    }

    public BankingDetails getBankingDetails() {
        return this.bankingDetails;
    }

    public void setBankingDetails(BankingDetails bankingDetails) {
        this.bankingDetails = bankingDetails;
    }

    public Individual bankingDetails(BankingDetails bankingDetails) {
        this.setBankingDetails(bankingDetails);
        return this;
    }

    public RiskProfile getRiskProfile() {
        return this.riskProfile;
    }

    public void setRiskProfile(RiskProfile riskProfile) {
        this.riskProfile = riskProfile;
    }

    public Individual riskProfile(RiskProfile riskProfile) {
        this.setRiskProfile(riskProfile);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Individual)) {
            return false;
        }
        return id != null && id.equals(((Individual) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Individual{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", initial='" + getInitial() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", gender='" + getGender() + "'" +
            ", nationalId='" + getNationalId() + "'" +
            "}";
    }
}
