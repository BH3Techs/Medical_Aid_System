package com.xplug.medical_aid_system.service;

import com.xplug.medical_aid_system.domain.ContactDetails;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ContactDetails}.
 */
public interface ContactDetailsService {
    /**
     * Save a contactDetails.
     *
     * @param contactDetails the entity to save.
     * @return the persisted entity.
     */
    ContactDetails save(ContactDetails contactDetails);

    /**
     * Updates a contactDetails.
     *
     * @param contactDetails the entity to update.
     * @return the persisted entity.
     */
    ContactDetails update(ContactDetails contactDetails);

    /**
     * Partially updates a contactDetails.
     *
     * @param contactDetails the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ContactDetails> partialUpdate(ContactDetails contactDetails);

    /**
     * Get all the contactDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ContactDetails> findAll(Pageable pageable);

    /**
     * Get the "id" contactDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ContactDetails> findOne(Long id);

    /**
     * Delete the "id" contactDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
