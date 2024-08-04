package com.banquito.core.invoicedoc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.banquito.core.invoicedoc.model.Tax;

@Repository
public interface TaxRepository extends MongoRepository<Tax, String> {

    List<Tax> findAll();

    Optional<Tax> findByUniqueId(String uniqueId);

    List<Tax> findByInvoiceId(String invoiceId);


}
