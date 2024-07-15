package com.banquito.corecobros.invoice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.banquito.corecobros.invoice.dto.InvoiceDTO;
import com.banquito.corecobros.invoice.model.Invoice;
import com.banquito.corecobros.invoice.repository.InvoiceRepository;
import com.banquito.corecobros.invoice.util.mapper.InvoiceMapper;

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
        invoice = invoiceRepository.save(invoice);
        return invoiceMapper.toDTO(invoice);
    }

    public InvoiceDTO updateInvoice(String id, InvoiceDTO invoiceDTO) {
        Invoice existingInvoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encuentra la factura"));
        Invoice invoice = invoiceMapper.toPersistence(invoiceDTO);
        invoice.setId(existingInvoice.getId());
        invoice = invoiceRepository.save(invoice);
        return invoiceMapper.toDTO(invoice);
    }

    public void deleteInvoice(String id) {
        invoiceRepository.deleteById(id);
    }

    public InvoiceDTO getInvoiceById(String id) {
        return invoiceRepository.findById(id)
                .map(invoiceMapper::toDTO)
                .orElse(null);
    }

    public List<InvoiceDTO> getAllInvoices() {
        return invoiceRepository.findAll().stream()
                .map(invoiceMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<InvoiceDTO> getInvoicesByCompanyName(String companyName) {
        return invoiceRepository.findByCompanyNameContaining(companyName).stream()
                .map(invoiceMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<InvoiceDTO> getInvoicesByRuc(String ruc) {
        return invoiceRepository.findByRuc(ruc).stream()
                .map(invoiceMapper::toDTO)
                .collect(Collectors.toList());
    }
}
