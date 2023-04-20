package com.xplug.medical_aid_system.web.rest;

import com.xplug.medical_aid_system.domain.InvoiceLine;
import com.xplug.medical_aid_system.repository.InvoiceLineRepository;
import com.xplug.medical_aid_system.service.InvoiceLineService;
import com.xplug.medical_aid_system.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link com.xplug.medical_aid_system.domain.InvoiceLine}.
 */
@RestController
@RequestMapping("/api")
public class InvoiceLineResource {

    private final Logger log = LoggerFactory.getLogger(InvoiceLineResource.class);

    private static final String ENTITY_NAME = "invoiceLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InvoiceLineService invoiceLineService;

    private final InvoiceLineRepository invoiceLineRepository;

    public InvoiceLineResource(InvoiceLineService invoiceLineService, InvoiceLineRepository invoiceLineRepository) {
        this.invoiceLineService = invoiceLineService;
        this.invoiceLineRepository = invoiceLineRepository;
    }

    /**
     * {@code POST  /invoice-lines} : Create a new invoiceLine.
     *
     * @param invoiceLine the invoiceLine to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new invoiceLine, or with status {@code 400 (Bad Request)} if the invoiceLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/invoice-lines")
    public ResponseEntity<InvoiceLine> createInvoiceLine(@RequestBody InvoiceLine invoiceLine) throws URISyntaxException {
        log.debug("REST request to save InvoiceLine : {}", invoiceLine);
        if (invoiceLine.getId() != null) {
            throw new BadRequestAlertException("A new invoiceLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InvoiceLine result = invoiceLineService.save(invoiceLine);
        return ResponseEntity
            .created(new URI("/api/invoice-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /invoice-lines/:id} : Updates an existing invoiceLine.
     *
     * @param id the id of the invoiceLine to save.
     * @param invoiceLine the invoiceLine to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated invoiceLine,
     * or with status {@code 400 (Bad Request)} if the invoiceLine is not valid,
     * or with status {@code 500 (Internal Server Error)} if the invoiceLine couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/invoice-lines/{id}")
    public ResponseEntity<InvoiceLine> updateInvoiceLine(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InvoiceLine invoiceLine
    ) throws URISyntaxException {
        log.debug("REST request to update InvoiceLine : {}, {}", id, invoiceLine);
        if (invoiceLine.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, invoiceLine.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!invoiceLineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        InvoiceLine result = invoiceLineService.update(invoiceLine);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, invoiceLine.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /invoice-lines/:id} : Partial updates given fields of an existing invoiceLine, field will ignore if it is null
     *
     * @param id the id of the invoiceLine to save.
     * @param invoiceLine the invoiceLine to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated invoiceLine,
     * or with status {@code 400 (Bad Request)} if the invoiceLine is not valid,
     * or with status {@code 404 (Not Found)} if the invoiceLine is not found,
     * or with status {@code 500 (Internal Server Error)} if the invoiceLine couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/invoice-lines/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InvoiceLine> partialUpdateInvoiceLine(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InvoiceLine invoiceLine
    ) throws URISyntaxException {
        log.debug("REST request to partial update InvoiceLine partially : {}, {}", id, invoiceLine);
        if (invoiceLine.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, invoiceLine.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!invoiceLineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InvoiceLine> result = invoiceLineService.partialUpdate(invoiceLine);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, invoiceLine.getId().toString())
        );
    }

    /**
     * {@code GET  /invoice-lines} : get all the invoiceLines.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of invoiceLines in body.
     */
    @GetMapping("/invoice-lines")
    public ResponseEntity<List<InvoiceLine>> getAllInvoiceLines(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of InvoiceLines");
        Page<InvoiceLine> page = invoiceLineService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /invoice-lines/:id} : get the "id" invoiceLine.
     *
     * @param id the id of the invoiceLine to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the invoiceLine, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/invoice-lines/{id}")
    public ResponseEntity<InvoiceLine> getInvoiceLine(@PathVariable Long id) {
        log.debug("REST request to get InvoiceLine : {}", id);
        Optional<InvoiceLine> invoiceLine = invoiceLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(invoiceLine);
    }

    /**
     * {@code DELETE  /invoice-lines/:id} : delete the "id" invoiceLine.
     *
     * @param id the id of the invoiceLine to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/invoice-lines/{id}")
    public ResponseEntity<Void> deleteInvoiceLine(@PathVariable Long id) {
        log.debug("REST request to delete InvoiceLine : {}", id);
        invoiceLineService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
