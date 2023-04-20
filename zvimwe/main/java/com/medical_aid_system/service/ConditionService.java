package com.medical_aid_system.service;

import com.medical_aid_system.domain.Condition;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Condition}.
 */
public interface ConditionService {
    /**
     * Save a condition.
     *
     * @param condition the entity to save.
     * @return the persisted entity.
     */
    Condition save(Condition condition);

    /**
     * Updates a condition.
     *
     * @param condition the entity to update.
     * @return the persisted entity.
     */
    Condition update(Condition condition);

    /**
     * Partially updates a condition.
     *
     * @param condition the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Condition> partialUpdate(Condition condition);

    /**
     * Get all the conditions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Condition> findAll(Pageable pageable);

    /**
     * Get the "id" condition.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Condition> findOne(Long id);

    /**
     * Delete the "id" condition.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
