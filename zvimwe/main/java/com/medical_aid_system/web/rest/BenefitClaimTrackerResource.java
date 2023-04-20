package com.medical_aid_system.web.rest;

import com.medical_aid_system.domain.BenefitClaimTracker;
import com.medical_aid_system.repository.BenefitClaimTrackerRepository;
import com.medical_aid_system.service.BenefitClaimTrackerService;
import com.medical_aid_system.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link com.medical_aid_system.domain.BenefitClaimTracker}.
 */
@RestController
@RequestMapping("/api")
public class BenefitClaimTrackerResource {

    private final Logger log = LoggerFactory.getLogger(BenefitClaimTrackerResource.class);

    private static final String ENTITY_NAME = "benefitClaimTracker";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BenefitClaimTrackerService benefitClaimTrackerService;

    private final BenefitClaimTrackerRepository benefitClaimTrackerRepository;

    public BenefitClaimTrackerResource(
        BenefitClaimTrackerService benefitClaimTrackerService,
        BenefitClaimTrackerRepository benefitClaimTrackerRepository
    ) {
        this.benefitClaimTrackerService = benefitClaimTrackerService;
        this.benefitClaimTrackerRepository = benefitClaimTrackerRepository;
    }

    /**
     * {@code POST  /benefit-claim-trackers} : Create a new benefitClaimTracker.
     *
     * @param benefitClaimTracker the benefitClaimTracker to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new benefitClaimTracker, or with status {@code 400 (Bad Request)} if the benefitClaimTracker has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/benefit-claim-trackers")
    public ResponseEntity<BenefitClaimTracker> createBenefitClaimTracker(@RequestBody BenefitClaimTracker benefitClaimTracker)
        throws URISyntaxException {
        log.debug("REST request to save BenefitClaimTracker : {}", benefitClaimTracker);
        if (benefitClaimTracker.getId() != null) {
            throw new BadRequestAlertException("A new benefitClaimTracker cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BenefitClaimTracker result = benefitClaimTrackerService.save(benefitClaimTracker);
        return ResponseEntity
            .created(new URI("/api/benefit-claim-trackers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /benefit-claim-trackers/:id} : Updates an existing benefitClaimTracker.
     *
     * @param id the id of the benefitClaimTracker to save.
     * @param benefitClaimTracker the benefitClaimTracker to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated benefitClaimTracker,
     * or with status {@code 400 (Bad Request)} if the benefitClaimTracker is not valid,
     * or with status {@code 500 (Internal Server Error)} if the benefitClaimTracker couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/benefit-claim-trackers/{id}")
    public ResponseEntity<BenefitClaimTracker> updateBenefitClaimTracker(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BenefitClaimTracker benefitClaimTracker
    ) throws URISyntaxException {
        log.debug("REST request to update BenefitClaimTracker : {}, {}", id, benefitClaimTracker);
        if (benefitClaimTracker.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, benefitClaimTracker.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!benefitClaimTrackerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BenefitClaimTracker result = benefitClaimTrackerService.update(benefitClaimTracker);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, benefitClaimTracker.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /benefit-claim-trackers/:id} : Partial updates given fields of an existing benefitClaimTracker, field will ignore if it is null
     *
     * @param id the id of the benefitClaimTracker to save.
     * @param benefitClaimTracker the benefitClaimTracker to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated benefitClaimTracker,
     * or with status {@code 400 (Bad Request)} if the benefitClaimTracker is not valid,
     * or with status {@code 404 (Not Found)} if the benefitClaimTracker is not found,
     * or with status {@code 500 (Internal Server Error)} if the benefitClaimTracker couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/benefit-claim-trackers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BenefitClaimTracker> partialUpdateBenefitClaimTracker(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BenefitClaimTracker benefitClaimTracker
    ) throws URISyntaxException {
        log.debug("REST request to partial update BenefitClaimTracker partially : {}, {}", id, benefitClaimTracker);
        if (benefitClaimTracker.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, benefitClaimTracker.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!benefitClaimTrackerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BenefitClaimTracker> result = benefitClaimTrackerService.partialUpdate(benefitClaimTracker);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, benefitClaimTracker.getId().toString())
        );
    }

    /**
     * {@code GET  /benefit-claim-trackers} : get all the benefitClaimTrackers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of benefitClaimTrackers in body.
     */
    @GetMapping("/benefit-claim-trackers")
    public ResponseEntity<List<BenefitClaimTracker>> getAllBenefitClaimTrackers(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of BenefitClaimTrackers");
        Page<BenefitClaimTracker> page = benefitClaimTrackerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /benefit-claim-trackers/:id} : get the "id" benefitClaimTracker.
     *
     * @param id the id of the benefitClaimTracker to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the benefitClaimTracker, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/benefit-claim-trackers/{id}")
    public ResponseEntity<BenefitClaimTracker> getBenefitClaimTracker(@PathVariable Long id) {
        log.debug("REST request to get BenefitClaimTracker : {}", id);
        Optional<BenefitClaimTracker> benefitClaimTracker = benefitClaimTrackerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(benefitClaimTracker);
    }

    /**
     * {@code DELETE  /benefit-claim-trackers/:id} : delete the "id" benefitClaimTracker.
     *
     * @param id the id of the benefitClaimTracker to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/benefit-claim-trackers/{id}")
    public ResponseEntity<Void> deleteBenefitClaimTracker(@PathVariable Long id) {
        log.debug("REST request to delete BenefitClaimTracker : {}", id);
        benefitClaimTrackerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
