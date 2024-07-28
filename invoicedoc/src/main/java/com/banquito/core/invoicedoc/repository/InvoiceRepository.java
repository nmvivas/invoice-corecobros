package com.banquito.core.invoicedoc.repository;

import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.banquito.core.invoicedoc.model.Invoice;

public interface InvoiceRepository extends MongoRepository<Invoice, String> {

    List<Invoice> findByCompanyNameContaining(String companyName);

    List<Invoice> findByRuc(String ruc);

    Optional<Invoice> findBySequential(String sequential);

    List<Invoice> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Invoice> findByRucAndCompanyNameContainingIgnoreCase(String ruc, String companyName);
}
