package com.xplug.medical_aid_system.service;

import com.xplug.medical_aid_system.domain.TarrifClaim;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link TarrifClaim}.
 */
public interface TarrifClaimService {
    /**
     * Save a tarrifClaim.
     *
     * @param tarrifClaim the entity to save.
     * @return the persisted entity.
     */
    TarrifClaim save(TarrifClaim tarrifClaim);

    /**
     * Updates a tarrifClaim.
     *
     * @param tarrifClaim the entity to update.
     * @return the persisted entity.
     */
    TarrifClaim update(TarrifClaim tarrifClaim);

    /**
     * Partially updates a tarrifClaim.
     *
     * @param tarrifClaim the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TarrifClaim> partialUpdate(TarrifClaim tarrifClaim);

    /**
     * Get all the tarrifClaims.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TarrifClaim> findAll(Pageable pageable);

    /**
     * Get the "id" tarrifClaim.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TarrifClaim> findOne(Long id);

    /**
     * Delete the "id" tarrifClaim.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
