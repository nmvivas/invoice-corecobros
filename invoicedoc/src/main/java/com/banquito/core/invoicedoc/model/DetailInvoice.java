package com.banquito.core.invoicedoc.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DetailInvoice {

    private String invoiceId;

    private String service;

}
