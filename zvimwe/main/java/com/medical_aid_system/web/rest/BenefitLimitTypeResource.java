package com.medical_aid_system.web.rest;

import com.medical_aid_system.domain.BenefitLimitType;
import com.medical_aid_system.repository.BenefitLimitTypeRepository;
import com.medical_aid_system.service.BenefitLimitTypeService;
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
 * REST controller for managing {@link com.medical_aid_system.domain.BenefitLimitType}.
 */
@RestController
@RequestMapping("/api")
public class BenefitLimitTypeResource {

    private final Logger log = LoggerFactory.getLogger(BenefitLimitTypeResource.class);

    private static final String ENTITY_NAME = "benefitLimitType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BenefitLimitTypeService benefitLimitTypeService;

    private final BenefitLimitTypeRepository benefitLimitTypeRepository;

    public BenefitLimitTypeResource(
        BenefitLimitTypeService benefitLimitTypeService,
        BenefitLimitTypeRepository benefitLimitTypeRepository
    ) {
        this.benefitLimitTypeService = benefitLimitTypeService;
        this.benefitLimitTypeRepository = benefitLimitTypeRepository;
    }

    /**
     * {@code POST  /benefit-limit-types} : Create a new benefitLimitType.
     *
     * @param benefitLimitType the benefitLimitType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new benefitLimitType, or with status {@code 400 (Bad Request)} if the benefitLimitType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/benefit-limit-types")
    public ResponseEntity<BenefitLimitType> createBenefitLimitType(@Valid @RequestBody BenefitLimitType benefitLimitType)
        throws URISyntaxException {
        log.debug("REST request to save BenefitLimitType : {}", benefitLimitType);
        if (benefitLimitType.getId() != null) {
            throw new BadRequestAlertException("A new benefitLimitType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BenefitLimitType result = benefitLimitTypeService.save(benefitLimitType);
        return ResponseEntity
            .created(new URI("/api/benefit-limit-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /benefit-limit-types/:id} : Updates an existing benefitLimitType.
     *
     * @param id the id of the benefitLimitType to save.
     * @param benefitLimitType the benefitLimitType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated benefitLimitType,
     * or with status {@code 400 (Bad Request)} if the benefitLimitType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the benefitLimitType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/benefit-limit-types/{id}")
    public ResponseEntity<BenefitLimitType> updateBenefitLimitType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BenefitLimitType benefitLimitType
    ) throws URISyntaxException {
        log.debug("REST request to update BenefitLimitType : {}, {}", id, benefitLimitType);
        if (benefitLimitType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, benefitLimitType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!benefitLimitTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BenefitLimitType result = benefitLimitTypeService.update(benefitLimitType);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, benefitLimitType.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /benefit-limit-types/:id} : Partial updates given fields of an existing benefitLimitType, field will ignore if it is null
     *
     * @param id the id of the benefitLimitType to save.
     * @param benefitLimitType the benefitLimitType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated benefitLimitType,
     * or with status {@code 400 (Bad Request)} if the benefitLimitType is not valid,
     * or with status {@code 404 (Not Found)} if the benefitLimitType is not found,
     * or with status {@code 500 (Internal Server Error)} if the benefitLimitType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/benefit-limit-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BenefitLimitType> partialUpdateBenefitLimitType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BenefitLimitType benefitLimitType
    ) throws URISyntaxException {
        log.debug("REST request to partial update BenefitLimitType partially : {}, {}", id, benefitLimitType);
        if (benefitLimitType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, benefitLimitType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!benefitLimitTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BenefitLimitType> result = benefitLimitTypeService.partialUpdate(benefitLimitType);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, benefitLimitType.getId().toString())
        );
    }

    /**
     * {@code GET  /benefit-limit-types} : get all the benefitLimitTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of benefitLimitTypes in body.
     */
    @GetMapping("/benefit-limit-types")
    public ResponseEntity<List<BenefitLimitType>> getAllBenefitLimitTypes(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of BenefitLimitTypes");
        Page<BenefitLimitType> page = benefitLimitTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /benefit-limit-types/:id} : get the "id" benefitLimitType.
     *
     * @param id the id of the benefitLimitType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the benefitLimitType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/benefit-limit-types/{id}")
    public ResponseEntity<BenefitLimitType> getBenefitLimitType(@PathVariable Long id) {
        log.debug("REST request to get BenefitLimitType : {}", id);
        Optional<BenefitLimitType> benefitLimitType = benefitLimitTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(benefitLimitType);
    }

    /**
     * {@code DELETE  /benefit-limit-types/:id} : delete the "id" benefitLimitType.
     *
     * @param id the id of the benefitLimitType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/benefit-limit-types/{id}")
    public ResponseEntity<Void> deleteBenefitLimitType(@PathVariable Long id) {
        log.debug("REST request to delete BenefitLimitType : {}", id);
        benefitLimitTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
