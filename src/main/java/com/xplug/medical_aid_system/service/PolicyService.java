package com.xplug.medical_aid_system.service;

import com.xplug.medical_aid_system.domain.Policy;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Policy}.
 */
public interface PolicyService {
    /**
     * Save a policy.
     *
     * @param policy the entity to save.
     * @return the persisted entity.
     */
    Policy save(Policy policy);

    /**
     * Updates a policy.
     *
     * @param policy the entity to update.
     * @return the persisted entity.
     */
    Policy update(Policy policy);

    /**
     * Partially updates a policy.
     *
     * @param policy the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Policy> partialUpdate(Policy policy);

    /**
     * Get all the policies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Policy> findAll(Pageable pageable);

    /**
     * Get the "id" policy.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Policy> findOne(Long id);

    /**
     * Delete the "id" policy.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
