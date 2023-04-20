package com.xplug.medical_aid_system.web.rest;

import com.xplug.medical_aid_system.domain.NextOfKin;
import com.xplug.medical_aid_system.repository.NextOfKinRepository;
import com.xplug.medical_aid_system.service.NextOfKinService;
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
 * REST controller for managing {@link com.xplug.medical_aid_system.domain.NextOfKin}.
 */
@RestController
@RequestMapping("/api")
public class NextOfKinResource {

    private final Logger log = LoggerFactory.getLogger(NextOfKinResource.class);

    private static final String ENTITY_NAME = "nextOfKin";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NextOfKinService nextOfKinService;

    private final NextOfKinRepository nextOfKinRepository;

    public NextOfKinResource(NextOfKinService nextOfKinService, NextOfKinRepository nextOfKinRepository) {
        this.nextOfKinService = nextOfKinService;
        this.nextOfKinRepository = nextOfKinRepository;
    }

    /**
     * {@code POST  /next-of-kins} : Create a new nextOfKin.
     *
     * @param nextOfKin the nextOfKin to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nextOfKin, or with status {@code 400 (Bad Request)} if the nextOfKin has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/next-of-kins")
    public ResponseEntity<NextOfKin> createNextOfKin(@Valid @RequestBody NextOfKin nextOfKin) throws URISyntaxException {
        log.debug("REST request to save NextOfKin : {}", nextOfKin);
        if (nextOfKin.getId() != null) {
            throw new BadRequestAlertException("A new nextOfKin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NextOfKin result = nextOfKinService.save(nextOfKin);
        return ResponseEntity
            .created(new URI("/api/next-of-kins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /next-of-kins/:id} : Updates an existing nextOfKin.
     *
     * @param id the id of the nextOfKin to save.
     * @param nextOfKin the nextOfKin to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nextOfKin,
     * or with status {@code 400 (Bad Request)} if the nextOfKin is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nextOfKin couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/next-of-kins/{id}")
    public ResponseEntity<NextOfKin> updateNextOfKin(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NextOfKin nextOfKin
    ) throws URISyntaxException {
        log.debug("REST request to update NextOfKin : {}, {}", id, nextOfKin);
        if (nextOfKin.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nextOfKin.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nextOfKinRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NextOfKin result = nextOfKinService.update(nextOfKin);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nextOfKin.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /next-of-kins/:id} : Partial updates given fields of an existing nextOfKin, field will ignore if it is null
     *
     * @param id the id of the nextOfKin to save.
     * @param nextOfKin the nextOfKin to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nextOfKin,
     * or with status {@code 400 (Bad Request)} if the nextOfKin is not valid,
     * or with status {@code 404 (Not Found)} if the nextOfKin is not found,
     * or with status {@code 500 (Internal Server Error)} if the nextOfKin couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/next-of-kins/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NextOfKin> partialUpdateNextOfKin(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NextOfKin nextOfKin
    ) throws URISyntaxException {
        log.debug("REST request to partial update NextOfKin partially : {}, {}", id, nextOfKin);
        if (nextOfKin.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nextOfKin.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nextOfKinRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NextOfKin> result = nextOfKinService.partialUpdate(nextOfKin);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nextOfKin.getId().toString())
        );
    }

    /**
     * {@code GET  /next-of-kins} : get all the nextOfKins.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nextOfKins in body.
     */
    @GetMapping("/next-of-kins")
    public ResponseEntity<List<NextOfKin>> getAllNextOfKins(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of NextOfKins");
        Page<NextOfKin> page = nextOfKinService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /next-of-kins/:id} : get the "id" nextOfKin.
     *
     * @param id the id of the nextOfKin to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nextOfKin, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/next-of-kins/{id}")
    public ResponseEntity<NextOfKin> getNextOfKin(@PathVariable Long id) {
        log.debug("REST request to get NextOfKin : {}", id);
        Optional<NextOfKin> nextOfKin = nextOfKinService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nextOfKin);
    }

    /**
     * {@code DELETE  /next-of-kins/:id} : delete the "id" nextOfKin.
     *
     * @param id the id of the nextOfKin to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/next-of-kins/{id}")
    public ResponseEntity<Void> deleteNextOfKin(@PathVariable Long id) {
        log.debug("REST request to delete NextOfKin : {}", id);
        nextOfKinService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
