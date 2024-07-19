package com.banquito.corecobros.invoice.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class TaxDTO {

    private String id;
    private String invoiceId;
    private String name;
    private BigDecimal value;
    private BigDecimal percentage;
}
