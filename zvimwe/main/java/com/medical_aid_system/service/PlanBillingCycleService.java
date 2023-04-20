package com.medical_aid_system.service;

import com.medical_aid_system.domain.PlanBillingCycle;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link PlanBillingCycle}.
 */
public interface PlanBillingCycleService {
    /**
     * Save a planBillingCycle.
     *
     * @param planBillingCycle the entity to save.
     * @return the persisted entity.
     */
    PlanBillingCycle save(PlanBillingCycle planBillingCycle);

    /**
     * Updates a planBillingCycle.
     *
     * @param planBillingCycle the entity to update.
     * @return the persisted entity.
     */
    PlanBillingCycle update(PlanBillingCycle planBillingCycle);

    /**
     * Partially updates a planBillingCycle.
     *
     * @param planBillingCycle the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PlanBillingCycle> partialUpdate(PlanBillingCycle planBillingCycle);

    /**
     * Get all the planBillingCycles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PlanBillingCycle> findAll(Pageable pageable);

    /**
     * Get the "id" planBillingCycle.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PlanBillingCycle> findOne(Long id);

    /**
     * Delete the "id" planBillingCycle.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
