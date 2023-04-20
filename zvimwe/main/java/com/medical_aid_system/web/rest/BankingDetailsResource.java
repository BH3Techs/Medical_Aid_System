package com.medical_aid_system.web.rest;

import com.medical_aid_system.domain.BankingDetails;
import com.medical_aid_system.repository.BankingDetailsRepository;
import com.medical_aid_system.service.BankingDetailsService;
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
 * REST controller for managing {@link com.medical_aid_system.domain.BankingDetails}.
 */
@RestController
@RequestMapping("/api")
public class BankingDetailsResource {

    private final Logger log = LoggerFactory.getLogger(BankingDetailsResource.class);

    private static final String ENTITY_NAME = "bankingDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankingDetailsService bankingDetailsService;

    private final BankingDetailsRepository bankingDetailsRepository;

    public BankingDetailsResource(BankingDetailsService bankingDetailsService, BankingDetailsRepository bankingDetailsRepository) {
        this.bankingDetailsService = bankingDetailsService;
        this.bankingDetailsRepository = bankingDetailsRepository;
    }

    /**
     * {@code POST  /banking-details} : Create a new bankingDetails.
     *
     * @param bankingDetails the bankingDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankingDetails, or with status {@code 400 (Bad Request)} if the bankingDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/banking-details")
    public ResponseEntity<BankingDetails> createBankingDetails(@Valid @RequestBody BankingDetails bankingDetails)
        throws URISyntaxException {
        log.debug("REST request to save BankingDetails : {}", bankingDetails);
        if (bankingDetails.getId() != null) {
            throw new BadRequestAlertException("A new bankingDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankingDetails result = bankingDetailsService.save(bankingDetails);
        return ResponseEntity
            .created(new URI("/api/banking-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /banking-details/:id} : Updates an existing bankingDetails.
     *
     * @param id the id of the bankingDetails to save.
     * @param bankingDetails the bankingDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankingDetails,
     * or with status {@code 400 (Bad Request)} if the bankingDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankingDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/banking-details/{id}")
    public ResponseEntity<BankingDetails> updateBankingDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BankingDetails bankingDetails
    ) throws URISyntaxException {
        log.debug("REST request to update BankingDetails : {}, {}", id, bankingDetails);
        if (bankingDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankingDetails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankingDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BankingDetails result = bankingDetailsService.update(bankingDetails);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankingDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /banking-details/:id} : Partial updates given fields of an existing bankingDetails, field will ignore if it is null
     *
     * @param id the id of the bankingDetails to save.
     * @param bankingDetails the bankingDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankingDetails,
     * or with status {@code 400 (Bad Request)} if the bankingDetails is not valid,
     * or with status {@code 404 (Not Found)} if the bankingDetails is not found,
     * or with status {@code 500 (Internal Server Error)} if the bankingDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/banking-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BankingDetails> partialUpdateBankingDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BankingDetails bankingDetails
    ) throws URISyntaxException {
        log.debug("REST request to partial update BankingDetails partially : {}, {}", id, bankingDetails);
        if (bankingDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankingDetails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankingDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BankingDetails> result = bankingDetailsService.partialUpdate(bankingDetails);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankingDetails.getId().toString())
        );
    }

    /**
     * {@code GET  /banking-details} : get all the bankingDetails.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankingDetails in body.
     */
    @GetMapping("/banking-details")
    public ResponseEntity<List<BankingDetails>> getAllBankingDetails(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of BankingDetails");
        Page<BankingDetails> page = bankingDetailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /banking-details/:id} : get the "id" bankingDetails.
     *
     * @param id the id of the bankingDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankingDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/banking-details/{id}")
    public ResponseEntity<BankingDetails> getBankingDetails(@PathVariable Long id) {
        log.debug("REST request to get BankingDetails : {}", id);
        Optional<BankingDetails> bankingDetails = bankingDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bankingDetails);
    }

    /**
     * {@code DELETE  /banking-details/:id} : delete the "id" bankingDetails.
     *
     * @param id the id of the bankingDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/banking-details/{id}")
    public ResponseEntity<Void> deleteBankingDetails(@PathVariable Long id) {
        log.debug("REST request to delete BankingDetails : {}", id);
        bankingDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
