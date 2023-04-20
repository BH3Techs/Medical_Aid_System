package com.medical_aid_system.web.rest;

import com.medical_aid_system.domain.ContactDetails;
import com.medical_aid_system.repository.ContactDetailsRepository;
import com.medical_aid_system.service.ContactDetailsService;
import com.medical_aid_system.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.medical_aid_system.domain.ContactDetails}.
 */
@RestController
@RequestMapping("/api")
public class ContactDetailsResource {

    private final Logger log = LoggerFactory.getLogger(ContactDetailsResource.class);

    private static final String ENTITY_NAME = "contactDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContactDetailsService contactDetailsService;

    private final ContactDetailsRepository contactDetailsRepository;

    public ContactDetailsResource(ContactDetailsService contactDetailsService, ContactDetailsRepository contactDetailsRepository) {
        this.contactDetailsService = contactDetailsService;
        this.contactDetailsRepository = contactDetailsRepository;
    }

    /**
     * {@code POST  /contact-details} : Create a new contactDetails.
     *
     * @param contactDetails the contactDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contactDetails, or with status {@code 400 (Bad Request)} if the contactDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contact-details")
    public ResponseEntity<ContactDetails> createContactDetails(@Valid @RequestBody ContactDetails contactDetails)
        throws URISyntaxException {
        log.debug("REST request to save ContactDetails : {}", contactDetails);
        if (contactDetails.getId() != null) {
            throw new BadRequestAlertException("A new contactDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContactDetails result = contactDetailsService.save(contactDetails);
        return ResponseEntity
            .created(new URI("/api/contact-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contact-details/:id} : Updates an existing contactDetails.
     *
     * @param id the id of the contactDetails to save.
     * @param contactDetails the contactDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactDetails,
     * or with status {@code 400 (Bad Request)} if the contactDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contactDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contact-details/{id}")
    public ResponseEntity<ContactDetails> updateContactDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ContactDetails contactDetails
    ) throws URISyntaxException {
        log.debug("REST request to update ContactDetails : {}, {}", id, contactDetails);
        if (contactDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contactDetails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contactDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ContactDetails result = contactDetailsService.update(contactDetails);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contactDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /contact-details/:id} : Partial updates given fields of an existing contactDetails, field will ignore if it is null
     *
     * @param id the id of the contactDetails to save.
     * @param contactDetails the contactDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactDetails,
     * or with status {@code 400 (Bad Request)} if the contactDetails is not valid,
     * or with status {@code 404 (Not Found)} if the contactDetails is not found,
     * or with status {@code 500 (Internal Server Error)} if the contactDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/contact-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ContactDetails> partialUpdateContactDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ContactDetails contactDetails
    ) throws URISyntaxException {
        log.debug("REST request to partial update ContactDetails partially : {}, {}", id, contactDetails);
        if (contactDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contactDetails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contactDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ContactDetails> result = contactDetailsService.partialUpdate(contactDetails);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contactDetails.getId().toString())
        );
    }

    /**
     * {@code GET  /contact-details} : get all the contactDetails.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contactDetails in body.
     */
    @GetMapping("/contact-details")
    public ResponseEntity<List<ContactDetails>> getAllContactDetails(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ContactDetails");
        Page<ContactDetails> page = contactDetailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contact-details/:id} : get the "id" contactDetails.
     *
     * @param id the id of the contactDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contactDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contact-details/{id}")
    public ResponseEntity<ContactDetails> getContactDetails(@PathVariable Long id) {
        log.debug("REST request to get ContactDetails : {}", id);
        Optional<ContactDetails> contactDetails = contactDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contactDetails);
    }

    /**
     * {@code DELETE  /contact-details/:id} : delete the "id" contactDetails.
     *
     * @param id the id of the contactDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contact-details/{id}")
    public ResponseEntity<Void> deleteContactDetails(@PathVariable Long id) {
        log.debug("REST request to delete ContactDetails : {}", id);
        contactDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
