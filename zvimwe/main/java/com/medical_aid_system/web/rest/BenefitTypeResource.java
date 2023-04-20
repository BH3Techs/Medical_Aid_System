package com.medical_aid_system.web.rest;

import com.medical_aid_system.domain.BenefitType;
import com.medical_aid_system.repository.BenefitTypeRepository;
import com.medical_aid_system.service.BenefitTypeService;
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
 * REST controller for managing {@link com.medical_aid_system.domain.BenefitType}.
 */
@RestController
@RequestMapping("/api")
public class BenefitTypeResource {

    private final Logger log = LoggerFactory.getLogger(BenefitTypeResource.class);

    private static final String ENTITY_NAME = "benefitType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BenefitTypeService benefitTypeService;

    private final BenefitTypeRepository benefitTypeRepository;

    public BenefitTypeResource(BenefitTypeService benefitTypeService, BenefitTypeRepository benefitTypeRepository) {
        this.benefitTypeService = benefitTypeService;
        this.benefitTypeRepository = benefitTypeRepository;
    }

    /**
     * {@code POST  /benefit-types} : Create a new benefitType.
     *
     * @param benefitType the benefitType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new benefitType, or with status {@code 400 (Bad Request)} if the benefitType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/benefit-types")
    public ResponseEntity<BenefitType> createBenefitType(@Valid @RequestBody BenefitType benefitType) throws URISyntaxException {
        log.debug("REST request to save BenefitType : {}", benefitType);
        if (benefitType.getId() != null) {
            throw new BadRequestAlertException("A new benefitType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BenefitType result = benefitTypeService.save(benefitType);
        return ResponseEntity
            .created(new URI("/api/benefit-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /benefit-types/:id} : Updates an existing benefitType.
     *
     * @param id the id of the benefitType to save.
     * @param benefitType the benefitType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated benefitType,
     * or with status {@code 400 (Bad Request)} if the benefitType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the benefitType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/benefit-types/{id}")
    public ResponseEntity<BenefitType> updateBenefitType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BenefitType benefitType
    ) throws URISyntaxException {
        log.debug("REST request to update BenefitType : {}, {}", id, benefitType);
        if (benefitType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, benefitType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!benefitTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BenefitType result = benefitTypeService.update(benefitType);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, benefitType.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /benefit-types/:id} : Partial updates given fields of an existing benefitType, field will ignore if it is null
     *
     * @param id the id of the benefitType to save.
     * @param benefitType the benefitType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated benefitType,
     * or with status {@code 400 (Bad Request)} if the benefitType is not valid,
     * or with status {@code 404 (Not Found)} if the benefitType is not found,
     * or with status {@code 500 (Internal Server Error)} if the benefitType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/benefit-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BenefitType> partialUpdateBenefitType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BenefitType benefitType
    ) throws URISyntaxException {
        log.debug("REST request to partial update BenefitType partially : {}, {}", id, benefitType);
        if (benefitType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, benefitType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!benefitTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BenefitType> result = benefitTypeService.partialUpdate(benefitType);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, benefitType.getId().toString())
        );
    }

    /**
     * {@code GET  /benefit-types} : get all the benefitTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of benefitTypes in body.
     */
    @GetMapping("/benefit-types")
    public ResponseEntity<List<BenefitType>> getAllBenefitTypes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of BenefitTypes");
        Page<BenefitType> page = benefitTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /benefit-types/:id} : get the "id" benefitType.
     *
     * @param id the id of the benefitType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the benefitType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/benefit-types/{id}")
    public ResponseEntity<BenefitType> getBenefitType(@PathVariable Long id) {
        log.debug("REST request to get BenefitType : {}", id);
        Optional<BenefitType> benefitType = benefitTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(benefitType);
    }

    /**
     * {@code DELETE  /benefit-types/:id} : delete the "id" benefitType.
     *
     * @param id the id of the benefitType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/benefit-types/{id}")
    public ResponseEntity<Void> deleteBenefitType(@PathVariable Long id) {
        log.debug("REST request to delete BenefitType : {}", id);
        benefitTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
