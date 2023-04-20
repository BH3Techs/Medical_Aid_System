package com.xplug.medical_aid_system.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TarrifClaim.
 */
@Entity
@Table(name = "tarrif_claim")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TarrifClaim implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "tarrif_code", nullable = false)
    private String tarrifCode;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    @JsonIgnoreProperties(value = { "tariffs", "wallets", "tarrifClaims", "plans" }, allowSetters = true)
    private Currency currency;

    @ManyToOne
    @JsonIgnoreProperties(value = { "tarrifClaims", "policy", "serviceProvider" }, allowSetters = true)
    private Claim claim;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TarrifClaim id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTarrifCode() {
        return this.tarrifCode;
    }

    public TarrifClaim tarrifCode(String tarrifCode) {
        this.setTarrifCode(tarrifCode);
        return this;
    }

    public void setTarrifCode(String tarrifCode) {
        this.tarrifCode = tarrifCode;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public TarrifClaim quantity(Integer quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getAmount() {
        return this.amount;
    }

    public TarrifClaim amount(Double amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return this.description;
    }

    public TarrifClaim description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public TarrifClaim currency(Currency currency) {
        this.setCurrency(currency);
        return this;
    }

    public Claim getClaim() {
        return this.claim;
    }

    public void setClaim(Claim claim) {
        this.claim = claim;
    }

    public TarrifClaim claim(Claim claim) {
        this.setClaim(claim);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TarrifClaim)) {
            return false;
        }
        return id != null && id.equals(((TarrifClaim) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TarrifClaim{" +
            "id=" + getId() +
            ", tarrifCode='" + getTarrifCode() + "'" +
            ", quantity=" + getQuantity() +
            ", amount=" + getAmount() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
