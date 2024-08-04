package com.banquito.core.invoicedoc.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.banquito.core.invoicedoc.dto.InvoiceDTO;
import com.banquito.core.invoicedoc.service.InvoiceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/Invoice-Microservice/api/v1/invoices")
@Validated
@Slf4j
@Tag(name = "Invoice", description = "Endpoints for managing invoices")
public class InvoiceController {

    private InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    @Operation(summary = "Create a new invoice", description = "Create a new invoice along with their details")
    public ResponseEntity<InvoiceDTO> createInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        InvoiceDTO createdInvoice = invoiceService.create(invoiceDTO);
        return new ResponseEntity<>(createdInvoice, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all invoices", description = "Retrive all invoices")
    public ResponseEntity<List<InvoiceDTO>> getAllInvoices() {
        List<InvoiceDTO> invoices = invoiceService.getAllInvoices();
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/sequential/{sequential}")
    @Operation(summary = "Get invoice by sequential", description = "Retrive invoice by sequential")
    public ResponseEntity<InvoiceDTO> getInvoiceBySequential(@PathVariable String sequential) {
        InvoiceDTO invoice = invoiceService.getInvoiceBySequential(sequential);
        return ResponseEntity.ok(invoice);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an invoice", description = "Update an existing invoice")
    public ResponseEntity<InvoiceDTO> updateInvoice(@PathVariable String id,
            @Valid @RequestBody InvoiceDTO invoiceDTO) {
        InvoiceDTO updatedInvoice = invoiceService.updateInvoice(id, invoiceDTO);
        return ResponseEntity.ok(updatedInvoice);
    }

    @GetMapping("/uniqueId")
    @Operation(summary = "Get invoice by uniqueID", description = "Retrive invoice by uniqueID")
    public ResponseEntity<InvoiceDTO> getInvoiceByUniqueId(@RequestParam String uniqueId) {
        InvoiceDTO invoice = invoiceService.getInvoiceByUniqueId(uniqueId);
        return ResponseEntity.ok(invoice);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an invoice", description = "Delete an existing invoice")
    public ResponseEntity<Void> deleteInvoice(@PathVariable String id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/dateRange")
    @Operation(summary = "Get invoices by date range", description = "Retrive invoices by date range")
    public ResponseEntity<List<InvoiceDTO>> getInvoicesByDateRange(@RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        List<InvoiceDTO> invoices = invoiceService.getInvoicesByDateRange(startDate, endDate);
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/client")
    @Operation(summary = "Get invoices by client", description = "Retrive invoices by client")
    public ResponseEntity<List<InvoiceDTO>> getInvoicesByClient(
            @RequestParam String ruc,
            @RequestParam(required = false) String companyName) {
        List<InvoiceDTO> invoices = invoiceService.getInvoicesByClient(ruc, companyName);
        return ResponseEntity.ok(invoices);
    }
}
