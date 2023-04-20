package com.medical_aid_system.service;

import com.medical_aid_system.domain.Plans;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Plans}.
 */
public interface PlansService {
    /**
     * Save a plans.
     *
     * @param plans the entity to save.
     * @return the persisted entity.
     */
    Plans save(Plans plans);

    /**
     * Updates a plans.
     *
     * @param plans the entity to update.
     * @return the persisted entity.
     */
    Plans update(Plans plans);

    /**
     * Partially updates a plans.
     *
     * @param plans the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Plans> partialUpdate(Plans plans);

    /**
     * Get all the plans.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Plans> findAll(Pageable pageable);

    /**
     * Get all the plans with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Plans> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" plans.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Plans> findOne(Long id);

    /**
     * Delete the "id" plans.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
