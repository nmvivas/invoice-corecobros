package com.banquito.core.invoicedoc.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banquito.core.invoicedoc.dto.InvoiceDTO;
import com.banquito.core.invoicedoc.model.DetailInvoice;
import com.banquito.core.invoicedoc.model.Invoice;
import com.banquito.core.invoicedoc.model.Tax;
import com.banquito.core.invoicedoc.repository.InvoiceRepository;
import com.banquito.core.invoicedoc.util.UniqueIdGeneration;
import com.banquito.core.invoicedoc.util.mapper.InvoiceMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final UniqueIdGeneration uniqueIdGeneration;
    private final InvoiceMapper mapper;

    public InvoiceService(InvoiceRepository invoiceRepository, UniqueIdGeneration uniqueIdGeneration,
            InvoiceMapper mapper) {
        this.invoiceRepository = invoiceRepository;
        this.uniqueIdGeneration = uniqueIdGeneration;
        this.mapper = mapper;
    }

    @Transactional
    public InvoiceDTO create(InvoiceDTO invoiceDTO) {
        log.info("Creando nueva factura.");
        Invoice invoice = mapper.toPersistence(invoiceDTO);
        invoice.setUniqueId(uniqueIdGeneration.generateUniqueId());
        invoice.setDate(LocalDateTime.now());

        if (invoice.getDetailInvoices() != null) {
            for (DetailInvoice detail : invoice.getDetailInvoices()) {
                if (detail.getId() == null) {
                    detail.setId(UUID.randomUUID().toString());
                }
            }
        }

        if (invoice.getTaxes() != null) {
            for (Tax taxes : invoice.getTaxes()) {
                if (taxes.getId() == null) {
                    taxes.setId(UUID.randomUUID().toString());
                }
            }
        }

        invoice = invoiceRepository.save(invoice);
        return mapper.toDTO(invoice);
    }

    public List<InvoiceDTO> getAllInvoices() {
        log.info("Fetching all invoices");
        return invoiceRepository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public InvoiceDTO updateInvoice(String id, InvoiceDTO invoiceDTO) {
        log.info("Updating invoice with id: {}", id);
        Invoice existingInvoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encuentra la factura"));
        Invoice invoice = mapper.toPersistence(invoiceDTO);
        invoice.setId(existingInvoice.getId());
        invoice = invoiceRepository.save(invoice);
        return mapper.toDTO(invoice);
    }

    public void deleteInvoice(String id) {
        log.info("Deleting invoice with id: {}", id);
        invoiceRepository.deleteById(id);
        log.info("Invoice deleted successfully with id: {}", id);
    }

    public InvoiceDTO getInvoiceBySequential(String sequential) {
        log.info("Fetching invoice with sequential: {}", sequential);
        Invoice invoice = invoiceRepository.findBySequential(sequential)
                .orElseThrow(() -> new RuntimeException("Invoice not found with sequential: " + sequential));
        return mapper.toDTO(invoice);
    }

    // public List<InvoiceDTO> getInvoicesByDateRange(String startDate, String
    // endDate) {
    // DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
    // LocalDateTime start = LocalDateTime.parse(startDate, formatter);
    // LocalDateTime end = LocalDateTime.parse(endDate, formatter);

    // List<Invoice> invoices =
    // invoiceRepository.findByDateBetween(start.atStartOfDay(),
    // end.plusDays(1).atStartOfDay());
    // return
    // invoices.stream().map(invoiceMapper::toDTO).collect(Collectors.toList());
    // }

    public List<InvoiceDTO> getInvoicesByClient(String ruc, String companyName) {
        List<Invoice> invoices;
        if (companyName != null && !companyName.isEmpty()) {
            invoices = invoiceRepository.findByRucAndCompanyNameContainingIgnoreCase(ruc, companyName);
        } else {
            invoices = invoiceRepository.findByRuc(ruc);
        }
        return invoices.stream().map(mapper::toDTO).collect(Collectors.toList());
    }
}