package com.banquito.core.invoicedoc.model;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Tax {

    private String uniqueId;
    private String invoiceId;

    private String name;
    private BigDecimal value;
    private BigDecimal percentage;

}
