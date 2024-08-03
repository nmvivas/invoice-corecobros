package com.banquito.core.invoicedoc.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class InvoiceDTO {
    private String id;
    private String uniqueId;
    private String ruc;
    private String companyName;
    private String address;
    private String phone;
    private String email;
    private String sequential;
    private String authorizationNumber;
    private LocalDateTime date;
    private BigDecimal subtotal;
    private BigDecimal total;
    private List<DetailInvoiceDTO> detailInvoices;
    private List<TaxDTO> taxes;
}