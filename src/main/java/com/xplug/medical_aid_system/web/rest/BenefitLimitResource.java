package com.xplug.medical_aid_system.web.rest;

import com.xplug.medical_aid_system.domain.BenefitLimit;
import com.xplug.medical_aid_system.repository.BenefitLimitRepository;
import com.xplug.medical_aid_system.service.BenefitLimitService;
import com.xplug.medical_aid_system.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.xplug.medical_aid_system.domain.BenefitLimit}.
 */
@RestController
@RequestMapping("/api")
public class BenefitLimitResource {

    private final Logger log = LoggerFactory.getLogger(BenefitLimitResource.class);

    private static final String ENTITY_NAME = "benefitLimit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BenefitLimitService benefitLimitService;

    private final BenefitLimitRepository benefitLimitRepository;

    public BenefitLimitResource(BenefitLimitService benefitLimitService, BenefitLimitRepository benefitLimitRepository) {
        this.benefitLimitService = benefitLimitService;
        this.benefitLimitRepository = benefitLimitRepository;
    }

    /**
     * {@code POST  /benefit-limits} : Create a new benefitLimit.
     *
     * @param benefitLimit the benefitLimit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new benefitLimit, or with status {@code 400 (Bad Request)} if the benefitLimit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/benefit-limits")
    public ResponseEntity<BenefitLimit> createBenefitLimit(@Valid @RequestBody BenefitLimit benefitLimit) throws URISyntaxException {
        log.debug("REST request to save BenefitLimit : {}", benefitLimit);
        if (benefitLimit.getId() != null) {
            throw new BadRequestAlertException("A new benefitLimit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BenefitLimit result = benefitLimitService.save(benefitLimit);
        return ResponseEntity
            .created(new URI("/api/benefit-limits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /benefit-limits/:id} : Updates an existing benefitLimit.
     *
     * @param id the id of the benefitLimit to save.
     * @param benefitLimit the benefitLimit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated benefitLimit,
     * or with status {@code 400 (Bad Request)} if the benefitLimit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the benefitLimit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/benefit-limits/{id}")
    public ResponseEntity<BenefitLimit> updateBenefitLimit(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BenefitLimit benefitLimit
    ) throws URISyntaxException {
        log.debug("REST request to update BenefitLimit : {}, {}", id, benefitLimit);
        if (benefitLimit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, benefitLimit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!benefitLimitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BenefitLimit result = benefitLimitService.update(benefitLimit);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, benefitLimit.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /benefit-limits/:id} : Partial updates given fields of an existing benefitLimit, field will ignore if it is null
     *
     * @param id the id of the benefitLimit to save.
     * @param benefitLimit the benefitLimit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated benefitLimit,
     * or with status {@code 400 (Bad Request)} if the benefitLimit is not valid,
     * or with status {@code 404 (Not Found)} if the benefitLimit is not found,
     * or with status {@code 500 (Internal Server Error)} if the benefitLimit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/benefit-limits/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BenefitLimit> partialUpdateBenefitLimit(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BenefitLimit benefitLimit
    ) throws URISyntaxException {
        log.debug("REST request to partial update BenefitLimit partially : {}, {}", id, benefitLimit);
        if (benefitLimit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, benefitLimit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!benefitLimitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BenefitLimit> result = benefitLimitService.partialUpdate(benefitLimit);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, benefitLimit.getId().toString())
        );
    }

    /**
     * {@code GET  /benefit-limits} : get all the benefitLimits.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of benefitLimits in body.
     */
    @GetMapping("/benefit-limits")
    public ResponseEntity<List<BenefitLimit>> getAllBenefitLimits(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of BenefitLimits");
        Page<BenefitLimit> page = benefitLimitService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /benefit-limits/:id} : get the "id" benefitLimit.
     *
     * @param id the id of the benefitLimit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the benefitLimit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/benefit-limits/{id}")
    public ResponseEntity<BenefitLimit> getBenefitLimit(@PathVariable Long id) {
        log.debug("REST request to get BenefitLimit : {}", id);
        Optional<BenefitLimit> benefitLimit = benefitLimitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(benefitLimit);
    }

    /**
     * {@code DELETE  /benefit-limits/:id} : delete the "id" benefitLimit.
     *
     * @param id the id of the benefitLimit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/benefit-limits/{id}")
    public ResponseEntity<Void> deleteBenefitLimit(@PathVariable Long id) {
        log.debug("REST request to delete BenefitLimit : {}", id);
        benefitLimitService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
