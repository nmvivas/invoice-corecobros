package com.banquito.core.invoicedoc.controller;

import com.banquito.core.invoicedoc.dto.InvoiceDTO;
import com.banquito.core.invoicedoc.service.InvoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/invoices")
@Validated
public class InvoiceController {

    private InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<InvoiceDTO> createInvoice(@Valid @RequestBody InvoiceDTO invoiceDTO) {
        InvoiceDTO createdInvoice = invoiceService.createInvoice(invoiceDTO);
        return ResponseEntity.status(201).body(createdInvoice); // Status 201 for created resource
    }

    @GetMapping
    public ResponseEntity<List<InvoiceDTO>> getAllInvoices() {
        List<InvoiceDTO> invoices = invoiceService.getAllInvoices();
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDTO> getInvoiceById(@PathVariable String id) {
        InvoiceDTO invoice = invoiceService.getInvoiceById(id);
        return ResponseEntity.ok(invoice);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvoiceDTO> updateInvoice(@PathVariable String id,
            @Valid @RequestBody InvoiceDTO invoiceDTO) {
        InvoiceDTO updatedInvoice = invoiceService.updateInvoice(id, invoiceDTO);
        return ResponseEntity.ok(updatedInvoice);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable String id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }
}
