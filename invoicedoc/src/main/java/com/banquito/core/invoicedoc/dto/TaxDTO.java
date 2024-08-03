package com.banquito.core.invoicedoc.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private String invoiceId;
    @NotNull
    private String name;
    @NotNull
    private BigDecimal value;
    @NotNull
    private BigDecimal percentage;
}
