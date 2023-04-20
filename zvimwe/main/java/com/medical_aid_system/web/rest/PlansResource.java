package com.medical_aid_system.web.rest;

import com.medical_aid_system.domain.Plans;
import com.medical_aid_system.repository.PlansRepository;
import com.medical_aid_system.service.PlansService;
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
 * REST controller for managing {@link com.medical_aid_system.domain.Plans}.
 */
@RestController
@RequestMapping("/api")
public class PlansResource {

    private final Logger log = LoggerFactory.getLogger(PlansResource.class);

    private static final String ENTITY_NAME = "plans";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlansService plansService;

    private final PlansRepository plansRepository;

    public PlansResource(PlansService plansService, PlansRepository plansRepository) {
        this.plansService = plansService;
        this.plansRepository = plansRepository;
    }

    /**
     * {@code POST  /plans} : Create a new plans.
     *
     * @param plans the plans to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new plans, or with status {@code 400 (Bad Request)} if the plans has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plans")
    public ResponseEntity<Plans> createPlans(@Valid @RequestBody Plans plans) throws URISyntaxException {
        log.debug("REST request to save Plans : {}", plans);
        if (plans.getId() != null) {
            throw new BadRequestAlertException("A new plans cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Plans result = plansService.save(plans);
        return ResponseEntity
            .created(new URI("/api/plans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plans/:id} : Updates an existing plans.
     *
     * @param id the id of the plans to save.
     * @param plans the plans to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plans,
     * or with status {@code 400 (Bad Request)} if the plans is not valid,
     * or with status {@code 500 (Internal Server Error)} if the plans couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plans/{id}")
    public ResponseEntity<Plans> updatePlans(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Plans plans)
        throws URISyntaxException {
        log.debug("REST request to update Plans : {}, {}", id, plans);
        if (plans.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plans.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plansRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Plans result = plansService.update(plans);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plans.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /plans/:id} : Partial updates given fields of an existing plans, field will ignore if it is null
     *
     * @param id the id of the plans to save.
     * @param plans the plans to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plans,
     * or with status {@code 400 (Bad Request)} if the plans is not valid,
     * or with status {@code 404 (Not Found)} if the plans is not found,
     * or with status {@code 500 (Internal Server Error)} if the plans couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/plans/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Plans> partialUpdatePlans(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Plans plans
    ) throws URISyntaxException {
        log.debug("REST request to partial update Plans partially : {}, {}", id, plans);
        if (plans.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plans.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plansRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Plans> result = plansService.partialUpdate(plans);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plans.getId().toString())
        );
    }

    /**
     * {@code GET  /plans} : get all the plans.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plans in body.
     */
    @GetMapping("/plans")
    public ResponseEntity<List<Plans>> getAllPlans(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Plans");
        Page<Plans> page;
        if (eagerload) {
            page = plansService.findAllWithEagerRelationships(pageable);
        } else {
            page = plansService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /plans/:id} : get the "id" plans.
     *
     * @param id the id of the plans to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the plans, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plans/{id}")
    public ResponseEntity<Plans> getPlans(@PathVariable Long id) {
        log.debug("REST request to get Plans : {}", id);
        Optional<Plans> plans = plansService.findOne(id);
        return ResponseUtil.wrapOrNotFound(plans);
    }

    /**
     * {@code DELETE  /plans/:id} : delete the "id" plans.
     *
     * @param id the id of the plans to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plans/{id}")
    public ResponseEntity<Void> deletePlans(@PathVariable Long id) {
        log.debug("REST request to delete Plans : {}", id);
        plansService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
