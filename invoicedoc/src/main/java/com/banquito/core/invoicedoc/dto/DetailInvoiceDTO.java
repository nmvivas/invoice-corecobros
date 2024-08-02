package com.banquito.core.invoicedoc.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class DetailInvoiceDTO {
    @NotNull
    private String invoiceId;
    @NotNull
    private String service;
}