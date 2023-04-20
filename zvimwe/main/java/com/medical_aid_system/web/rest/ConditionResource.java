package com.medical_aid_system.web.rest;

import com.medical_aid_system.domain.Condition;
import com.medical_aid_system.repository.ConditionRepository;
import com.medical_aid_system.service.ConditionService;
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
 * REST controller for managing {@link com.medical_aid_system.domain.Condition}.
 */
@RestController
@RequestMapping("/api")
public class ConditionResource {

    private final Logger log = LoggerFactory.getLogger(ConditionResource.class);

    private static final String ENTITY_NAME = "condition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConditionService conditionService;

    private final ConditionRepository conditionRepository;

    public ConditionResource(ConditionService conditionService, ConditionRepository conditionRepository) {
        this.conditionService = conditionService;
        this.conditionRepository = conditionRepository;
    }

    /**
     * {@code POST  /conditions} : Create a new condition.
     *
     * @param condition the condition to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new condition, or with status {@code 400 (Bad Request)} if the condition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/conditions")
    public ResponseEntity<Condition> createCondition(@Valid @RequestBody Condition condition) throws URISyntaxException {
        log.debug("REST request to save Condition : {}", condition);
        if (condition.getId() != null) {
            throw new BadRequestAlertException("A new condition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Condition result = conditionService.save(condition);
        return ResponseEntity
            .created(new URI("/api/conditions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /conditions/:id} : Updates an existing condition.
     *
     * @param id the id of the condition to save.
     * @param condition the condition to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated condition,
     * or with status {@code 400 (Bad Request)} if the condition is not valid,
     * or with status {@code 500 (Internal Server Error)} if the condition couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/conditions/{id}")
    public ResponseEntity<Condition> updateCondition(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Condition condition
    ) throws URISyntaxException {
        log.debug("REST request to update Condition : {}, {}", id, condition);
        if (condition.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, condition.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!conditionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Condition result = conditionService.update(condition);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, condition.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /conditions/:id} : Partial updates given fields of an existing condition, field will ignore if it is null
     *
     * @param id the id of the condition to save.
     * @param condition the condition to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated condition,
     * or with status {@code 400 (Bad Request)} if the condition is not valid,
     * or with status {@code 404 (Not Found)} if the condition is not found,
     * or with status {@code 500 (Internal Server Error)} if the condition couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/conditions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Condition> partialUpdateCondition(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Condition condition
    ) throws URISyntaxException {
        log.debug("REST request to partial update Condition partially : {}, {}", id, condition);
        if (condition.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, condition.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!conditionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Condition> result = conditionService.partialUpdate(condition);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, condition.getId().toString())
        );
    }

    /**
     * {@code GET  /conditions} : get all the conditions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conditions in body.
     */
    @GetMapping("/conditions")
    public ResponseEntity<List<Condition>> getAllConditions(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Conditions");
        Page<Condition> page = conditionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /conditions/:id} : get the "id" condition.
     *
     * @param id the id of the condition to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the condition, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/conditions/{id}")
    public ResponseEntity<Condition> getCondition(@PathVariable Long id) {
        log.debug("REST request to get Condition : {}", id);
        Optional<Condition> condition = conditionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(condition);
    }

    /**
     * {@code DELETE  /conditions/:id} : delete the "id" condition.
     *
     * @param id the id of the condition to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/conditions/{id}")
    public ResponseEntity<Void> deleteCondition(@PathVariable Long id) {
        log.debug("REST request to delete Condition : {}", id);
        conditionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
