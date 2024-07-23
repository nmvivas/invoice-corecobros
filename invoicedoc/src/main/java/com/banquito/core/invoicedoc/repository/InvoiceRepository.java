package com.banquito.core.invoicedoc.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.banquito.core.invoicedoc.model.Invoice;

public interface InvoiceRepository extends MongoRepository<Invoice, String> {

    List<Invoice> findByCompanyNameContaining(String companyName);
    List<Invoice> findByRuc(String ruc);
}
