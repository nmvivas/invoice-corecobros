package com.banquito.core.invoicedoc.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.banquito.core.invoicedoc.model.Tax;

@Repository
public interface TaxRepository extends MongoRepository<Tax, String>{

    List<Tax> findByInvoiceId(String invoiceId);

}
