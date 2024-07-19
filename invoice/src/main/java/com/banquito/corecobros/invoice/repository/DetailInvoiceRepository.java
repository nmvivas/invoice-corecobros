package com.banquito.corecobros.invoice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.banquito.corecobros.invoice.model.DetailInvoice;

public interface DetailInvoiceRepository extends MongoRepository<DetailInvoice, String>{

    List<DetailInvoice> findByInvoiceId(String invoiceId);
}
