package com.banquito.core.invoicedoc.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import java.util.Optional;
import org.springframework.stereotype.Service;

import com.banquito.core.invoicedoc.dto.TaxDTO;
import com.banquito.core.invoicedoc.model.Tax;
import com.banquito.core.invoicedoc.repository.TaxRepository;
import com.banquito.core.invoicedoc.util.mapper.TaxMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TaxService {

    private TaxRepository taxRepository;
    private TaxMapper taxMapper;

    public TaxService(TaxRepository taxRepository, TaxMapper taxMapper) {
        this.taxRepository = taxRepository;
        this.taxMapper = taxMapper;
    }

    public TaxDTO createTax(TaxDTO taxDTO) {
        log.info("Creating tax: {}", taxDTO);
        Tax tax = taxMapper.toModel(taxDTO);
        tax.setUniqueId(UUID.randomUUID().toString());
        tax = taxRepository.save(tax);
        log.info("Tax created: {}", tax);
        return taxMapper.toDTO(tax);
    }

    public TaxDTO updateTax(String id, TaxDTO taxDTO) {
        Tax existingTax = taxRepository.findById(id).orElseThrow(() -> new RuntimeException("Tax not found"));
        Tax tax = taxMapper.toModel(taxDTO);
        tax.setId(existingTax.getId());
        tax = taxRepository.save(tax);
        return taxMapper.toDTO(tax);
    }

    public void deleteTax(String id) {
        log.info("Deleting tax with ID: {}", id);
        if (taxRepository.existsById(id)) {
            taxRepository.deleteById(id);
            log.info("Tax deleted with ID: {}", id);
        } else {
            log.warn("Tax not found with ID: {}", id);
        }
    }

    public TaxDTO getTaxById(String id) {
        log.info("Fetching tax with ID: {}", id);
        Optional<Tax> taxOptional = taxRepository.findById(id);
        if (taxOptional.isPresent()) {
            log.info("Tax found with ID: {}", id);
            return taxMapper.toDTO(taxOptional.get());
        } else {
            log.warn("Tax not found with ID: {}", id);
            return null;
        }
    }

    public List<TaxDTO> getAllTaxes() {
        log.info("Fetching all taxes");
        List<Tax> taxes = taxRepository.findAll();
        return taxes.stream()
                .map(taxMapper::toDTO)
                .collect(Collectors.toList());
    }


}
