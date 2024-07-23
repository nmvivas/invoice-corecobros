package com.banquito.core.invoicedoc.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.banquito.core.invoicedoc.dto.TaxDTO;
import com.banquito.core.invoicedoc.model.Tax;
import com.banquito.core.invoicedoc.repository.TaxRepository;
import com.banquito.core.invoicedoc.util.mapper.TaxMapper;

@Service
public class TaxService {

    private TaxRepository taxRepository;
    private TaxMapper taxMapper;

    public TaxService(TaxRepository taxRepository, TaxMapper taxMapper) {
        this.taxRepository = taxRepository;
        this.taxMapper = taxMapper;
    }

   public TaxDTO createTax(TaxDTO taxDTO) {
        Tax tax = taxMapper.toPersistence(taxDTO);
        tax.setUniqueId(UUID.randomUUID().toString());
        tax = taxRepository.save(tax);
        return taxMapper.toDTO(tax);
    }

    public TaxDTO updateTax(String id, TaxDTO taxDTO) {
        Tax existingTax = taxRepository.findById(id).orElseThrow(() -> new RuntimeException("Tax not found"));
        Tax tax = taxMapper.toPersistence(taxDTO);
        tax.setId(existingTax.getId());
        tax = taxRepository.save(tax);
        return taxMapper.toDTO(tax);
    }

    public void deleteTax(String id) {
        taxRepository.deleteById(id);
    }

    public TaxDTO getTaxById(String id) {
        return taxRepository.findById(id)
                .map(taxMapper::toDTO)
                .orElse(null);
    }

    public List<TaxDTO> getAllTaxes() {
        return taxRepository.findAll().stream()
                .map(taxMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<TaxDTO> getTaxesByInvoiceId(String invoiceId) {
        return taxRepository.findByInvoiceId(invoiceId).stream()
                .map(taxMapper::toDTO)
                .collect(Collectors.toList());
    }

}
