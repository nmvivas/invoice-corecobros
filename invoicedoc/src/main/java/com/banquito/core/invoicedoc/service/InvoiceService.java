package com.banquito.core.invoicedoc.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.banquito.core.invoicedoc.dto.InvoiceDTO;
import com.banquito.core.invoicedoc.model.DetailInvoice;
import com.banquito.core.invoicedoc.model.Invoice;
import com.banquito.core.invoicedoc.model.Tax;
import com.banquito.core.invoicedoc.repository.DetailInvoiceRepository;
import com.banquito.core.invoicedoc.repository.InvoiceRepository;
import com.banquito.core.invoicedoc.repository.TaxRepository;
import com.banquito.core.invoicedoc.util.UniqueIdGeneration;
import com.banquito.core.invoicedoc.util.mapper.DetailInvoiceMapper;
import com.banquito.core.invoicedoc.util.mapper.InvoiceMapper;
import com.banquito.core.invoicedoc.util.mapper.TaxMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private DetailInvoiceRepository detailInvoiceRepository;
    private TaxRepository taxRepository;
    private final UniqueIdGeneration uniqueIdGeneration;
    private final InvoiceMapper mapper;
    private DetailInvoiceMapper detailInvoiceMapper;
    private TaxMapper taxMapper;

    public InvoiceService(InvoiceRepository invoiceRepository, DetailInvoiceRepository detailInvoiceRepository,
            TaxRepository taxRepository, UniqueIdGeneration uniqueIdGeneration, InvoiceMapper mapper,
            DetailInvoiceMapper detailInvoiceMapper, TaxMapper taxMapper) {
        this.invoiceRepository = invoiceRepository;
        this.detailInvoiceRepository = detailInvoiceRepository;
        this.taxRepository = taxRepository;
        this.uniqueIdGeneration = uniqueIdGeneration;
        this.mapper = mapper;
        this.detailInvoiceMapper = detailInvoiceMapper;
        this.taxMapper = taxMapper;
    }

    public InvoiceDTO create(InvoiceDTO dto) {
        log.info("Va a crear factura {}", dto);
    
        Invoice invoice = this.mapper.toPersistence(dto);
        String uniqueId = uniqueIdGeneration.generateUniqueId();
        invoice.setUniqueId(uniqueId);
        invoice.setDate(LocalDateTime.now());
    
        if (dto.getDetailInvoices() != null) {
            List<DetailInvoice> detailInvoices = dto.getDetailInvoices().stream()
                    .map(detailInvoiceDTO -> {
                        DetailInvoice detailInvoice = detailInvoiceMapper.toPersistence(detailInvoiceDTO);
                        detailInvoice.setInvoiceId(uniqueId);
                        return detailInvoice;
                    })
                    .collect(Collectors.toList());
            invoice.setDetailInvoices(detailInvoices);
        }
    
        if (dto.getTaxes() != null) {
            List<Tax> taxes = dto.getTaxes().stream()
                    .map(taxDTO -> {
                        Tax tax = taxMapper.toPersistence(taxDTO);
                        tax.setInvoiceId(uniqueId);
                        return tax;
                    })
                    .collect(Collectors.toList());
            invoice.setTaxes(taxes);
        }
    
        invoice = this.invoiceRepository.save(invoice);
        log.info("Se creó la factura: {}", invoice);
    
        if (invoice.getDetailInvoices() != null) {
            detailInvoiceRepository.saveAll(invoice.getDetailInvoices());
        }
    
        if (invoice.getTaxes() != null) {
            taxRepository.saveAll(invoice.getTaxes());
        }
    
        return this.mapper.toDTO(invoice);
    }


    public List<InvoiceDTO> getAllInvoices() {
        log.info("Fetching all invoices");
        return invoiceRepository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public InvoiceDTO getInvoiceById(String id) {
        log.info("Fetching invoice with id: {}", id);
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with id: " + id));
        return mapper.toDTO(invoice);
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
