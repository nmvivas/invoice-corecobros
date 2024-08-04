package com.banquito.core.invoicedoc.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class TaxDTO {

    private String id;
    private String uniqueId;
    private String invoiceId;
    private String name;
    private BigDecimal value;
    private BigDecimal percentage;
}
