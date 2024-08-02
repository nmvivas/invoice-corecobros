package com.banquito.core.invoicedoc.controller;

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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/invoices")
@Validated
public class InvoiceController {

    private InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public ResponseEntity<InvoiceDTO> createInvoice(@RequestBody InvoiceDTO dto) {
        try {
            InvoiceDTO dtoInv = this.invoiceService.createInvoice(dto);
            return new ResponseEntity<>(dtoInv, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
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

    @GetMapping("/sequential/{sequential}")
    public ResponseEntity<InvoiceDTO> getInvoiceBySequential(@PathVariable String sequential) {
        InvoiceDTO invoice = invoiceService.getInvoiceBySequential(sequential);
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

    // @GetMapping("/date-range")
    // public ResponseEntity<List<InvoiceDTO>> getInvoicesByDateRange(
    // @RequestParam String startDate,
    // @RequestParam String endDate) {
    // List<InvoiceDTO> invoices = invoiceService.getInvoicesByDateRange(startDate,
    // endDate);
    // return ResponseEntity.ok(invoices);
    // }

    @GetMapping("/client")
    public ResponseEntity<List<InvoiceDTO>> getInvoicesByClient(
            @RequestParam String ruc,
            @RequestParam(required = false) String companyName) {
        List<InvoiceDTO> invoices = invoiceService.getInvoicesByClient(ruc, companyName);
        return ResponseEntity.ok(invoices);
    }
}
