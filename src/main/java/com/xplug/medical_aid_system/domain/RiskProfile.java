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
 * A RiskProfile.
 */
@Entity
@Table(name = "risk_profile")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RiskProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "total_risk_score")
    private Double totalRiskScore;

    @NotNull
    @Column(name = "life_style", nullable = false)
    private String lifeStyle;

    @OneToMany(mappedBy = "riskProfile")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "riskProfile" }, allowSetters = true)
    private Set<Condition> conditions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RiskProfile id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalRiskScore() {
        return this.totalRiskScore;
    }

    public RiskProfile totalRiskScore(Double totalRiskScore) {
        this.setTotalRiskScore(totalRiskScore);
        return this;
    }

    public void setTotalRiskScore(Double totalRiskScore) {
        this.totalRiskScore = totalRiskScore;
    }

    public String getLifeStyle() {
        return this.lifeStyle;
    }

    public RiskProfile lifeStyle(String lifeStyle) {
        this.setLifeStyle(lifeStyle);
        return this;
    }

    public void setLifeStyle(String lifeStyle) {
        this.lifeStyle = lifeStyle;
    }

    public Set<Condition> getConditions() {
        return this.conditions;
    }

    public void setConditions(Set<Condition> conditions) {
        if (this.conditions != null) {
            this.conditions.forEach(i -> i.setRiskProfile(null));
        }
        if (conditions != null) {
            conditions.forEach(i -> i.setRiskProfile(this));
        }
        this.conditions = conditions;
    }

    public RiskProfile conditions(Set<Condition> conditions) {
        this.setConditions(conditions);
        return this;
    }

    public RiskProfile addCondition(Condition condition) {
        this.conditions.add(condition);
        condition.setRiskProfile(this);
        return this;
    }

    public RiskProfile removeCondition(Condition condition) {
        this.conditions.remove(condition);
        condition.setRiskProfile(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RiskProfile)) {
            return false;
        }
        return id != null && id.equals(((RiskProfile) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RiskProfile{" +
            "id=" + getId() +
            ", totalRiskScore=" + getTotalRiskScore() +
            ", lifeStyle='" + getLifeStyle() + "'" +
            "}";
    }
}
