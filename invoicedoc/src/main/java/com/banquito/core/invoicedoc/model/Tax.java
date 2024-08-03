package com.banquito.core.invoicedoc.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Tax {

    @Id
    private String id;

    @NotNull
    private String invoiceId;
    @NotNull
    private String name;
    @NotBlank
    private BigDecimal value;
    @NotBlank
    private BigDecimal percentage;

}
