package com.xplug.medical_aid_system.web.rest;

import com.xplug.medical_aid_system.domain.Benefit;
import com.xplug.medical_aid_system.repository.BenefitRepository;
import com.xplug.medical_aid_system.service.BenefitService;
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
 * REST controller for managing {@link com.xplug.medical_aid_system.domain.Benefit}.
 */
@RestController
@RequestMapping("/api")
public class BenefitResource {

    private final Logger log = LoggerFactory.getLogger(BenefitResource.class);

    private static final String ENTITY_NAME = "benefit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BenefitService benefitService;

    private final BenefitRepository benefitRepository;

    public BenefitResource(BenefitService benefitService, BenefitRepository benefitRepository) {
        this.benefitService = benefitService;
        this.benefitRepository = benefitRepository;
    }

    /**
     * {@code POST  /benefits} : Create a new benefit.
     *
     * @param benefit the benefit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new benefit, or with status {@code 400 (Bad Request)} if the benefit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/benefits")
    public ResponseEntity<Benefit> createBenefit(@Valid @RequestBody Benefit benefit) throws URISyntaxException {
        log.debug("REST request to save Benefit : {}", benefit);
        if (benefit.getId() != null) {
            throw new BadRequestAlertException("A new benefit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Benefit result = benefitService.save(benefit);
        return ResponseEntity
            .created(new URI("/api/benefits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /benefits/:id} : Updates an existing benefit.
     *
     * @param id the id of the benefit to save.
     * @param benefit the benefit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated benefit,
     * or with status {@code 400 (Bad Request)} if the benefit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the benefit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/benefits/{id}")
    public ResponseEntity<Benefit> updateBenefit(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Benefit benefit
    ) throws URISyntaxException {
        log.debug("REST request to update Benefit : {}, {}", id, benefit);
        if (benefit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, benefit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!benefitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Benefit result = benefitService.update(benefit);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, benefit.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /benefits/:id} : Partial updates given fields of an existing benefit, field will ignore if it is null
     *
     * @param id the id of the benefit to save.
     * @param benefit the benefit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated benefit,
     * or with status {@code 400 (Bad Request)} if the benefit is not valid,
     * or with status {@code 404 (Not Found)} if the benefit is not found,
     * or with status {@code 500 (Internal Server Error)} if the benefit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/benefits/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Benefit> partialUpdateBenefit(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Benefit benefit
    ) throws URISyntaxException {
        log.debug("REST request to partial update Benefit partially : {}, {}", id, benefit);
        if (benefit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, benefit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!benefitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Benefit> result = benefitService.partialUpdate(benefit);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, benefit.getId().toString())
        );
    }

    /**
     * {@code GET  /benefits} : get all the benefits.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of benefits in body.
     */
    @GetMapping("/benefits")
    public ResponseEntity<List<Benefit>> getAllBenefits(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Benefits");
        Page<Benefit> page = benefitService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /benefits/:id} : get the "id" benefit.
     *
     * @param id the id of the benefit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the benefit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/benefits/{id}")
    public ResponseEntity<Benefit> getBenefit(@PathVariable Long id) {
        log.debug("REST request to get Benefit : {}", id);
        Optional<Benefit> benefit = benefitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(benefit);
    }

    /**
     * {@code DELETE  /benefits/:id} : delete the "id" benefit.
     *
     * @param id the id of the benefit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/benefits/{id}")
    public ResponseEntity<Void> deleteBenefit(@PathVariable Long id) {
        log.debug("REST request to delete Benefit : {}", id);
        benefitService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
