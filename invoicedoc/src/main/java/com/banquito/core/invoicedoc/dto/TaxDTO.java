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

    @NotBlank(message = "El código del impuesto es obligatorio.")
    @Size(max = 10, message = "El codigo del impuesto no puede tener más de 10 caracteres.")
    private String taxCode;

    @NotBlank(message = "El nombre del impuesto es obligatorio")
    @Size(max = 50, message = "El nombre del impuesto no puede tener más de 50 caracteres")
    private String taxName;

    @NotNull(message = "La tarifa es obligatoria")
    private BigDecimal rate;

    @NotNull(message = "La fecha de vigencia es obligatoria")
    private LocalDateTime effectiveDate;

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    private LocalDateTime expirationDate;
}
