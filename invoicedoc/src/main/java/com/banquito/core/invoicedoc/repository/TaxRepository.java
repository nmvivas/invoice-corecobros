package com.banquito.core.invoicedoc.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.banquito.core.invoicedoc.model.Tax;

public interface TaxRepository extends MongoRepository<Tax, String>{

    List<Tax> findByInvoiceId(String invoiceId);

}
