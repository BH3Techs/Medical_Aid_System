package com.medical_aid_system.service;

import com.medical_aid_system.domain.DocumentType;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link DocumentType}.
 */
public interface DocumentTypeService {
    /**
     * Save a documentType.
     *
     * @param documentType the entity to save.
     * @return the persisted entity.
     */
    DocumentType save(DocumentType documentType);

    /**
     * Updates a documentType.
     *
     * @param documentType the entity to update.
     * @return the persisted entity.
     */
    DocumentType update(DocumentType documentType);

    /**
     * Partially updates a documentType.
     *
     * @param documentType the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DocumentType> partialUpdate(DocumentType documentType);

    /**
     * Get all the documentTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DocumentType> findAll(Pageable pageable);

    /**
     * Get the "id" documentType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DocumentType> findOne(Long id);

    /**
     * Delete the "id" documentType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
