package com.xplug.medical_aid_system.web.rest;

import com.xplug.medical_aid_system.domain.TarrifClaim;
import com.xplug.medical_aid_system.repository.TarrifClaimRepository;
import com.xplug.medical_aid_system.service.TarrifClaimService;
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
 * REST controller for managing {@link com.xplug.medical_aid_system.domain.TarrifClaim}.
 */
@RestController
@RequestMapping("/api")
public class TarrifClaimResource {

    private final Logger log = LoggerFactory.getLogger(TarrifClaimResource.class);

    private static final String ENTITY_NAME = "tarrifClaim";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TarrifClaimService tarrifClaimService;

    private final TarrifClaimRepository tarrifClaimRepository;

    public TarrifClaimResource(TarrifClaimService tarrifClaimService, TarrifClaimRepository tarrifClaimRepository) {
        this.tarrifClaimService = tarrifClaimService;
        this.tarrifClaimRepository = tarrifClaimRepository;
    }

    /**
     * {@code POST  /tarrif-claims} : Create a new tarrifClaim.
     *
     * @param tarrifClaim the tarrifClaim to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tarrifClaim, or with status {@code 400 (Bad Request)} if the tarrifClaim has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tarrif-claims")
    public ResponseEntity<TarrifClaim> createTarrifClaim(@Valid @RequestBody TarrifClaim tarrifClaim) throws URISyntaxException {
        log.debug("REST request to save TarrifClaim : {}", tarrifClaim);
        if (tarrifClaim.getId() != null) {
            throw new BadRequestAlertException("A new tarrifClaim cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TarrifClaim result = tarrifClaimService.save(tarrifClaim);
        return ResponseEntity
            .created(new URI("/api/tarrif-claims/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tarrif-claims/:id} : Updates an existing tarrifClaim.
     *
     * @param id the id of the tarrifClaim to save.
     * @param tarrifClaim the tarrifClaim to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tarrifClaim,
     * or with status {@code 400 (Bad Request)} if the tarrifClaim is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tarrifClaim couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tarrif-claims/{id}")
    public ResponseEntity<TarrifClaim> updateTarrifClaim(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TarrifClaim tarrifClaim
    ) throws URISyntaxException {
        log.debug("REST request to update TarrifClaim : {}, {}", id, tarrifClaim);
        if (tarrifClaim.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tarrifClaim.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tarrifClaimRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TarrifClaim result = tarrifClaimService.update(tarrifClaim);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tarrifClaim.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tarrif-claims/:id} : Partial updates given fields of an existing tarrifClaim, field will ignore if it is null
     *
     * @param id the id of the tarrifClaim to save.
     * @param tarrifClaim the tarrifClaim to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tarrifClaim,
     * or with status {@code 400 (Bad Request)} if the tarrifClaim is not valid,
     * or with status {@code 404 (Not Found)} if the tarrifClaim is not found,
     * or with status {@code 500 (Internal Server Error)} if the tarrifClaim couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tarrif-claims/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TarrifClaim> partialUpdateTarrifClaim(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TarrifClaim tarrifClaim
    ) throws URISyntaxException {
        log.debug("REST request to partial update TarrifClaim partially : {}, {}", id, tarrifClaim);
        if (tarrifClaim.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tarrifClaim.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tarrifClaimRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TarrifClaim> result = tarrifClaimService.partialUpdate(tarrifClaim);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tarrifClaim.getId().toString())
        );
    }

    /**
     * {@code GET  /tarrif-claims} : get all the tarrifClaims.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tarrifClaims in body.
     */
    @GetMapping("/tarrif-claims")
    public ResponseEntity<List<TarrifClaim>> getAllTarrifClaims(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TarrifClaims");
        Page<TarrifClaim> page = tarrifClaimService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tarrif-claims/:id} : get the "id" tarrifClaim.
     *
     * @param id the id of the tarrifClaim to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tarrifClaim, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tarrif-claims/{id}")
    public ResponseEntity<TarrifClaim> getTarrifClaim(@PathVariable Long id) {
        log.debug("REST request to get TarrifClaim : {}", id);
        Optional<TarrifClaim> tarrifClaim = tarrifClaimService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tarrifClaim);
    }

    /**
     * {@code DELETE  /tarrif-claims/:id} : delete the "id" tarrifClaim.
     *
     * @param id the id of the tarrifClaim to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tarrif-claims/{id}")
    public ResponseEntity<Void> deleteTarrifClaim(@PathVariable Long id) {
        log.debug("REST request to delete TarrifClaim : {}", id);
        tarrifClaimService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
