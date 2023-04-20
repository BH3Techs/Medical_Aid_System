package com.medical_aid_system.service;

import com.medical_aid_system.domain.Claim;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Claim}.
 */
public interface ClaimService {
    /**
     * Save a claim.
     *
     * @param claim the entity to save.
     * @return the persisted entity.
     */
    Claim save(Claim claim);

    /**
     * Updates a claim.
     *
     * @param claim the entity to update.
     * @return the persisted entity.
     */
    Claim update(Claim claim);

    /**
     * Partially updates a claim.
     *
     * @param claim the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Claim> partialUpdate(Claim claim);

    /**
     * Get all the claims.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Claim> findAll(Pageable pageable);

    /**
     * Get the "id" claim.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Claim> findOne(Long id);

    /**
     * Delete the "id" claim.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
