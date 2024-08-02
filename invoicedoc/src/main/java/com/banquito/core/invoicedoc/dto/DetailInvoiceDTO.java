package com.banquito.core.invoicedoc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DetailInvoiceDTO {
    private String id;
    private String invoiceId;
    private String service;
}