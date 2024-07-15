package com.banquito.corecobros.invoice.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TaxDTO {

    private String id;
    private String invoiceId;
    private String name;
    private BigDecimal value;
    private BigDecimal percentage;
}
