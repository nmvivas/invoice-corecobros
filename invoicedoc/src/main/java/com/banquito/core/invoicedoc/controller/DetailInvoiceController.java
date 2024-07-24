package com.banquito.core.invoicedoc.controller;

import com.banquito.core.invoicedoc.dto.DetailInvoiceDTO;
import com.banquito.core.invoicedoc.service.DetailInvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/detail-invoice")
@Validated
public class DetailInvoiceController {

    private final DetailInvoiceService detailInvoiceService;

    public DetailInvoiceController(DetailInvoiceService detailInvoiceService) {
        this.detailInvoiceService = detailInvoiceService;
    }

    @PostMapping
    public ResponseEntity<DetailInvoiceDTO> createDetailInvoice(@Valid @RequestBody DetailInvoiceDTO detailInvoiceDTO) {
        DetailInvoiceDTO createdDetailInvoice = detailInvoiceService.createDetailInvoice(detailInvoiceDTO);
        return new ResponseEntity<>(createdDetailInvoice, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailInvoiceDTO> getDetailInvoiceById(@PathVariable String id) {
        DetailInvoiceDTO detailInvoiceDTO = detailInvoiceService.getDetailInvoiceById(id);
        return new ResponseEntity<>(detailInvoiceDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<DetailInvoiceDTO>> getAllDetailInvoices() {
        List<DetailInvoiceDTO> detailInvoices = detailInvoiceService.getAllDetailInvoices();
        return new ResponseEntity<>(detailInvoices, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetailInvoiceDTO> updateDetailInvoice(@PathVariable String id, @Valid @RequestBody DetailInvoiceDTO detailInvoiceDTO) {
        DetailInvoiceDTO updatedDetailInvoice = detailInvoiceService.updateDetailInvoice(id, detailInvoiceDTO);
        return new ResponseEntity<>(updatedDetailInvoice, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDetailInvoice(@PathVariable String id) {
        detailInvoiceService.deleteDetailInvoice(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
