package com.banquito.core.invoicedoc.controller;

import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import com.banquito.core.invoicedoc.dto.DetailInvoiceDTO;
import com.banquito.core.invoicedoc.service.DetailInvoiceService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/v1/detail-invoices")
public class DetailInvoiceController {

    private DetailInvoiceService detailInvoiceService;

    @PostMapping
    public ResponseEntity<DetailInvoiceDTO> createDetailInvoice(@RequestBody DetailInvoiceDTO detailInvoiceDTO) {
        DetailInvoiceDTO createdDetailInvoice = detailInvoiceService.createDetailInvoice(detailInvoiceDTO);
        return ResponseEntity.ok(createdDetailInvoice);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetailInvoiceDTO> updateDetailInvoice(@PathVariable String id, @RequestBody DetailInvoiceDTO detailInvoiceDTO) {
        DetailInvoiceDTO updatedDetailInvoice = detailInvoiceService.updateDetailInvoice(id, detailInvoiceDTO);
        return ResponseEntity.ok(updatedDetailInvoice);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDetailInvoice(@PathVariable String id) {
        detailInvoiceService.deleteDetailInvoice(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailInvoiceDTO> getDetailInvoiceById(@PathVariable String id) {
        DetailInvoiceDTO detailInvoiceDTO = detailInvoiceService.getDetailInvoiceById(id);
        return ResponseEntity.ok(detailInvoiceDTO);
    }

    @GetMapping
    public ResponseEntity<List<DetailInvoiceDTO>> getAllDetailInvoices() {
        List<DetailInvoiceDTO> detailInvoices = detailInvoiceService.getAllDetailInvoices();
        return ResponseEntity.ok(detailInvoices);
    }

    @GetMapping("/invoice/{invoiceId}")
    public ResponseEntity<List<DetailInvoiceDTO>> getDetailInvoicesByInvoiceId(@PathVariable String invoiceId) {
        List<DetailInvoiceDTO> detailInvoices = detailInvoiceService.getDetailInvoicesByInvoiceId(invoiceId);
        return ResponseEntity.ok(detailInvoices);
    }
}