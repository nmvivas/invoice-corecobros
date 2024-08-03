package com.banquito.core.invoicedoc.model;

import org.springframework.data.annotation.Id;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DetailInvoice {

    @Id
    private String id;

    @NotNull
    private String invoiceId;
    @NotNull
    private String service;

}
