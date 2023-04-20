package com.medical_aid_system.web.rest;

import com.medical_aid_system.domain.Tariff;
import com.medical_aid_system.repository.TariffRepository;
import com.medical_aid_system.service.TariffService;
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
 * REST controller for managing {@link com.medical_aid_system.domain.Tariff}.
 */
@RestController
@RequestMapping("/api")
public class TariffResource {

    private final Logger log = LoggerFactory.getLogger(TariffResource.class);

    private static final String ENTITY_NAME = "tariff";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TariffService tariffService;

    private final TariffRepository tariffRepository;

    public TariffResource(TariffService tariffService, TariffRepository tariffRepository) {
        this.tariffService = tariffService;
        this.tariffRepository = tariffRepository;
    }

    /**
     * {@code POST  /tariffs} : Create a new tariff.
     *
     * @param tariff the tariff to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tariff, or with status {@code 400 (Bad Request)} if the tariff has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tariffs")
    public ResponseEntity<Tariff> createTariff(@Valid @RequestBody Tariff tariff) throws URISyntaxException {
        log.debug("REST request to save Tariff : {}", tariff);
        if (tariff.getId() != null) {
            throw new BadRequestAlertException("A new tariff cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Tariff result = tariffService.save(tariff);
        return ResponseEntity
            .created(new URI("/api/tariffs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tariffs/:id} : Updates an existing tariff.
     *
     * @param id the id of the tariff to save.
     * @param tariff the tariff to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tariff,
     * or with status {@code 400 (Bad Request)} if the tariff is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tariff couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tariffs/{id}")
    public ResponseEntity<Tariff> updateTariff(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Tariff tariff
    ) throws URISyntaxException {
        log.debug("REST request to update Tariff : {}, {}", id, tariff);
        if (tariff.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tariff.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tariffRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Tariff result = tariffService.update(tariff);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tariff.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tariffs/:id} : Partial updates given fields of an existing tariff, field will ignore if it is null
     *
     * @param id the id of the tariff to save.
     * @param tariff the tariff to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tariff,
     * or with status {@code 400 (Bad Request)} if the tariff is not valid,
     * or with status {@code 404 (Not Found)} if the tariff is not found,
     * or with status {@code 500 (Internal Server Error)} if the tariff couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tariffs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Tariff> partialUpdateTariff(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Tariff tariff
    ) throws URISyntaxException {
        log.debug("REST request to partial update Tariff partially : {}, {}", id, tariff);
        if (tariff.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tariff.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tariffRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Tariff> result = tariffService.partialUpdate(tariff);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tariff.getId().toString())
        );
    }

    /**
     * {@code GET  /tariffs} : get all the tariffs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tariffs in body.
     */
    @GetMapping("/tariffs")
    public ResponseEntity<List<Tariff>> getAllTariffs(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Tariffs");
        Page<Tariff> page = tariffService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tariffs/:id} : get the "id" tariff.
     *
     * @param id the id of the tariff to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tariff, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tariffs/{id}")
    public ResponseEntity<Tariff> getTariff(@PathVariable Long id) {
        log.debug("REST request to get Tariff : {}", id);
        Optional<Tariff> tariff = tariffService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tariff);
    }

    /**
     * {@code DELETE  /tariffs/:id} : delete the "id" tariff.
     *
     * @param id the id of the tariff to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tariffs/{id}")
    public ResponseEntity<Void> deleteTariff(@PathVariable Long id) {
        log.debug("REST request to delete Tariff : {}", id);
        tariffService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
