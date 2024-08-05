package com.banquito.core.invoicedoc.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.banquito.core.invoicedoc.dto.TaxDTO;
import com.banquito.core.invoicedoc.model.Invoice;
import com.banquito.core.invoicedoc.model.Tax;
import com.banquito.core.invoicedoc.repository.InvoiceRepository;
import com.banquito.core.invoicedoc.repository.TaxRepository;
import com.banquito.core.invoicedoc.util.UniqueIdGeneration;
import com.banquito.core.invoicedoc.util.mapper.TaxMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TaxService {

    private final TaxRepository taxRepository;
    private final TaxMapper taxMapper;
    private final InvoiceRepository invoiceRepository;
    private final UniqueIdGeneration uniqueIdGeneration;

    public TaxService(TaxRepository taxRepository, TaxMapper taxMapper, InvoiceRepository invoiceRepository,
            UniqueIdGeneration uniqueIdGeneration) {
        this.taxRepository = taxRepository;
        this.taxMapper = taxMapper;
        this.invoiceRepository = invoiceRepository;
        this.uniqueIdGeneration = uniqueIdGeneration;
    }

    public TaxDTO createTax(TaxDTO taxDTO) {
        log.info("Creating tax: {}", taxDTO);

        Invoice invoice = invoiceRepository.findByUniqueId(taxDTO.getInvoiceId())
                .orElseThrow(() -> new RuntimeException("Factura no encontrada"));

        log.info("Calcular el valor del impuesto para la factura: {}", invoice);
        BigDecimal value = calculateValue(invoice.getSubtotal(), taxDTO.getPercentage());
        taxDTO.setValue(value);

        Tax tax = taxMapper.toPersistence(taxDTO);
        tax.setUniqueId(uniqueIdGeneration.generateUniqueId());

        tax = taxRepository.save(tax);
        return taxMapper.toDTO(tax);
    }

    private BigDecimal calculateValue(BigDecimal subtotal, BigDecimal percentage) {
        return subtotal.multiply(percentage).divide(BigDecimal.valueOf(100));
    }

    public List<TaxDTO> getAllTaxes() {
        List<Tax> taxes = taxRepository.findAll();
        return taxes.stream().map(taxMapper::toDTO).collect(Collectors.toList());
    }

    public TaxDTO getTaxByUniqueId(String uniqueId) {
        Tax tax = taxRepository.findByUniqueId(uniqueId)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada con uniqueID: " + uniqueId));
        return taxMapper.toDTO(tax);
    }

    public TaxDTO updateTax(String uniqueId, TaxDTO taxDTO) {
        Tax existingTax = taxRepository.findByUniqueId(uniqueId)
                .orElseThrow(() -> new RuntimeException("Tax not found"));

        existingTax.setPercentage(taxDTO.getPercentage());

        Invoice relatedInvoice = invoiceRepository.findByUniqueId(existingTax.getInvoiceId())
                .orElseThrow(() -> new RuntimeException("La factura no se encuentra"));

        BigDecimal newValue = relatedInvoice.getSubtotal()
                .multiply(existingTax.getPercentage().divide(BigDecimal.valueOf(100)));
        existingTax.setValue(newValue);

        Tax updatedTax = taxRepository.save(existingTax);
        return taxMapper.toDTO(updatedTax);
    }

    public void deleteTax(String id) {
        Tax tax = taxRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tax not found"));
        taxRepository.delete(tax);
    }

}