package com.banquito.core.invoicedoc.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.banquito.core.invoicedoc.model.DetailInvoice;

public interface DetailInvoiceRepository extends MongoRepository<DetailInvoice, String>{

    List<DetailInvoice> findByInvoiceId(String invoiceId);
}
