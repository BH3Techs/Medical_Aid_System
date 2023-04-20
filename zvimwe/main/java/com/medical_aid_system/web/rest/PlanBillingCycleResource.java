package com.medical_aid_system.web.rest;

import com.medical_aid_system.domain.PlanBillingCycle;
import com.medical_aid_system.repository.PlanBillingCycleRepository;
import com.medical_aid_system.service.PlanBillingCycleService;
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
 * REST controller for managing {@link com.medical_aid_system.domain.PlanBillingCycle}.
 */
@RestController
@RequestMapping("/api")
public class PlanBillingCycleResource {

    private final Logger log = LoggerFactory.getLogger(PlanBillingCycleResource.class);

    private static final String ENTITY_NAME = "planBillingCycle";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlanBillingCycleService planBillingCycleService;

    private final PlanBillingCycleRepository planBillingCycleRepository;

    public PlanBillingCycleResource(
        PlanBillingCycleService planBillingCycleService,
        PlanBillingCycleRepository planBillingCycleRepository
    ) {
        this.planBillingCycleService = planBillingCycleService;
        this.planBillingCycleRepository = planBillingCycleRepository;
    }

    /**
     * {@code POST  /plan-billing-cycles} : Create a new planBillingCycle.
     *
     * @param planBillingCycle the planBillingCycle to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new planBillingCycle, or with status {@code 400 (Bad Request)} if the planBillingCycle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plan-billing-cycles")
    public ResponseEntity<PlanBillingCycle> createPlanBillingCycle(@Valid @RequestBody PlanBillingCycle planBillingCycle)
        throws URISyntaxException {
        log.debug("REST request to save PlanBillingCycle : {}", planBillingCycle);
        if (planBillingCycle.getId() != null) {
            throw new BadRequestAlertException("A new planBillingCycle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlanBillingCycle result = planBillingCycleService.save(planBillingCycle);
        return ResponseEntity
            .created(new URI("/api/plan-billing-cycles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plan-billing-cycles/:id} : Updates an existing planBillingCycle.
     *
     * @param id the id of the planBillingCycle to save.
     * @param planBillingCycle the planBillingCycle to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planBillingCycle,
     * or with status {@code 400 (Bad Request)} if the planBillingCycle is not valid,
     * or with status {@code 500 (Internal Server Error)} if the planBillingCycle couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plan-billing-cycles/{id}")
    public ResponseEntity<PlanBillingCycle> updatePlanBillingCycle(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PlanBillingCycle planBillingCycle
    ) throws URISyntaxException {
        log.debug("REST request to update PlanBillingCycle : {}, {}", id, planBillingCycle);
        if (planBillingCycle.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, planBillingCycle.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!planBillingCycleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PlanBillingCycle result = planBillingCycleService.update(planBillingCycle);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planBillingCycle.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /plan-billing-cycles/:id} : Partial updates given fields of an existing planBillingCycle, field will ignore if it is null
     *
     * @param id the id of the planBillingCycle to save.
     * @param planBillingCycle the planBillingCycle to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planBillingCycle,
     * or with status {@code 400 (Bad Request)} if the planBillingCycle is not valid,
     * or with status {@code 404 (Not Found)} if the planBillingCycle is not found,
     * or with status {@code 500 (Internal Server Error)} if the planBillingCycle couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/plan-billing-cycles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlanBillingCycle> partialUpdatePlanBillingCycle(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PlanBillingCycle planBillingCycle
    ) throws URISyntaxException {
        log.debug("REST request to partial update PlanBillingCycle partially : {}, {}", id, planBillingCycle);
        if (planBillingCycle.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, planBillingCycle.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!planBillingCycleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PlanBillingCycle> result = planBillingCycleService.partialUpdate(planBillingCycle);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planBillingCycle.getId().toString())
        );
    }

    /**
     * {@code GET  /plan-billing-cycles} : get all the planBillingCycles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of planBillingCycles in body.
     */
    @GetMapping("/plan-billing-cycles")
    public ResponseEntity<List<PlanBillingCycle>> getAllPlanBillingCycles(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of PlanBillingCycles");
        Page<PlanBillingCycle> page = planBillingCycleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /plan-billing-cycles/:id} : get the "id" planBillingCycle.
     *
     * @param id the id of the planBillingCycle to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the planBillingCycle, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plan-billing-cycles/{id}")
    public ResponseEntity<PlanBillingCycle> getPlanBillingCycle(@PathVariable Long id) {
        log.debug("REST request to get PlanBillingCycle : {}", id);
        Optional<PlanBillingCycle> planBillingCycle = planBillingCycleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(planBillingCycle);
    }

    /**
     * {@code DELETE  /plan-billing-cycles/:id} : delete the "id" planBillingCycle.
     *
     * @param id the id of the planBillingCycle to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plan-billing-cycles/{id}")
    public ResponseEntity<Void> deletePlanBillingCycle(@PathVariable Long id) {
        log.debug("REST request to delete PlanBillingCycle : {}", id);
        planBillingCycleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
