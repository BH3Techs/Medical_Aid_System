package com.medical_aid_system.web.rest;

import com.medical_aid_system.domain.SponsorAdministration;
import com.medical_aid_system.repository.SponsorAdministrationRepository;
import com.medical_aid_system.service.SponsorAdministrationService;
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
 * REST controller for managing {@link com.medical_aid_system.domain.SponsorAdministration}.
 */
@RestController
@RequestMapping("/api")
public class SponsorAdministrationResource {

    private final Logger log = LoggerFactory.getLogger(SponsorAdministrationResource.class);

    private static final String ENTITY_NAME = "sponsorAdministration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SponsorAdministrationService sponsorAdministrationService;

    private final SponsorAdministrationRepository sponsorAdministrationRepository;

    public SponsorAdministrationResource(
        SponsorAdministrationService sponsorAdministrationService,
        SponsorAdministrationRepository sponsorAdministrationRepository
    ) {
        this.sponsorAdministrationService = sponsorAdministrationService;
        this.sponsorAdministrationRepository = sponsorAdministrationRepository;
    }

    /**
     * {@code POST  /sponsor-administrations} : Create a new sponsorAdministration.
     *
     * @param sponsorAdministration the sponsorAdministration to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sponsorAdministration, or with status {@code 400 (Bad Request)} if the sponsorAdministration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sponsor-administrations")
    public ResponseEntity<SponsorAdministration> createSponsorAdministration(
        @Valid @RequestBody SponsorAdministration sponsorAdministration
    ) throws URISyntaxException {
        log.debug("REST request to save SponsorAdministration : {}", sponsorAdministration);
        if (sponsorAdministration.getId() != null) {
            throw new BadRequestAlertException("A new sponsorAdministration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SponsorAdministration result = sponsorAdministrationService.save(sponsorAdministration);
        return ResponseEntity
            .created(new URI("/api/sponsor-administrations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sponsor-administrations/:id} : Updates an existing sponsorAdministration.
     *
     * @param id the id of the sponsorAdministration to save.
     * @param sponsorAdministration the sponsorAdministration to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sponsorAdministration,
     * or with status {@code 400 (Bad Request)} if the sponsorAdministration is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sponsorAdministration couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sponsor-administrations/{id}")
    public ResponseEntity<SponsorAdministration> updateSponsorAdministration(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SponsorAdministration sponsorAdministration
    ) throws URISyntaxException {
        log.debug("REST request to update SponsorAdministration : {}, {}", id, sponsorAdministration);
        if (sponsorAdministration.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sponsorAdministration.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sponsorAdministrationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SponsorAdministration result = sponsorAdministrationService.update(sponsorAdministration);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sponsorAdministration.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sponsor-administrations/:id} : Partial updates given fields of an existing sponsorAdministration, field will ignore if it is null
     *
     * @param id the id of the sponsorAdministration to save.
     * @param sponsorAdministration the sponsorAdministration to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sponsorAdministration,
     * or with status {@code 400 (Bad Request)} if the sponsorAdministration is not valid,
     * or with status {@code 404 (Not Found)} if the sponsorAdministration is not found,
     * or with status {@code 500 (Internal Server Error)} if the sponsorAdministration couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sponsor-administrations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SponsorAdministration> partialUpdateSponsorAdministration(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SponsorAdministration sponsorAdministration
    ) throws URISyntaxException {
        log.debug("REST request to partial update SponsorAdministration partially : {}, {}", id, sponsorAdministration);
        if (sponsorAdministration.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sponsorAdministration.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sponsorAdministrationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SponsorAdministration> result = sponsorAdministrationService.partialUpdate(sponsorAdministration);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sponsorAdministration.getId().toString())
        );
    }

    /**
     * {@code GET  /sponsor-administrations} : get all the sponsorAdministrations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sponsorAdministrations in body.
     */
    @GetMapping("/sponsor-administrations")
    public ResponseEntity<List<SponsorAdministration>> getAllSponsorAdministrations(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of SponsorAdministrations");
        Page<SponsorAdministration> page = sponsorAdministrationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sponsor-administrations/:id} : get the "id" sponsorAdministration.
     *
     * @param id the id of the sponsorAdministration to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sponsorAdministration, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sponsor-administrations/{id}")
    public ResponseEntity<SponsorAdministration> getSponsorAdministration(@PathVariable Long id) {
        log.debug("REST request to get SponsorAdministration : {}", id);
        Optional<SponsorAdministration> sponsorAdministration = sponsorAdministrationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sponsorAdministration);
    }

    /**
     * {@code DELETE  /sponsor-administrations/:id} : delete the "id" sponsorAdministration.
     *
     * @param id the id of the sponsorAdministration to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sponsor-administrations/{id}")
    public ResponseEntity<Void> deleteSponsorAdministration(@PathVariable Long id) {
        log.debug("REST request to delete SponsorAdministration : {}", id);
        sponsorAdministrationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
