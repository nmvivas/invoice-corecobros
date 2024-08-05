package com.banquito.core.invoicedoc.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/invoice-microservice/api/v1/taxes")
@Validated
@Slf4j
@CrossOrigin(origins = "*")
@Tag(name = "Tax", description = "Endpoints for managing taxes")
public class TaxController {

    private TaxService taxService;

    public TaxController(TaxService taxService) {
        this.taxService = taxService;
    }

    
    @PostMapping
    @Operation(summary = "Create a new tax", description = "Create a new tax along with their details")
    public ResponseEntity<TaxDTO> createTax(@Valid @RequestBody TaxDTO taxDTO) {
        TaxDTO createdTax = taxService.createTax(taxDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTax);
    }

    @GetMapping
    @Operation(summary = "Get all taxes", description = "Retrive all taxes")
    public ResponseEntity<List<TaxDTO>> getAllTaxes() {
        List<TaxDTO> taxes = taxService.getAllTaxes();
        return ResponseEntity.ok(taxes);
    }

    @GetMapping("/{uniqueId}")
    @Operation(summary = "Get tax by uniqueID", description = "Retrive tax by uniqueID")
    public ResponseEntity<TaxDTO> getTaxByUniqueId(@PathVariable String uniqueId) {
        TaxDTO tax = taxService.getTaxByUniqueId(uniqueId);
        return ResponseEntity.ok(tax);
    }

    @PutMapping("/{uniqueId}")
    @Operation(summary = "Update a tax", description = "Update an existing tax")
    public ResponseEntity<TaxDTO> updateTax(@PathVariable String uniqueId,
            @Valid @RequestBody TaxDTO taxDTO) {
        TaxDTO updatedTax = taxService.updateTax(uniqueId, taxDTO);
        return ResponseEntity.ok(updatedTax);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a tax", description = "Delete an existing tax")
    public ResponseEntity<Void> deleteTax(@PathVariable String id) {
        taxService.deleteTax(id);
        return ResponseEntity.noContent().build();
    }
}
