package com.medical_aid_system.web.rest;

import com.medical_aid_system.domain.RiskProfile;
import com.medical_aid_system.repository.RiskProfileRepository;
import com.medical_aid_system.service.RiskProfileService;
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
 * REST controller for managing {@link com.medical_aid_system.domain.RiskProfile}.
 */
@RestController
@RequestMapping("/api")
public class RiskProfileResource {

    private final Logger log = LoggerFactory.getLogger(RiskProfileResource.class);

    private static final String ENTITY_NAME = "riskProfile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RiskProfileService riskProfileService;

    private final RiskProfileRepository riskProfileRepository;

    public RiskProfileResource(RiskProfileService riskProfileService, RiskProfileRepository riskProfileRepository) {
        this.riskProfileService = riskProfileService;
        this.riskProfileRepository = riskProfileRepository;
    }

    /**
     * {@code POST  /risk-profiles} : Create a new riskProfile.
     *
     * @param riskProfile the riskProfile to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new riskProfile, or with status {@code 400 (Bad Request)} if the riskProfile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/risk-profiles")
    public ResponseEntity<RiskProfile> createRiskProfile(@Valid @RequestBody RiskProfile riskProfile) throws URISyntaxException {
        log.debug("REST request to save RiskProfile : {}", riskProfile);
        if (riskProfile.getId() != null) {
            throw new BadRequestAlertException("A new riskProfile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RiskProfile result = riskProfileService.save(riskProfile);
        return ResponseEntity
            .created(new URI("/api/risk-profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /risk-profiles/:id} : Updates an existing riskProfile.
     *
     * @param id the id of the riskProfile to save.
     * @param riskProfile the riskProfile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated riskProfile,
     * or with status {@code 400 (Bad Request)} if the riskProfile is not valid,
     * or with status {@code 500 (Internal Server Error)} if the riskProfile couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/risk-profiles/{id}")
    public ResponseEntity<RiskProfile> updateRiskProfile(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RiskProfile riskProfile
    ) throws URISyntaxException {
        log.debug("REST request to update RiskProfile : {}, {}", id, riskProfile);
        if (riskProfile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, riskProfile.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!riskProfileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RiskProfile result = riskProfileService.update(riskProfile);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, riskProfile.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /risk-profiles/:id} : Partial updates given fields of an existing riskProfile, field will ignore if it is null
     *
     * @param id the id of the riskProfile to save.
     * @param riskProfile the riskProfile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated riskProfile,
     * or with status {@code 400 (Bad Request)} if the riskProfile is not valid,
     * or with status {@code 404 (Not Found)} if the riskProfile is not found,
     * or with status {@code 500 (Internal Server Error)} if the riskProfile couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/risk-profiles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RiskProfile> partialUpdateRiskProfile(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RiskProfile riskProfile
    ) throws URISyntaxException {
        log.debug("REST request to partial update RiskProfile partially : {}, {}", id, riskProfile);
        if (riskProfile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, riskProfile.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!riskProfileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RiskProfile> result = riskProfileService.partialUpdate(riskProfile);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, riskProfile.getId().toString())
        );
    }

    /**
     * {@code GET  /risk-profiles} : get all the riskProfiles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of riskProfiles in body.
     */
    @GetMapping("/risk-profiles")
    public ResponseEntity<List<RiskProfile>> getAllRiskProfiles(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of RiskProfiles");
        Page<RiskProfile> page = riskProfileService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /risk-profiles/:id} : get the "id" riskProfile.
     *
     * @param id the id of the riskProfile to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the riskProfile, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/risk-profiles/{id}")
    public ResponseEntity<RiskProfile> getRiskProfile(@PathVariable Long id) {
        log.debug("REST request to get RiskProfile : {}", id);
        Optional<RiskProfile> riskProfile = riskProfileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(riskProfile);
    }

    /**
     * {@code DELETE  /risk-profiles/:id} : delete the "id" riskProfile.
     *
     * @param id the id of the riskProfile to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/risk-profiles/{id}")
    public ResponseEntity<Void> deleteRiskProfile(@PathVariable Long id) {
        log.debug("REST request to delete RiskProfile : {}", id);
        riskProfileService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
