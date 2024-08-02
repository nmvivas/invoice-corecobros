package com.banquito.core.invoicedoc.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.banquito.core.invoicedoc.dto.DetailInvoiceDTO;
import com.banquito.core.invoicedoc.model.DetailInvoice;
import com.banquito.core.invoicedoc.repository.DetailInvoiceRepository;
import com.banquito.core.invoicedoc.util.mapper.DetailInvoiceMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DetailInvoiceService {
    private DetailInvoiceRepository detailInvoiceRepository;
    private DetailInvoiceMapper detailInvoiceMapper;

    public DetailInvoiceService(DetailInvoiceRepository detailInvoiceRepository,
            DetailInvoiceMapper detailInvoiceMapper) {
        this.detailInvoiceRepository = detailInvoiceRepository;
        this.detailInvoiceMapper = detailInvoiceMapper;
    }

    public DetailInvoiceDTO createDetailInvoice(DetailInvoiceDTO detailInvoiceDTO) {
        log.info("Creating detail invoice: {}", detailInvoiceDTO);
        DetailInvoice detailInvoice = detailInvoiceMapper.toModel(detailInvoiceDTO);
        detailInvoice = detailInvoiceRepository.save(detailInvoice);
        log.info("Detail invoice created successfully with id: {}", detailInvoice.getId());
        return detailInvoiceMapper.toDTO(detailInvoice);
    }

    public DetailInvoiceDTO updateDetailInvoice(String id, DetailInvoiceDTO detailInvoiceDTO) {
        log.info("Updating detail invoice with id: {}", id);
        DetailInvoice existingDetailInvoice = detailInvoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encuentra el detalle de factura"));
        DetailInvoice detailInvoice = detailInvoiceMapper.toModel(detailInvoiceDTO);
        detailInvoice.setId(existingDetailInvoice.getId());
        detailInvoice = detailInvoiceRepository.save(detailInvoice);
        log.info("Detail invoice updated successfully with id: {}", detailInvoice.getId());
        return detailInvoiceMapper.toDTO(detailInvoice);
    }

    public void deleteDetailInvoice(String id) {
        log.info("Deleting detail invoice with id: {}", id);
        detailInvoiceRepository.deleteById(id);
        log.info("Detail invoice deleted successfully with id: {}", id);
    }

    public DetailInvoiceDTO getDetailInvoiceById(String id) {
       log.info("Fetching detail invoice with id: {}", id);
        return detailInvoiceRepository.findById(id)
                .map(detailInvoiceMapper::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Detail invoice not found with id: " + id));
    }

    public List<DetailInvoiceDTO> getAllDetailInvoices() {
       log.info("Fetching all detail invoices");
        return detailInvoiceRepository.findAll().stream()
                .map(detailInvoiceMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<DetailInvoiceDTO> getDetailInvoicesByInvoiceId(String invoiceId) {
        return detailInvoiceRepository.findByInvoiceId(invoiceId).stream()
                .map(detailInvoiceMapper::toDTO)
                .collect(Collectors.toList());
    }

}
