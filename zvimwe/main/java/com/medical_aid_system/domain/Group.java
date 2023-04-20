package com.medical_aid_system.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Group.
 */
@Entity
@Table(name = "jhi_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "group_type")
    private String groupType;

    @NotNull
    @Column(name = "date_registered", nullable = false)
    private LocalDate dateRegistered;

    @OneToOne
    @JoinColumn(unique = true)
    private ContactDetails contactDetails;

    @OneToOne
    @JoinColumn(unique = true)
    private BankingDetails bankingDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Group id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Group name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupType() {
        return this.groupType;
    }

    public Group groupType(String groupType) {
        this.setGroupType(groupType);
        return this;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public LocalDate getDateRegistered() {
        return this.dateRegistered;
    }

    public Group dateRegistered(LocalDate dateRegistered) {
        this.setDateRegistered(dateRegistered);
        return this;
    }

    public void setDateRegistered(LocalDate dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public ContactDetails getContactDetails() {
        return this.contactDetails;
    }

    public void setContactDetails(ContactDetails contactDetails) {
        this.contactDetails = contactDetails;
    }

    public Group contactDetails(ContactDetails contactDetails) {
        this.setContactDetails(contactDetails);
        return this;
    }

    public BankingDetails getBankingDetails() {
        return this.bankingDetails;
    }

    public void setBankingDetails(BankingDetails bankingDetails) {
        this.bankingDetails = bankingDetails;
    }

    public Group bankingDetails(BankingDetails bankingDetails) {
        this.setBankingDetails(bankingDetails);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Group)) {
            return false;
        }
        return id != null && id.equals(((Group) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Group{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", groupType='" + getGroupType() + "'" +
            ", dateRegistered='" + getDateRegistered() + "'" +
            "}";
    }
}
