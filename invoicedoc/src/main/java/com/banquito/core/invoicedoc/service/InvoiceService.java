package com.banquito.core.invoicedoc.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.banquito.core.invoicedoc.dto.InvoiceDTO;
import com.banquito.core.invoicedoc.model.Invoice;
import com.banquito.core.invoicedoc.repository.InvoiceRepository;
import com.banquito.core.invoicedoc.util.mapper.InvoiceMapper;

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
        invoice.setUniqueId(UUID.randomUUID().toString());
        invoice = invoiceRepository.save(invoice);
        return invoiceMapper.toDTO(invoice);
    }

    public List<InvoiceDTO> getAllInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        return invoices.stream().map(invoiceMapper::toDTO).collect(Collectors.toList());
    }

    public InvoiceDTO getInvoiceById(String id) {
        Invoice invoice = invoiceRepository.findById(id).orElse(null);
        return invoiceMapper.toDTO(invoice);
    }

    public InvoiceDTO updateInvoice(String id, InvoiceDTO invoiceDTO) {
        Invoice invoice = invoiceMapper.toPersistence(invoiceDTO);
        invoice.setId(id);
        invoice = invoiceRepository.save(invoice);
        return invoiceMapper.toDTO(invoice);
    }

    public void deleteInvoice(String id) {
        invoiceRepository.deleteById(id);
    }
}
