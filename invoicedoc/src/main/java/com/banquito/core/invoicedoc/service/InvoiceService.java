package com.banquito.core.invoicedoc.service;

import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.banquito.core.invoicedoc.dto.InvoiceDTO;
import com.banquito.core.invoicedoc.model.Invoice;
import com.banquito.core.invoicedoc.repository.InvoiceRepository;
import com.banquito.core.invoicedoc.util.UniqueIdGeneration;
import com.banquito.core.invoicedoc.util.mapper.InvoiceMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InvoiceService {
    private InvoiceRepository invoiceRepository;
    private InvoiceMapper invoiceMapper;

    public InvoiceService(InvoiceRepository invoiceRepository, InvoiceMapper invoiceMapper) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
    }

    public InvoiceDTO createInvoice(InvoiceDTO invoiceDTO) {
        Invoice invoice = invoiceMapper.toPersistence(invoiceDTO);
        invoice.setUniqueId(new UniqueIdGeneration().getUniqueId());
        Invoice savedInvoice = invoiceRepository.save(invoice);
        return invoiceMapper.toDTO(savedInvoice);
    }

    public List<InvoiceDTO> getAllInvoices() {
        log.info("Fetching all invoices");
        return invoiceRepository.findAll().stream()
                .map(invoiceMapper::toDTO)
                .collect(Collectors.toList());
    }

    public InvoiceDTO getInvoiceById(String id) {
        log.info("Fetching invoice with id: {}", id);
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with id: " + id));
        return invoiceMapper.toDTO(invoice);
    }

    public InvoiceDTO updateInvoice(String id, InvoiceDTO invoiceDTO) {
        log.info("Updating invoice with id: {}", id);
        Invoice existingInvoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encuentra la factura"));
        Invoice invoice = invoiceMapper.toPersistence(invoiceDTO);
        invoice.setId(existingInvoice.getId());
        invoice = invoiceRepository.save(invoice);
        return invoiceMapper.toDTO(invoice);
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
        return invoiceMapper.toDTO(invoice);
    }

    public List<InvoiceDTO> getInvoicesByDateRange(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        List<Invoice> invoices = invoiceRepository.findByDateBetween(start.atStartOfDay(),
                end.plusDays(1).atStartOfDay());
        return invoices.stream().map(invoiceMapper::toDTO).collect(Collectors.toList());
    }

    public List<InvoiceDTO> getInvoicesByClient(String ruc, String companyName) {
        List<Invoice> invoices;
        if (companyName != null && !companyName.isEmpty()) {
            invoices = invoiceRepository.findByRucAndCompanyNameContainingIgnoreCase(ruc, companyName);
        } else {
            invoices = invoiceRepository.findByRuc(ruc);
        }
        return invoices.stream().map(invoiceMapper::toDTO).collect(Collectors.toList());
    }
}
