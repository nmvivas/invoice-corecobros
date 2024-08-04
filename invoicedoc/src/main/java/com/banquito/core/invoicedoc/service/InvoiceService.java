package com.banquito.core.invoicedoc.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banquito.core.invoicedoc.dto.InvoiceDTO;
import com.banquito.core.invoicedoc.model.DetailInvoice;
import com.banquito.core.invoicedoc.model.Invoice;
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
        if (invoiceDTO.getUniqueId() == null) {
            invoiceDTO.setUniqueId(uniqueIdGeneration.generateUniqueId());
        }

        if (invoiceDTO.getDate() == null) {
            invoiceDTO.setDate(LocalDateTime.now());

        }
        
        log.info("Creando nueva factura: {}", invoiceDTO);
        Invoice invoice = mapper.toPersistence(invoiceDTO);

        if (invoice.getDetailInvoices() != null) {
            for (DetailInvoice detail : invoice.getDetailInvoices()) {
                if (detail.getInvoiceId() == null) {
                    detail.setInvoiceId(invoice.getUniqueId());
                }
            }
        }

        if (invoice.getUniqueId() == null) {
            throw new IllegalArgumentException("El campo uniqueId no puede ser null.");
        }

        invoice = invoiceRepository.save(invoice);
        log.info("Se creó la factura: {}", invoice);
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

        if (invoiceDTO.getAddress() != null) {
            existingInvoice.setAddress(invoiceDTO.getAddress());
        }
        if (invoiceDTO.getEmail() != null) {
            existingInvoice.setEmail(invoiceDTO.getEmail());
        }
        if (invoiceDTO.getPhone() != null) {
            existingInvoice.setPhone(invoiceDTO.getPhone());
        }
        if (invoiceDTO.getSubtotal() != null) {
            existingInvoice.setSubtotal(invoiceDTO.getSubtotal());
        }
        if (invoiceDTO.getTotal() != null) {
            existingInvoice.setTotal(invoiceDTO.getTotal());
        }

        existingInvoice = invoiceRepository.save(existingInvoice);
        return mapper.toDTO(existingInvoice);
    }

    public void deleteInvoice(String id) {
        log.info("Deleting invoice with id: {}", id);
        invoiceRepository.deleteById(id);
        log.info("Invoice deleted successfully with id: {}", id);
    }

    public InvoiceDTO getInvoiceByUniqueId(String uniqueId) {
        Invoice invoice = invoiceRepository.findByUniqueId(uniqueId)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada con uniqueID: " + uniqueId));
        return mapper.toDTO(invoice);
    }

    public InvoiceDTO getInvoiceBySequential(String sequential) {
        log.info("Fetching invoice with sequential: {}", sequential);
        Invoice invoice = invoiceRepository.findBySequential(sequential)
                .orElseThrow(() -> new RuntimeException("Invoice not found with sequential: " + sequential));
        return mapper.toDTO(invoice);
    }

    public List<InvoiceDTO> getInvoicesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        List<Invoice> invoices = invoiceRepository.findByDateRange(startDate, endDate);
        return invoices.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

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