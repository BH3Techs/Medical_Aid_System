package com.xplug.medical_aid_system.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Currency.
 */
@Entity
@Table(name = "currency")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Currency implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "currency_name", nullable = false, unique = true)
    private String currencyName;

    @NotNull
    @Column(name = "currency_code", nullable = false, unique = true)
    private String currencyCode;

    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "currency")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "currency", "benefit" }, allowSetters = true)
    private Set<Tariff> tariffs = new HashSet<>();

    @OneToMany(mappedBy = "currency")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "currency" }, allowSetters = true)
    private Set<Wallet> wallets = new HashSet<>();

    @OneToMany(mappedBy = "currency")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "currency", "claim" }, allowSetters = true)
    private Set<TarrifClaim> tarrifClaims = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "currencies", "planBenefits", "planBillingCycles", "policies", "planCategory" }, allowSetters = true)
    private Plans plans;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Currency id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrencyName() {
        return this.currencyName;
    }

    public Currency currencyName(String currencyName) {
        this.setCurrencyName(currencyName);
        return this;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public Currency currencyCode(String currencyCode) {
        this.setCurrencyCode(currencyCode);
        return this;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Boolean getActive() {
        return this.active;
    }

    public Currency active(Boolean active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<Tariff> getTariffs() {
        return this.tariffs;
    }

    public void setTariffs(Set<Tariff> tariffs) {
        if (this.tariffs != null) {
            this.tariffs.forEach(i -> i.setCurrency(null));
        }
        if (tariffs != null) {
            tariffs.forEach(i -> i.setCurrency(this));
        }
        this.tariffs = tariffs;
    }

    public Currency tariffs(Set<Tariff> tariffs) {
        this.setTariffs(tariffs);
        return this;
    }

    public Currency addTariff(Tariff tariff) {
        this.tariffs.add(tariff);
        tariff.setCurrency(this);
        return this;
    }

    public Currency removeTariff(Tariff tariff) {
        this.tariffs.remove(tariff);
        tariff.setCurrency(null);
        return this;
    }

    public Set<Wallet> getWallets() {
        return this.wallets;
    }

    public void setWallets(Set<Wallet> wallets) {
        if (this.wallets != null) {
            this.wallets.forEach(i -> i.setCurrency(null));
        }
        if (wallets != null) {
            wallets.forEach(i -> i.setCurrency(this));
        }
        this.wallets = wallets;
    }

    public Currency wallets(Set<Wallet> wallets) {
        this.setWallets(wallets);
        return this;
    }

    public Currency addWallet(Wallet wallet) {
        this.wallets.add(wallet);
        wallet.setCurrency(this);
        return this;
    }

    public Currency removeWallet(Wallet wallet) {
        this.wallets.remove(wallet);
        wallet.setCurrency(null);
        return this;
    }

    public Set<TarrifClaim> getTarrifClaims() {
        return this.tarrifClaims;
    }

    public void setTarrifClaims(Set<TarrifClaim> tarrifClaims) {
        if (this.tarrifClaims != null) {
            this.tarrifClaims.forEach(i -> i.setCurrency(null));
        }
        if (tarrifClaims != null) {
            tarrifClaims.forEach(i -> i.setCurrency(this));
        }
        this.tarrifClaims = tarrifClaims;
    }

    public Currency tarrifClaims(Set<TarrifClaim> tarrifClaims) {
        this.setTarrifClaims(tarrifClaims);
        return this;
    }

    public Currency addTarrifClaim(TarrifClaim tarrifClaim) {
        this.tarrifClaims.add(tarrifClaim);
        tarrifClaim.setCurrency(this);
        return this;
    }

    public Currency removeTarrifClaim(TarrifClaim tarrifClaim) {
        this.tarrifClaims.remove(tarrifClaim);
        tarrifClaim.setCurrency(null);
        return this;
    }

    public Plans getPlans() {
        return this.plans;
    }

    public void setPlans(Plans plans) {
        this.plans = plans;
    }

    public Currency plans(Plans plans) {
        this.setPlans(plans);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Currency)) {
            return false;
        }
        return id != null && id.equals(((Currency) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Currency{" +
            "id=" + getId() +
            ", currencyName='" + getCurrencyName() + "'" +
            ", currencyCode='" + getCurrencyCode() + "'" +
            ", active='" + getActive() + "'" +
            "}";
    }
}
