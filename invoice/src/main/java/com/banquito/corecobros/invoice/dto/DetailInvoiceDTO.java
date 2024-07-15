package com.banquito.corecobros.invoice.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DetailInvoiceDTO {

    private String id;
    private String invoiceId;
    private String service;
}
