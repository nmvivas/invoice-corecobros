package com.banquito.corecobros.invoice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.banquito.corecobros.invoice.dto.DetailInvoiceDTO;
import com.banquito.corecobros.invoice.model.DetailInvoice;
import com.banquito.corecobros.invoice.repository.DetailInvoiceRepository;
import com.banquito.corecobros.invoice.util.mapper.DetailInvoiceMapper;

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
        DetailInvoice detailInvoice = detailInvoiceMapper.toPersistence(detailInvoiceDTO);
        detailInvoice = detailInvoiceRepository.save(detailInvoice);
        return detailInvoiceMapper.toDTO(detailInvoice);
    }

    public DetailInvoiceDTO updateDetailInvoice(String id, DetailInvoiceDTO detailInvoiceDTO) {
        DetailInvoice existingDetailInvoice = detailInvoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encuentra el detalle de factura"));
        DetailInvoice detailInvoice = detailInvoiceMapper.toPersistence(detailInvoiceDTO);
        detailInvoice.setId(existingDetailInvoice.getId());
        detailInvoice = detailInvoiceRepository.save(detailInvoice);
        return detailInvoiceMapper.toDTO(detailInvoice);
    }

    public void deleteDetailInvoice(String id) {
        detailInvoiceRepository.deleteById(id);
    }

    public DetailInvoiceDTO getDetailInvoiceById(String id) {
        return detailInvoiceRepository.findById(id)
                .map(detailInvoiceMapper::toDTO)
                .orElse(null);
    }

    public List<DetailInvoiceDTO> getAllDetailInvoices() {
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
