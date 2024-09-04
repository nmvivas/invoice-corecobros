package com.banquito.core.invoicedoc.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.banquito.core.invoicedoc.dto.TaxDTO;
import com.banquito.core.invoicedoc.model.Invoice;
import com.banquito.core.invoicedoc.model.Tax;
import com.banquito.core.invoicedoc.repository.InvoiceRepository;
import com.banquito.core.invoicedoc.repository.TaxRepository;
import com.banquito.core.invoicedoc.util.UniqueIdGeneration;
import com.banquito.core.invoicedoc.util.mapper.TaxMapper;

public class TaxServiceTest {

    @Mock
    private TaxRepository taxRepository;

    @Mock
    private TaxMapper taxMapper;

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private UniqueIdGeneration uniqueIdGeneration;

    @InjectMocks
    private TaxService taxService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTax() {
        // Mock data
        TaxDTO taxDTO = new TaxDTO();
        taxDTO.setInvoiceId("BWJ0087837");
        taxDTO.setPercentage(new BigDecimal("20.00"));
        Invoice invoice = new Invoice();
        invoice.setSubtotal(new BigDecimal("100.00"));
        Tax tax = new Tax();
        tax.setUniqueId("SZA0094735");
        tax.setInvoiceId("BWJ0087837");
        tax.setPercentage(new BigDecimal("20.00"));
        tax.setValue(new BigDecimal("20.00"));

        // Mock repository behavior
        when(invoiceRepository.findByUniqueId(taxDTO.getInvoiceId())).thenReturn(Optional.of(invoice));
        when(taxMapper.toPersistence(any(TaxDTO.class))).thenReturn(tax);
        when(taxRepository.save(any(Tax.class))).thenReturn(tax);
        when(taxMapper.toDTO(any(Tax.class))).thenReturn(taxDTO);

        // Call the service method
        TaxDTO result = taxService.createTax(taxDTO);

        // Verify and assert
        assertEquals(taxDTO.getValue(), result.getValue());
        verify(invoiceRepository, times(1)).findByUniqueId(taxDTO.getInvoiceId());
        verify(taxRepository, times(1)).save(tax);
        verify(taxMapper, times(1)).toDTO(tax);
    }

    @Test
    void testGetAllTaxes() {
        // Mock data
        Tax tax1 = new Tax();
        Tax tax2 = new Tax();
        List<Tax> taxes = List.of(tax1, tax2);
        List<TaxDTO> taxDTOs = List.of(new TaxDTO(), new TaxDTO());

        // Mock repository behavior
        when(taxRepository.findAll()).thenReturn(taxes);
        when(taxMapper.toDTO(any(Tax.class))).thenReturn(new TaxDTO());

        // Call the service method
        List<TaxDTO> result = taxService.getAllTaxes();

        // Verify and assert
        assertEquals(taxDTOs.size(), result.size());
        verify(taxRepository, times(1)).findAll();
        verify(taxMapper, times(2)).toDTO(any(Tax.class));
    }

    @Test
    void testGetTaxByUniqueId() {
        // Mock data
        String uniqueId = "SZA0094735";
        Tax tax = new Tax();
        tax.setUniqueId(uniqueId);
        TaxDTO taxDTO = new TaxDTO();

        // Mock repository behavior
        when(taxRepository.findByUniqueId(uniqueId)).thenReturn(Optional.of(tax));
        when(taxMapper.toDTO(tax)).thenReturn(taxDTO);

        // Call the service method
        TaxDTO result = taxService.getTaxByUniqueId(uniqueId);

        // Verify and assert
        assertNotNull(result);
        verify(taxRepository, times(1)).findByUniqueId(uniqueId);
        verify(taxMapper, times(1)).toDTO(tax);
    }

    @Test
    void testUpdateTax() {
        // Mock data
        String uniqueId = "SZA0094735";
        TaxDTO taxDTO = new TaxDTO();
        taxDTO.setPercentage(new BigDecimal("25.00"));
        Tax existingTax = new Tax();
        existingTax.setUniqueId(uniqueId);
        existingTax.setInvoiceId("BWJ0087837");
        Invoice relatedInvoice = new Invoice();
        relatedInvoice.setSubtotal(new BigDecimal("100.00"));

        // Mock repository behavior
        when(taxRepository.findByUniqueId(uniqueId)).thenReturn(Optional.of(existingTax));
        when(invoiceRepository.findByUniqueId(existingTax.getInvoiceId())).thenReturn(Optional.of(relatedInvoice));
        when(taxRepository.save(any(Tax.class))).thenReturn(existingTax);
        when(taxMapper.toDTO(any(Tax.class))).thenReturn(taxDTO);

        // Call the service method
        TaxDTO result = taxService.updateTax(uniqueId, taxDTO);

        // Verify and assert
        assertEquals(taxDTO.getPercentage(), result.getPercentage());
        verify(taxRepository, times(1)).findByUniqueId(uniqueId);
        verify(invoiceRepository, times(1)).findByUniqueId(existingTax.getInvoiceId());
        verify(taxRepository, times(1)).save(existingTax);
        verify(taxMapper, times(1)).toDTO(existingTax);
    }

    @Test
    void testDeleteTax() {
        // Mock data
        String taxId = "66aeed724ba78215759f249c";
        Tax tax = new Tax();
        tax.setId(taxId);

        // Mock repository behavior
        when(taxRepository.findById(taxId)).thenReturn(Optional.of(tax));
        doNothing().when(taxRepository).delete(tax);

        // Call the service method
        taxService.deleteTax(taxId);

        // Verify and assert
        verify(taxRepository, times(1)).findById(taxId);
        verify(taxRepository, times(1)).delete(tax);
    }

}
