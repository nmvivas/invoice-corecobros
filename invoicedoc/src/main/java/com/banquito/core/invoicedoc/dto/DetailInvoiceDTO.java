package com.banquito.core.invoicedoc.dto;

import java.math.BigDecimal;
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
public class DetailInvoiceDTO {

    private String id;

    @NotBlank(message = "El ID de factura es obligatorio")
    private String invoiceId;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 255, message = "La descripción no puede tener más de 255 caracteres.")
    private String description;

    @NotNull(message = "La cantidad es obligatoria")
    private Integer quantity;

    @NotNull(message = "El precio unitario es obligatorio.")
    private BigDecimal unitPrice;

    @NotNull(message = "El precio total es obligatorio")
    private BigDecimal totalPrice;
}
