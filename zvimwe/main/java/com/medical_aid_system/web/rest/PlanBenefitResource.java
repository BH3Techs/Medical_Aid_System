package com.medical_aid_system.web.rest;

import com.medical_aid_system.domain.PlanBenefit;
import com.medical_aid_system.repository.PlanBenefitRepository;
import com.medical_aid_system.service.PlanBenefitService;
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
 * REST controller for managing {@link com.medical_aid_system.domain.PlanBenefit}.
 */
@RestController
@RequestMapping("/api")
public class PlanBenefitResource {

    private final Logger log = LoggerFactory.getLogger(PlanBenefitResource.class);

    private static final String ENTITY_NAME = "planBenefit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlanBenefitService planBenefitService;

    private final PlanBenefitRepository planBenefitRepository;

    public PlanBenefitResource(PlanBenefitService planBenefitService, PlanBenefitRepository planBenefitRepository) {
        this.planBenefitService = planBenefitService;
        this.planBenefitRepository = planBenefitRepository;
    }

    /**
     * {@code POST  /plan-benefits} : Create a new planBenefit.
     *
     * @param planBenefit the planBenefit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new planBenefit, or with status {@code 400 (Bad Request)} if the planBenefit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plan-benefits")
    public ResponseEntity<PlanBenefit> createPlanBenefit(@Valid @RequestBody PlanBenefit planBenefit) throws URISyntaxException {
        log.debug("REST request to save PlanBenefit : {}", planBenefit);
        if (planBenefit.getId() != null) {
            throw new BadRequestAlertException("A new planBenefit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlanBenefit result = planBenefitService.save(planBenefit);
        return ResponseEntity
            .created(new URI("/api/plan-benefits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plan-benefits/:id} : Updates an existing planBenefit.
     *
     * @param id the id of the planBenefit to save.
     * @param planBenefit the planBenefit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planBenefit,
     * or with status {@code 400 (Bad Request)} if the planBenefit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the planBenefit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plan-benefits/{id}")
    public ResponseEntity<PlanBenefit> updatePlanBenefit(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PlanBenefit planBenefit
    ) throws URISyntaxException {
        log.debug("REST request to update PlanBenefit : {}, {}", id, planBenefit);
        if (planBenefit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, planBenefit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!planBenefitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PlanBenefit result = planBenefitService.update(planBenefit);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planBenefit.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /plan-benefits/:id} : Partial updates given fields of an existing planBenefit, field will ignore if it is null
     *
     * @param id the id of the planBenefit to save.
     * @param planBenefit the planBenefit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planBenefit,
     * or with status {@code 400 (Bad Request)} if the planBenefit is not valid,
     * or with status {@code 404 (Not Found)} if the planBenefit is not found,
     * or with status {@code 500 (Internal Server Error)} if the planBenefit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/plan-benefits/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlanBenefit> partialUpdatePlanBenefit(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PlanBenefit planBenefit
    ) throws URISyntaxException {
        log.debug("REST request to partial update PlanBenefit partially : {}, {}", id, planBenefit);
        if (planBenefit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, planBenefit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!planBenefitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PlanBenefit> result = planBenefitService.partialUpdate(planBenefit);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planBenefit.getId().toString())
        );
    }

    /**
     * {@code GET  /plan-benefits} : get all the planBenefits.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of planBenefits in body.
     */
    @GetMapping("/plan-benefits")
    public ResponseEntity<List<PlanBenefit>> getAllPlanBenefits(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of PlanBenefits");
        Page<PlanBenefit> page = planBenefitService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /plan-benefits/:id} : get the "id" planBenefit.
     *
     * @param id the id of the planBenefit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the planBenefit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plan-benefits/{id}")
    public ResponseEntity<PlanBenefit> getPlanBenefit(@PathVariable Long id) {
        log.debug("REST request to get PlanBenefit : {}", id);
        Optional<PlanBenefit> planBenefit = planBenefitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(planBenefit);
    }

    /**
     * {@code DELETE  /plan-benefits/:id} : delete the "id" planBenefit.
     *
     * @param id the id of the planBenefit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plan-benefits/{id}")
    public ResponseEntity<Void> deletePlanBenefit(@PathVariable Long id) {
        log.debug("REST request to delete PlanBenefit : {}", id);
        planBenefitService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
