package com.banquito.corecobros.invoice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.banquito.corecobros.invoice.model.Invoice;

public interface InvoiceRepository extends MongoRepository<Invoice, String> {

    List<Invoice> findByCompanyNameContaining(String companyName);
    List<Invoice> findByRuc(String ruc);
}
