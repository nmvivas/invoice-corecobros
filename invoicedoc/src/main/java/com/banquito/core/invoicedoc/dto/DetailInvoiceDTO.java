package com.banquito.corecobros.invoice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class DetailInvoiceDTO {

    private String id;
    private String invoiceId;
    private String service;
}
