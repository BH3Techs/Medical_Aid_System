package com.xplug.medical_aid_system.repository;

import com.xplug.medical_aid_system.domain.Plans;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class PlansRepositoryWithBagRelationshipsImpl implements PlansRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Plans> fetchBagRelationships(Optional<Plans> plans) {
        return plans.map(this::fetchPolicies);
    }

    @Override
    public Page<Plans> fetchBagRelationships(Page<Plans> plans) {
        return new PageImpl<>(fetchBagRelationships(plans.getContent()), plans.getPageable(), plans.getTotalElements());
    }

    @Override
    public List<Plans> fetchBagRelationships(List<Plans> plans) {
        return Optional.of(plans).map(this::fetchPolicies).orElse(Collections.emptyList());
    }

    Plans fetchPolicies(Plans result) {
        return entityManager
            .createQuery("select plans from Plans plans left join fetch plans.policies where plans is :plans", Plans.class)
            .setParameter("plans", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Plans> fetchPolicies(List<Plans> plans) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, plans.size()).forEach(index -> order.put(plans.get(index).getId(), index));
        List<Plans> result = entityManager
            .createQuery("select distinct plans from Plans plans left join fetch plans.policies where plans in :plans", Plans.class)
            .setParameter("plans", plans)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
