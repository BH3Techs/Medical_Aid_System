package com.xplug.medical_aid_system.service.impl;

import com.xplug.medical_aid_system.domain.Document;
import com.xplug.medical_aid_system.repository.DocumentRepository;
import com.xplug.medical_aid_system.service.DocumentService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Document}.
 */
@Service
@Transactional
public class DocumentServiceImpl implements DocumentService {

    private final Logger log = LoggerFactory.getLogger(DocumentServiceImpl.class);

    private final DocumentRepository documentRepository;

    public DocumentServiceImpl(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Override
    public Document save(Document document) {
        log.debug("Request to save Document : {}", document);
        return documentRepository.save(document);
    }

    @Override
    public Document update(Document document) {
        log.debug("Request to update Document : {}", document);
        return documentRepository.save(document);
    }

    @Override
    public Optional<Document> partialUpdate(Document document) {
        log.debug("Request to partially update Document : {}", document);

        return documentRepository
            .findById(document.getId())
            .map(existingDocument -> {
                if (document.getFormat() != null) {
                    existingDocument.setFormat(document.getFormat());
                }
                if (document.getName() != null) {
                    existingDocument.setName(document.getName());
                }
                if (document.getOwnerType() != null) {
                    existingDocument.setOwnerType(document.getOwnerType());
                }
                if (document.getDateCreated() != null) {
                    existingDocument.setDateCreated(document.getDateCreated());
                }
                if (document.getValidity() != null) {
                    existingDocument.setValidity(document.getValidity());
                }

                return existingDocument;
            })
            .map(documentRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Document> findAll(Pageable pageable) {
        log.debug("Request to get all Documents");
        return documentRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Document> findOne(Long id) {
        log.debug("Request to get Document : {}", id);
        return documentRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Document : {}", id);
        documentRepository.deleteById(id);
    }
}
