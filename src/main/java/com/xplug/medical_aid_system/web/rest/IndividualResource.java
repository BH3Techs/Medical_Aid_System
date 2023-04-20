package com.xplug.medical_aid_system.web.rest;

import com.xplug.medical_aid_system.domain.Individual;
import com.xplug.medical_aid_system.repository.IndividualRepository;
import com.xplug.medical_aid_system.service.IndividualService;
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
 * REST controller for managing {@link com.xplug.medical_aid_system.domain.Individual}.
 */
@RestController
@RequestMapping("/api")
public class IndividualResource {

    private final Logger log = LoggerFactory.getLogger(IndividualResource.class);

    private static final String ENTITY_NAME = "individual";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IndividualService individualService;

    private final IndividualRepository individualRepository;

    public IndividualResource(IndividualService individualService, IndividualRepository individualRepository) {
        this.individualService = individualService;
        this.individualRepository = individualRepository;
    }

    /**
     * {@code POST  /individuals} : Create a new individual.
     *
     * @param individual the individual to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new individual, or with status {@code 400 (Bad Request)} if the individual has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/individuals")
    public ResponseEntity<Individual> createIndividual(@Valid @RequestBody Individual individual) throws URISyntaxException {
        log.debug("REST request to save Individual : {}", individual);
        if (individual.getId() != null) {
            throw new BadRequestAlertException("A new individual cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Individual result = individualService.save(individual);
        return ResponseEntity
            .created(new URI("/api/individuals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /individuals/:id} : Updates an existing individual.
     *
     * @param id the id of the individual to save.
     * @param individual the individual to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated individual,
     * or with status {@code 400 (Bad Request)} if the individual is not valid,
     * or with status {@code 500 (Internal Server Error)} if the individual couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/individuals/{id}")
    public ResponseEntity<Individual> updateIndividual(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Individual individual
    ) throws URISyntaxException {
        log.debug("REST request to update Individual : {}, {}", id, individual);
        if (individual.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, individual.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!individualRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Individual result = individualService.update(individual);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, individual.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /individuals/:id} : Partial updates given fields of an existing individual, field will ignore if it is null
     *
     * @param id the id of the individual to save.
     * @param individual the individual to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated individual,
     * or with status {@code 400 (Bad Request)} if the individual is not valid,
     * or with status {@code 404 (Not Found)} if the individual is not found,
     * or with status {@code 500 (Internal Server Error)} if the individual couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/individuals/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Individual> partialUpdateIndividual(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Individual individual
    ) throws URISyntaxException {
        log.debug("REST request to partial update Individual partially : {}, {}", id, individual);
        if (individual.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, individual.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!individualRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Individual> result = individualService.partialUpdate(individual);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, individual.getId().toString())
        );
    }

    /**
     * {@code GET  /individuals} : get all the individuals.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of individuals in body.
     */
    @GetMapping("/individuals")
    public ResponseEntity<List<Individual>> getAllIndividuals(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Individuals");
        Page<Individual> page = individualService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /individuals/:id} : get the "id" individual.
     *
     * @param id the id of the individual to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the individual, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/individuals/{id}")
    public ResponseEntity<Individual> getIndividual(@PathVariable Long id) {
        log.debug("REST request to get Individual : {}", id);
        Optional<Individual> individual = individualService.findOne(id);
        return ResponseUtil.wrapOrNotFound(individual);
    }

    /**
     * {@code DELETE  /individuals/:id} : delete the "id" individual.
     *
     * @param id the id of the individual to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/individuals/{id}")
    public ResponseEntity<Void> deleteIndividual(@PathVariable Long id) {
        log.debug("REST request to delete Individual : {}", id);
        individualService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
