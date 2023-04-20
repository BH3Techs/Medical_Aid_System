package com.xplug.medical_aid_system.web.rest;

import com.xplug.medical_aid_system.domain.PlanCategory;
import com.xplug.medical_aid_system.repository.PlanCategoryRepository;
import com.xplug.medical_aid_system.service.PlanCategoryService;
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
 * REST controller for managing {@link com.xplug.medical_aid_system.domain.PlanCategory}.
 */
@RestController
@RequestMapping("/api")
public class PlanCategoryResource {

    private final Logger log = LoggerFactory.getLogger(PlanCategoryResource.class);

    private static final String ENTITY_NAME = "planCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlanCategoryService planCategoryService;

    private final PlanCategoryRepository planCategoryRepository;

    public PlanCategoryResource(PlanCategoryService planCategoryService, PlanCategoryRepository planCategoryRepository) {
        this.planCategoryService = planCategoryService;
        this.planCategoryRepository = planCategoryRepository;
    }

    /**
     * {@code POST  /plan-categories} : Create a new planCategory.
     *
     * @param planCategory the planCategory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new planCategory, or with status {@code 400 (Bad Request)} if the planCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plan-categories")
    public ResponseEntity<PlanCategory> createPlanCategory(@Valid @RequestBody PlanCategory planCategory) throws URISyntaxException {
        log.debug("REST request to save PlanCategory : {}", planCategory);
        if (planCategory.getId() != null) {
            throw new BadRequestAlertException("A new planCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlanCategory result = planCategoryService.save(planCategory);
        return ResponseEntity
            .created(new URI("/api/plan-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plan-categories/:id} : Updates an existing planCategory.
     *
     * @param id the id of the planCategory to save.
     * @param planCategory the planCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planCategory,
     * or with status {@code 400 (Bad Request)} if the planCategory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the planCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plan-categories/{id}")
    public ResponseEntity<PlanCategory> updatePlanCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PlanCategory planCategory
    ) throws URISyntaxException {
        log.debug("REST request to update PlanCategory : {}, {}", id, planCategory);
        if (planCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, planCategory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!planCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PlanCategory result = planCategoryService.update(planCategory);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planCategory.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /plan-categories/:id} : Partial updates given fields of an existing planCategory, field will ignore if it is null
     *
     * @param id the id of the planCategory to save.
     * @param planCategory the planCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planCategory,
     * or with status {@code 400 (Bad Request)} if the planCategory is not valid,
     * or with status {@code 404 (Not Found)} if the planCategory is not found,
     * or with status {@code 500 (Internal Server Error)} if the planCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/plan-categories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlanCategory> partialUpdatePlanCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PlanCategory planCategory
    ) throws URISyntaxException {
        log.debug("REST request to partial update PlanCategory partially : {}, {}", id, planCategory);
        if (planCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, planCategory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!planCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PlanCategory> result = planCategoryService.partialUpdate(planCategory);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planCategory.getId().toString())
        );
    }

    /**
     * {@code GET  /plan-categories} : get all the planCategories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of planCategories in body.
     */
    @GetMapping("/plan-categories")
    public ResponseEntity<List<PlanCategory>> getAllPlanCategories(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of PlanCategories");
        Page<PlanCategory> page = planCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /plan-categories/:id} : get the "id" planCategory.
     *
     * @param id the id of the planCategory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the planCategory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plan-categories/{id}")
    public ResponseEntity<PlanCategory> getPlanCategory(@PathVariable Long id) {
        log.debug("REST request to get PlanCategory : {}", id);
        Optional<PlanCategory> planCategory = planCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(planCategory);
    }

    /**
     * {@code DELETE  /plan-categories/:id} : delete the "id" planCategory.
     *
     * @param id the id of the planCategory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plan-categories/{id}")
    public ResponseEntity<Void> deletePlanCategory(@PathVariable Long id) {
        log.debug("REST request to delete PlanCategory : {}", id);
        planCategoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
