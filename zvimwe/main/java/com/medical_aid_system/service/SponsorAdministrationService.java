package com.medical_aid_system.service;

import com.medical_aid_system.domain.SponsorAdministration;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link SponsorAdministration}.
 */
public interface SponsorAdministrationService {
    /**
     * Save a sponsorAdministration.
     *
     * @param sponsorAdministration the entity to save.
     * @return the persisted entity.
     */
    SponsorAdministration save(SponsorAdministration sponsorAdministration);

    /**
     * Updates a sponsorAdministration.
     *
     * @param sponsorAdministration the entity to update.
     * @return the persisted entity.
     */
    SponsorAdministration update(SponsorAdministration sponsorAdministration);

    /**
     * Partially updates a sponsorAdministration.
     *
     * @param sponsorAdministration the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SponsorAdministration> partialUpdate(SponsorAdministration sponsorAdministration);

    /**
     * Get all the sponsorAdministrations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SponsorAdministration> findAll(Pageable pageable);

    /**
     * Get the "id" sponsorAdministration.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SponsorAdministration> findOne(Long id);

    /**
     * Delete the "id" sponsorAdministration.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
