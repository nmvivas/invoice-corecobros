package com.banquito.core.invoicedoc.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Tax Code is mandatory")
    @Size(max = 10, message = "Tax Code cannot be longer than 10 characters")
    private String taxCode;

    @NotBlank(message = "Tax Name is mandatory")
    @Size(max = 50, message = "Tax Name cannot be longer than 50 characters")
    private String taxName;

    @NotNull(message = "Rate is mandatory")
    private BigDecimal rate;

    @NotNull(message = "Effective Date is mandatory")
    private LocalDateTime effectiveDate;

    @NotNull(message = "Expiration Date is mandatory")
    private LocalDateTime expirationDate;
}
