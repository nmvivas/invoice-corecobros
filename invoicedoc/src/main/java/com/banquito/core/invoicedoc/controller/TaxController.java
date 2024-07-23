package com.banquito.core.invoicedoc.controller;

import org.springframework.http.ResponseEntity;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banquito.core.invoicedoc.dto.TaxDTO;
import com.banquito.core.invoicedoc.service.TaxService;

@RestController
@RequestMapping("/api/v1/taxes")
public class TaxController {

    private TaxService taxService;

    @PostMapping
    public ResponseEntity<TaxDTO> createTax(@RequestBody TaxDTO taxDTO) {
        TaxDTO createdTax = taxService.createTax(taxDTO);
        return ResponseEntity.ok(createdTax);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaxDTO> updateTax(@PathVariable String id, @RequestBody TaxDTO taxDTO) {
        TaxDTO updatedTax = taxService.updateTax(id, taxDTO);
        return ResponseEntity.ok(updatedTax);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTax(@PathVariable String id) {
        taxService.deleteTax(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaxDTO> getTaxById(@PathVariable String id) {
        TaxDTO taxDTO = taxService.getTaxById(id);
        return ResponseEntity.ok(taxDTO);
    }

    @GetMapping
    public ResponseEntity<List<TaxDTO>> getAllTaxes() {
        List<TaxDTO> taxes = taxService.getAllTaxes();
        return ResponseEntity.ok(taxes);
    }

    @GetMapping("/invoice/{invoiceId}")
    public ResponseEntity<List<TaxDTO>> getTaxesByInvoiceId(@PathVariable String invoiceId) {
        List<TaxDTO> taxes = taxService.getTaxesByInvoiceId(invoiceId);
        return ResponseEntity.ok(taxes);
    }
}