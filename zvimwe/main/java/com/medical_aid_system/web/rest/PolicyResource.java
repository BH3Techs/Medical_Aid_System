package com.medical_aid_system.web.rest;

import com.medical_aid_system.domain.Policy;
import com.medical_aid_system.repository.PolicyRepository;
import com.medical_aid_system.service.PolicyService;
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
 * REST controller for managing {@link com.medical_aid_system.domain.Policy}.
 */
@RestController
@RequestMapping("/api")
public class PolicyResource {

    private final Logger log = LoggerFactory.getLogger(PolicyResource.class);

    private static final String ENTITY_NAME = "policy";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PolicyService policyService;

    private final PolicyRepository policyRepository;

    public PolicyResource(PolicyService policyService, PolicyRepository policyRepository) {
        this.policyService = policyService;
        this.policyRepository = policyRepository;
    }

    /**
     * {@code POST  /policies} : Create a new policy.
     *
     * @param policy the policy to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new policy, or with status {@code 400 (Bad Request)} if the policy has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/policies")
    public ResponseEntity<Policy> createPolicy(@Valid @RequestBody Policy policy) throws URISyntaxException {
        log.debug("REST request to save Policy : {}", policy);
        if (policy.getId() != null) {
            throw new BadRequestAlertException("A new policy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Policy result = policyService.save(policy);
        return ResponseEntity
            .created(new URI("/api/policies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /policies/:id} : Updates an existing policy.
     *
     * @param id the id of the policy to save.
     * @param policy the policy to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated policy,
     * or with status {@code 400 (Bad Request)} if the policy is not valid,
     * or with status {@code 500 (Internal Server Error)} if the policy couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/policies/{id}")
    public ResponseEntity<Policy> updatePolicy(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Policy policy
    ) throws URISyntaxException {
        log.debug("REST request to update Policy : {}, {}", id, policy);
        if (policy.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, policy.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!policyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Policy result = policyService.update(policy);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, policy.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /policies/:id} : Partial updates given fields of an existing policy, field will ignore if it is null
     *
     * @param id the id of the policy to save.
     * @param policy the policy to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated policy,
     * or with status {@code 400 (Bad Request)} if the policy is not valid,
     * or with status {@code 404 (Not Found)} if the policy is not found,
     * or with status {@code 500 (Internal Server Error)} if the policy couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/policies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Policy> partialUpdatePolicy(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Policy policy
    ) throws URISyntaxException {
        log.debug("REST request to partial update Policy partially : {}, {}", id, policy);
        if (policy.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, policy.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!policyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Policy> result = policyService.partialUpdate(policy);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, policy.getId().toString())
        );
    }

    /**
     * {@code GET  /policies} : get all the policies.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of policies in body.
     */
    @GetMapping("/policies")
    public ResponseEntity<List<Policy>> getAllPolicies(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Policies");
        Page<Policy> page = policyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /policies/:id} : get the "id" policy.
     *
     * @param id the id of the policy to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the policy, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/policies/{id}")
    public ResponseEntity<Policy> getPolicy(@PathVariable Long id) {
        log.debug("REST request to get Policy : {}", id);
        Optional<Policy> policy = policyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(policy);
    }

    /**
     * {@code DELETE  /policies/:id} : delete the "id" policy.
     *
     * @param id the id of the policy to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/policies/{id}")
    public ResponseEntity<Void> deletePolicy(@PathVariable Long id) {
        log.debug("REST request to delete Policy : {}", id);
        policyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
