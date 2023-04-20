package com.medical_aid_system.service.impl;

import com.medical_aid_system.domain.Invoice;
import com.medical_aid_system.repository.InvoiceRepository;
import com.medical_aid_system.service.InvoiceService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Invoice}.
 */
@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

    private final Logger log = LoggerFactory.getLogger(InvoiceServiceImpl.class);

    private final InvoiceRepository invoiceRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public Invoice save(Invoice invoice) {
        log.debug("Request to save Invoice : {}", invoice);
        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice update(Invoice invoice) {
        log.debug("Request to update Invoice : {}", invoice);
        return invoiceRepository.save(invoice);
    }

    @Override
    public Optional<Invoice> partialUpdate(Invoice invoice) {
        log.debug("Request to partially update Invoice : {}", invoice);

        return invoiceRepository
            .findById(invoice.getId())
            .map(existingInvoice -> {
                if (invoice.getInvoiceNumber() != null) {
                    existingInvoice.setInvoiceNumber(invoice.getInvoiceNumber());
                }
                if (invoice.getInvoiceStatus() != null) {
                    existingInvoice.setInvoiceStatus(invoice.getInvoiceStatus());
                }
                if (invoice.getAmountPayable() != null) {
                    existingInvoice.setAmountPayable(invoice.getAmountPayable());
                }
                if (invoice.getInvoiceDate() != null) {
                    existingInvoice.setInvoiceDate(invoice.getInvoiceDate());
                }
                if (invoice.getNextInvoiceDate() != null) {
                    existingInvoice.setNextInvoiceDate(invoice.getNextInvoiceDate());
                }
                if (invoice.getInvoiceAmount() != null) {
                    existingInvoice.setInvoiceAmount(invoice.getInvoiceAmount());
                }
                if (invoice.getExpectedPaymentDate() != null) {
                    existingInvoice.setExpectedPaymentDate(invoice.getExpectedPaymentDate());
                }
                if (invoice.getGracePeriod() != null) {
                    existingInvoice.setGracePeriod(invoice.getGracePeriod());
                }

                return existingInvoice;
            })
            .map(invoiceRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Invoice> findAll(Pageable pageable) {
        log.debug("Request to get all Invoices");
        return invoiceRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Invoice> findOne(Long id) {
        log.debug("Request to get Invoice : {}", id);
        return invoiceRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Invoice : {}", id);
        invoiceRepository.deleteById(id);
    }
}
